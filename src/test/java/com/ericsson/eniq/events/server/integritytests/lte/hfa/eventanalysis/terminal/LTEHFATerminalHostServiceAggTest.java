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
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis.TerminalHandoverStageService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFATerminalHostResultRaw;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFATerminalHostServiceAggTest extends BaseDataIntegrityTest<LTEHFATerminalHostResultRaw> {

    private TerminalHandoverStageService lteHFATerminalHandoverStageService;

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

    private static final String TEST_VALUE_IMSI_1 = "123456789012345";

    private static final String TEST_VALUE_IMSI_2 = "123456789012346";

    private static final int TEST_VALUE_EXCLUSIVE_TAC = 2222222;

    private static final int TEST_VALUE_TAC = 35927204;

    private boolean insertDataToAggregationTable = false;

    private final static String EXPECTED_VALID_MANUFACTURER = "Sony Ericsson Mobile Communications AB";

    private final static String EXPECTED_VALID_MARKETING_NAME = "Sony Ericsson E16i";

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     *
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHFATerminalHandoverStageService = new TerminalHandoverStageService();
        attachDependencies(lteHFATerminalHandoverStageService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        createTable();
        insertTopoData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(IMSI);
        columnsForTable.add(TAC);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(LOCAL_DATE_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ERR_RAW, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(EVENT_ID_DESC);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(CATEGORY_ID_2_DESC);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_EVENTTYPE, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(VENDOR_NAME);
        columnsForTable.add(MARKETING_NAME);
        columnsForTable.add(TAC);
        createTemporaryTable(TEMP_DIM_E_SGEH_TAC, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(TAC);
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(TAC);
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, columnsForTable);
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
        valuesForTable.put(VENDOR_NAME, "Sony Ericsson Mobile Communications AB");
        valuesForTable.put(MARKETING_NAME, "Sony Ericsson E16i");
        insertRow(TEMP_DIM_E_SGEH_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TEST_VALUE_EXCLUSIVE_TAC);
        valuesForTable.put(VENDOR_NAME, "Sony Ericsson Mobile Communications AB");
        valuesForTable.put(MARKETING_NAME, "Sony Ericsson E16i");
        insertRow(TEMP_DIM_E_SGEH_TAC, valuesForTable);
    }

    private void insertData(final String categoryID, final int tac, final String time, final int instances, final String imsi) throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            final String localDateId = time.substring(0, 10);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryID);
            valuesForTable.put(TAC, tac);
            valuesForTable.put(IMSI_PARAM, imsi);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(LOCAL_DATE_ID, localDateId);
            insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
        }
    }

    private void insertAggData(final String aggTable, final int tac, final String categoryID, final String time, final int instances)
            throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryID);
        valuesForTable.put(TAC, tac);
        valuesForTable.put(NO_OF_ERRORS, instances);
        valuesForTable.put(DATETIME_ID, time);
        insertRow(aggTable, valuesForTable);
    }

    private void insertDataForAggTestWithEventID(final String categoryID, final int instances, final String dateTimeID, final String aggTable,
                                                 final int tac) throws SQLException {
        insertData(categoryID, tac, dateTimeID, instances, TEST_VALUE_IMSI_1);
        insertData(categoryID, tac, dateTimeID, instances, TEST_VALUE_IMSI_1);
        insertData(categoryID, tac, dateTimeID, instances, TEST_VALUE_IMSI_2);
        insertData(categoryID, tac, dateTimeID, instances, TEST_VALUE_IMSI_2);
        if (insertDataToAggregationTable) {
            insertAggData(aggTable, tac, categoryID, dateTimeID, instances);
        }
    }

    private String getJsonResultAgg(final String dateFrom, final String dateTo, final int tac) throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(TYPE_PARAM, TYPE_TAC);
        requestParameters.putSingle(NODE_PARAM, "TBD (AAB-1880030-BV)," + tac);
        return runQuery(lteHFATerminalHandoverStageService, requestParameters);
    }

    private void insertAggData(final String aggTable, final String dateTimeID, final int tac) throws Exception {
        insertDataForAggTestWithEventID(CATEGORY_ID_2_PREP, 2, dateTimeID, aggTable, tac);
        insertDataForAggTestWithEventID(CATEGORY_ID_2_PREP, 2, dateTimeID, aggTable, tac);
        insertDataForAggTestWithEventID(CATEGORY_ID_2_PREP, 2, dateTimeID, aggTable, tac);
        insertDataForAggTestWithEventID(CATEGORY_ID_2_PREP, 2, dateTimeID, aggTable, tac);
        insertDataForAggTestWithEventID(CATEGORY_ID_2_EXEC, 2, dateTimeID, aggTable, tac);
        insertDataForAggTestWithEventID(CATEGORY_ID_2_EXEC, 2, dateTimeID, aggTable, tac);
        insertDataForAggTestWithEventID(CATEGORY_ID_2_EXEC, 2, dateTimeID, aggTable, tac);
        insertDataForAggTestWithEventID(CATEGORY_ID_2_EXEC, 2, dateTimeID, aggTable, tac);
    }

    @Test
    public void testTerminalHostSummary15MinAgg() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1, TEST_VALUE_TAC);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2, TEST_VALUE_TAC);

        final String json = getJsonResultAgg(DATE_FROM_15MIN, DATE_TO_15MIN, TEST_VALUE_TAC);

        final ResultTranslator<LTEHFATerminalHostResultRaw> rt = getTranslator();
        final List<LTEHFATerminalHostResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRaw.class);
        assertThat(eventResult.get(0).getManufacture(), is(EXPECTED_VALID_MANUFACTURER));
        assertThat(eventResult.get(0).getModel(), is(EXPECTED_VALID_MARKETING_NAME));
        assertResult(eventResult);
    }

    @Test
    public void testTerminalHostSummaryDayAgg() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1, TEST_VALUE_TAC);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2, TEST_VALUE_TAC);

        final String json = getJsonResultAgg(DATE_FROM_DAY, DATE_TO_DAY, TEST_VALUE_TAC);

        final ResultTranslator<LTEHFATerminalHostResultRaw> rt = getTranslator();
        final List<LTEHFATerminalHostResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRaw.class);
        assertThat(eventResult.get(0).getManufacture(), is(EXPECTED_VALID_MANUFACTURER));
        assertThat(eventResult.get(0).getModel(), is(EXPECTED_VALID_MARKETING_NAME));
        assertResult(eventResult);
    }

    @Test
    public void testTerminalHostSummary15MinAggForExclusiveTAC() throws Exception {
        insertDataToAggregationTable = false;

        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1, TEST_VALUE_EXCLUSIVE_TAC);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2, TEST_VALUE_EXCLUSIVE_TAC);

        final String json = getJsonResultAgg(DATE_FROM_15MIN, DATE_TO_15MIN, TEST_VALUE_EXCLUSIVE_TAC);

        final ResultTranslator<LTEHFATerminalHostResultRaw> rt = getTranslator();
        final List<LTEHFATerminalHostResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRaw.class);
        assertResultForExclusiveTAC(eventResult);
    }

    @Test
    public void testTerminalHostSummaryDayAggForExclusiveTAC() throws Exception {
        insertDataToAggregationTable = false;

        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1, TEST_VALUE_EXCLUSIVE_TAC);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2, TEST_VALUE_EXCLUSIVE_TAC);

        final String json = getJsonResultAgg(DATE_FROM_DAY, DATE_TO_DAY, TEST_VALUE_EXCLUSIVE_TAC);

        final ResultTranslator<LTEHFATerminalHostResultRaw> rt = getTranslator();
        final List<LTEHFATerminalHostResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRaw.class);
        assertResultForExclusiveTAC(eventResult);
    }

    @Test
    public void testTerminalHostSummary15MinAggToVerifyTacInRawNotInDim() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1, SAMPLE_TAC_2);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2, SAMPLE_TAC_2);

        final String json = getJsonResultAgg(DATE_FROM_15MIN, DATE_TO_15MIN, SAMPLE_TAC_2);

        final ResultTranslator<LTEHFATerminalHostResultRaw> rt = getTranslator();
        final List<LTEHFATerminalHostResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalHostSummaryDayAggToVerifyTacInRawNotInDim() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1, SAMPLE_TAC_2);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2, SAMPLE_TAC_2);

        final String json = getJsonResultAgg(DATE_FROM_DAY, DATE_TO_DAY, SAMPLE_TAC_2);

        final ResultTranslator<LTEHFATerminalHostResultRaw> rt = getTranslator();
        final List<LTEHFATerminalHostResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalHostSummary15MinAggToVerifyImeisvInvalidTacZero() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1, TAC_EQUAL_TO_ZERO);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2, TAC_EQUAL_TO_ZERO);

        final String json = getJsonResultAgg(DATE_FROM_15MIN, DATE_TO_15MIN, TAC_EQUAL_TO_ZERO);

        final ResultTranslator<LTEHFATerminalHostResultRaw> rt = getTranslator();
        final List<LTEHFATerminalHostResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalHostSummaryDayAggToVerifyImeisvInvalidTacZero() throws Exception {
        insertDataToAggregationTable = true;
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_1, TAC_EQUAL_TO_ZERO);
        insertAggData(TEMP_EVENT_E_LTE_HFA_TAC_EVENTID_ERR_DAY, DATETIME_ID_DAY_2, TAC_EQUAL_TO_ZERO);

        final String json = getJsonResultAgg(DATE_FROM_DAY, DATE_TO_DAY, TAC_EQUAL_TO_ZERO);

        final ResultTranslator<LTEHFATerminalHostResultRaw> rt = getTranslator();
        final List<LTEHFATerminalHostResultRaw> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRaw.class);
        assertResult(eventResult);
    }

    private void assertResult(final List<LTEHFATerminalHostResultRaw> results) {
        assertThat(results.size(), is(2));
        for (final LTEHFATerminalHostResultRaw rs : results) {
            assertEquals(rs.getFailures(), 16);
            assertEquals(rs.getImapctedSubscribers(), 2);
            if (rs.getTac().equals(Integer.toString(TAC_NOT_IN_DIM))) {
                assertEquals(TERMINAL_MAKE_UNKNOWN, rs.getManufacture());
                assertEquals(TERMINAL_MODEL_UNKNOWN, rs.getModel());
            } else if (rs.getTac().equals(Integer.toString(TAC_EQUAL_TO_ZERO))) {
                assertEquals(TERMINAL_MAKE_INVALID, rs.getManufacture());
                assertEquals(TERMINAL_MODEL_INVALID, rs.getModel());
            } else if (rs.getTac().equals(Integer.toString(TEST_VALUE_TAC))) {
                assertEquals(EXPECTED_VALID_MANUFACTURER, rs.getManufacture());
                assertEquals(EXPECTED_VALID_MARKETING_NAME, rs.getModel());
            }
        }
    }

    private void assertResultForExclusiveTAC(final List<LTEHFATerminalHostResultRaw> results) {
        assertThat(results.size(), is(2));
        for (final LTEHFATerminalHostResultRaw rs : results) {
            assertEquals(64, rs.getFailures());
            assertEquals(2, rs.getImapctedSubscribers());
        }
    }
}
