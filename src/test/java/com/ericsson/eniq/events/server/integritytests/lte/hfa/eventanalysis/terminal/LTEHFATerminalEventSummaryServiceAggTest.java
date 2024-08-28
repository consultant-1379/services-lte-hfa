/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.eniq.events.server.integritytests.lte.hfa.eventanalysis.terminal;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.EventIDConstants.*;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;
import static com.ericsson.eniq.events.server.test.temptables.TempTableNames.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.eniq.events.server.common.EventIDConstants;
import com.ericsson.eniq.events.server.integritytests.stubs.ReplaceTablesWithTempTablesTemplateUtils;
import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis.TerminalEventSummaryService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFATerminalEventSummaryResultRaw;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFATerminalEventSummaryServiceAggTest extends BaseDataIntegrityTest<LTEHFATerminalEventSummaryResultRaw> {
    private TerminalEventSummaryService terminalEventSummaryService;

    private static final String DATETIME_ID_DAY_1 = "2011-09-19 08:15:00";

    private static final String DATETIME_ID_DAY_2 = "2011-09-18 12:15:00";

    private static final String DATE_FROM_DAY = "13092011";

    private static final String DATE_TO_DAY = "20092011";

    private static final String DATETIME_ID_15MIN_1 = "2011-09-20 08:15:00";

    private static final String DATETIME_ID_15MIN_2 = "2011-09-20 12:15:00";

    private static final String DATE_FROM_15MIN = "20092011";

    private static final String DATE_TO_15MIN = "20092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "1530";

    private static final String TEST_VALUE_TAC = "1090801";

    private static final String TEST_VALUE_IMSI_1 = "11111119";

    private static final String TEST_VALUE_IMSI_2 = "11111118";

    private boolean insertDataToAggregationTable = false;

    private boolean insertDataZeroTacToAggregationTable = false;

    private final static String EXPECTED_VALID_MANUFACTURER = "Sony Ericsson";

    private final static String EXPECTED_VALID_MARKETING_NAME = "TBD (AAB-1880030-BV)";

    private boolean insertDataDummyTacToAggregationTable = false;

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     *
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        terminalEventSummaryService = new TerminalEventSummaryService();
        attachDependencies(terminalEventSummaryService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        createTable();
        insertTopoData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(IMSI);
        columnsForTable.add(TAC);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(LOCAL_DATE_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ERR_RAW, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(TAC);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(TAC);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(EVENT_ID_DESC);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(CATEGORY_ID_2_DESC);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_EVENTTYPE, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(TAC);
        columnsForTable.add(VENDOR_NAME);
        columnsForTable.add(MARKETING_NAME);
        createTemporaryTable(TEMP_DIM_E_SGEH_TAC, columnsForTable);
    }

    private void insertTopoData() throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();

        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_S1_IN");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_EXEC);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.EXECUTION_CATEGORY_ID_2_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_S1_OUT");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_EXEC);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.EXECUTION_CATEGORY_ID_2_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_X2_IN");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_EXEC);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.EXECUTION_CATEGORY_ID_2_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_X2_OUT");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_EXEC);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.EXECUTION_CATEGORY_ID_2_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_S1_IN");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_PREP);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.PREPARATION_CATEGORY_ID_2_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_S1_OUT");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_PREP);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.PREPARATION_CATEGORY_ID_2_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_X2_IN");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_PREP);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.PREPARATION_CATEGORY_ID_2_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_X2_OUT");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_PREP);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.PREPARATION_CATEGORY_ID_2_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, Integer.valueOf(TEST_VALUE_EXCLUSIVE_TAC));
        valuesForTable.put(GROUP_NAME, EXCLUSIVE_TAC_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TEST_VALUE_TAC);
        valuesForTable.put(VENDOR_NAME, "Sony Ericsson");
        valuesForTable.put(MARKETING_NAME, "TBD (AAB-1880030-BV)");
        insertRow(TEMP_DIM_E_SGEH_TAC, valuesForTable);
    }

    private void insertData(final String eventID, final String categoryID, final String tac, final String time, final int instances, final String imsi)
            throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        for (int i = 0; i < instances; i++) {
            final String localDateId = time.substring(0, 10);
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryID);
            valuesForTable.put(TAC, tac);
            valuesForTable.put(IMSI_PARAM, imsi);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(LOCAL_DATE_ID, localDateId);
            insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
        }
    }

    private void insertAggData(final String aggTable, final String tac, final String eventID, final String categoryID, final String time,
                               final int instances) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(EVENT_ID, eventID);
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryID);
        valuesForTable.put(TAC, tac);
        valuesForTable.put(NO_OF_ERRORS, instances);
        valuesForTable.put(DATETIME_ID, time);
        insertRow(aggTable, valuesForTable);
        valuesForTable.clear();
    }

    private void insertDataForAggTestWithEventID(final String eventID, final String categoryID, final int instances, final String dateTimeID,
                                                 final String aggTable) throws SQLException {
        insertData(eventID, categoryID, TEST_VALUE_TAC, dateTimeID, instances, TEST_VALUE_IMSI_1);
        insertData(eventID, categoryID, TEST_VALUE_EXCLUSIVE_TAC, dateTimeID, instances, TEST_VALUE_IMSI_1);
        insertData(eventID, categoryID, TEST_VALUE_TAC, dateTimeID, instances, TEST_VALUE_IMSI_2);
        insertData(eventID, categoryID, TEST_VALUE_EXCLUSIVE_TAC, dateTimeID, instances, TEST_VALUE_IMSI_2);
        insertData(eventID, categoryID, String.valueOf(SAMPLE_TAC_2), dateTimeID, instances, TEST_VALUE_IMSI_1);
        insertData(eventID, categoryID, String.valueOf(SAMPLE_TAC_2), dateTimeID, instances, TEST_VALUE_IMSI_2);
        insertData(eventID, categoryID, String.valueOf(TAC_EQUAL_TO_ZERO), dateTimeID, instances, TEST_VALUE_IMSI_1);
        insertData(eventID, categoryID, String.valueOf(TAC_EQUAL_TO_ZERO), dateTimeID, instances, TEST_VALUE_IMSI_2);
        if (insertDataToAggregationTable) {
            insertAggData(aggTable, TEST_VALUE_TAC, eventID, categoryID, dateTimeID, instances);
        }
        if (insertDataDummyTacToAggregationTable) {
            insertAggData(aggTable, String.valueOf(SAMPLE_TAC_2), eventID, categoryID, dateTimeID, instances);
        }
        if (insertDataZeroTacToAggregationTable) {
            insertAggData(aggTable, String.valueOf(TAC_EQUAL_TO_ZERO), eventID, categoryID, dateTimeID, instances);
        }
    }

    private String getJsonResult(final String dateFrom, final String dateTo, final String categoryId, final String tac) throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(TAC_PARAM, tac);
        requestParameters.putSingle(CATEGORY_ID_SQL_PARAM, categoryId);
        return runQuery(terminalEventSummaryService, requestParameters);
    }

    private void insertAggData(final String aggTable, final String dateTimeID) throws Exception {
        insertDataForAggTestWithEventID(INTERNAL_PROC_HO_PREP_X2_IN_HFA, CATEGORY_ID_2_PREP, 1, dateTimeID, aggTable);
        insertDataForAggTestWithEventID(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, CATEGORY_ID_2_PREP, 1, dateTimeID, aggTable);
        insertDataForAggTestWithEventID(INTERNAL_PROC_HO_PREP_S1_IN_HFA, CATEGORY_ID_2_PREP, 1, dateTimeID, aggTable);
        insertDataForAggTestWithEventID(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, CATEGORY_ID_2_PREP, 1, dateTimeID, aggTable);
        insertDataForAggTestWithEventID(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, CATEGORY_ID_2_EXEC, 1, dateTimeID, aggTable);
        insertDataForAggTestWithEventID(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, CATEGORY_ID_2_EXEC, 1, dateTimeID, aggTable);
        insertDataForAggTestWithEventID(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, CATEGORY_ID_2_EXEC, 1, dateTimeID, aggTable);
        insertDataForAggTestWithEventID(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, CATEGORY_ID_2_EXEC, 1, dateTimeID, aggTable);
    }

    @Test
    public void testTerminalEventSummaryPrep15Min() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_PREP, TEST_VALUE_TAC);

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryExec15Min() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_EXEC, TEST_VALUE_TAC);

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryPrepDay() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_PREP, TEST_VALUE_TAC);

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryExecDay() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_EXEC, TEST_VALUE_TAC);

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryPrep15MinForExclusiveTAC() throws Exception {
        insertDataToAggregationTable = false;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_PREP, TEST_VALUE_EXCLUSIVE_TAC);

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertThat(eventResult.size(), is(4));
        assertResultForExclusiveTAC(eventResult);
    }

    @Test
    public void testTerminalEventSummaryExec15MinForExclusiveTAC() throws Exception {
        insertDataToAggregationTable = false;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_EXEC, TEST_VALUE_EXCLUSIVE_TAC);

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertThat(eventResult.size(), is(4));
        assertResultForExclusiveTAC(eventResult);
    }

    @Test
    public void testTerminalEventSummaryPrepDayForExclusiveTAC() throws Exception {
        insertDataToAggregationTable = false;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_PREP, TEST_VALUE_EXCLUSIVE_TAC);

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertThat(eventResult.size(), is(4));
        assertResultForExclusiveTAC(eventResult);
    }

    @Test
    public void testTerminalEventSummaryExecDayForExclusiveTAC() throws Exception {
        insertDataToAggregationTable = false;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_EXEC, TEST_VALUE_EXCLUSIVE_TAC);

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertThat(eventResult.size(), is(4));
        assertResultForExclusiveTAC(eventResult);
    }

    @Test
    public void testTerminalEventSummaryPrepDayToVerifyUnknownManufacturerAndUnknownModelvalue() throws Exception {
        insertDataToAggregationTable = true;
        insertDataDummyTacToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_PREP, String.valueOf(SAMPLE_TAC_2));

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryPrepDayToVerifyInvalidManufacturerAndInvalidModelvalue() throws Exception {
        insertDataToAggregationTable = true;
        insertDataZeroTacToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_PREP, String.valueOf(TAC_EQUAL_TO_ZERO));

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryExecDayToVerifyUnknownManufacturerAndUnknownModelvalue() throws Exception {
        insertDataToAggregationTable = true;
        insertDataDummyTacToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_EXEC, String.valueOf(SAMPLE_TAC_2));

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryExecDayToVerifyInvalidManufacturerAndInvalidModelvalue() throws Exception {
        insertDataToAggregationTable = true;
        insertDataZeroTacToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_EXEC, String.valueOf(TAC_EQUAL_TO_ZERO));

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryPrep15MinToVerifyUnknownManufacturerAndUnknownModelvalue() throws Exception {
        insertDataToAggregationTable = true;
        insertDataDummyTacToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_PREP, String.valueOf(SAMPLE_TAC_2));

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryPrep15MinToVerifyInvalidManufacturerAndInavlidModelvalue() throws Exception {
        insertDataToAggregationTable = true;
        insertDataZeroTacToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_PREP, String.valueOf(TAC_EQUAL_TO_ZERO));

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryExec15MinToVerifyUnknownManufacturerAndUnknownModelvalue() throws Exception {
        insertDataToAggregationTable = true;
        insertDataDummyTacToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_EXEC, String.valueOf(SAMPLE_TAC_2));

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalEventSummaryExec15MinToVerifyInvalidManufacturerAndInvalidModelvalue() throws Exception {
        insertDataToAggregationTable = true;
        insertDataZeroTacToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_EXEC, String.valueOf(TAC_EQUAL_TO_ZERO));

        final ResultTranslator<LTEHFATerminalEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFATerminalEventSummaryResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    private void assertResult(final List<LTEHFATerminalEventSummaryResultRaw> results) {
        for (final LTEHFATerminalEventSummaryResultRaw rs : results) {
            assertEquals(rs.getFailures(), 2);
            assertEquals(rs.getImpactedSubscribers(), 2);
            if (rs.getTac().equals(Integer.toString(SAMPLE_TAC_2))) {
                assertEquals(TERMINAL_MAKE_UNKNOWN, rs.getManufacture());
                assertEquals(TERMINAL_MODEL_UNKNOWN, rs.getModel());
            } else if (rs.getTac().equals(Integer.toString(TAC_EQUAL_TO_ZERO))) {
                assertEquals(TERMINAL_MAKE_INVALID, rs.getManufacture());
                assertEquals(TERMINAL_MODEL_INVALID, rs.getModel());
            } else if (rs.getTac().equals(TEST_VALUE_TAC)) {
                assertEquals(EXPECTED_VALID_MANUFACTURER, rs.getManufacture());
                assertEquals(EXPECTED_VALID_MARKETING_NAME, rs.getModel());
            }
        }
    }

    private void assertResultForExclusiveTAC(final List<LTEHFATerminalEventSummaryResultRaw> results) {
        for (final LTEHFATerminalEventSummaryResultRaw rs : results) {
            assertEquals(4, rs.getFailures());
            assertEquals(2, rs.getImpactedSubscribers());
        }
    }
}