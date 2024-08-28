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
package com.ericsson.eniq.events.server.integritytests.lte.hfa.qos.qcianalysis.ecell;

import com.ericsson.eniq.events.server.common.EventIDConstants;
import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis.EcellQCIEventSummaryService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.qos.LTEHFAQOSEcellGroupQCIEventSummaryResult;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;
import static com.ericsson.eniq.events.server.test.temptables.TempTableNames.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EcellGroupQCIEventSummaryServiceAggTest extends BaseDataIntegrityTest<LTEHFAQOSEcellGroupQCIEventSummaryResult> {
    EcellQCIEventSummaryService lteHFAEcellQCIEventSummaryService;

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

    private static final long TEST_VALUE_HIER321_ID_1 = 3135210477467174988L;

    private static final long TEST_VALUE_HIER321_ID_2 = 4809532081614999117L;

    private static final String TEST_VALUE_IMSI_1 = "11111119";

    private static final String TEST_VALUE_IMSI_2 = "11111118";

    private static final int TEST_VALUE_QCI_ID = 1;

    private static final int CATEGORY_ID_2_PREP = 0;

    private static final int CATEGORY_ID_2_EXEC = 1;

    private static final String TEST_VALUE_GROUP_NAME = "eCellGroup";

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     * 
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHFAEcellQCIEventSummaryService = new EcellQCIEventSummaryService();
        attachDependencies(lteHFAEcellQCIEventSummaryService);
        createTable();
        insertTopoData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(IMSI);
        columnsForTable.add(CATEGORY_ID_2);
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(QCI_ID_COLUMN);
        columnsForTable.add(TAC);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(LOCAL_DATE_ID);
        columnsForTable.add(EVENT_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_ERR_RAW, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(QCI_ID_COLUMN);
        columnsForTable.add(CATEGORY_ID_2);
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(EVENT_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_15MIN, columnsForTable);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_DAY, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(EVENT_ID_DESC);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_EVENTTYPE, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(GROUP_NAME);
        createTemporaryTable(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, columnsForTable);

    }

    private void insertTopoData() throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();

        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_INITIAL_CTXT_SETUP);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_INITIAL_CTXT_SETUP");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_ERAB_SETUP);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_ERAB_SETUP");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_UE_CTXT_RELEASE);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_UE_CTXT_RELEASE");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TEST_VALUE_EXCLUSIVE_TAC);
        valuesForTable.put(GROUP_NAME, EXCLUSIVE_TAC_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_1);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_2);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, valuesForTable);
    }

    private void insertData(final long hier321ID, final int tac, final int categoryID, final String eventID, final String time, final int instances,
                            final String imsi) throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(TAC, tac);
            valuesForTable.put(IMSI, imsi);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(LOCAL_DATE_ID, time.substring(0, 10));
            valuesForTable.put(CATEGORY_ID_2, categoryID);
            valuesForTable.put(HIER321_ID, hier321ID);
            valuesForTable.put(QCI_ID_COLUMN, TEST_VALUE_QCI_ID);
            valuesForTable.put(EVENT_ID, eventID);
            insertRow(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_ERR_RAW, valuesForTable);
        }
    }

    private void insertAggData(final String aggTable, final long hier321ID, final int categoryID, final String eventID, final String time,
                               final int instances) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(QCI_ID_COLUMN, TEST_VALUE_QCI_ID);
        valuesForTable.put(CATEGORY_ID_2, categoryID);
        valuesForTable.put(HIER321_ID, hier321ID);
        valuesForTable.put(NO_OF_ERRORS, instances);
        valuesForTable.put(DATETIME_ID, time);
        valuesForTable.put(EVENT_ID, eventID);
        insertRow(aggTable, valuesForTable);
    }

    private void insertDataForAggTest(final long hier321ID, final int categoryID, final String eventID, final int instances, final String dateTimeID,
                                      final String aggTable) throws SQLException {
        insertData(hier321ID, SAMPLE_TAC, categoryID, eventID, dateTimeID, instances, TEST_VALUE_IMSI_1);
        insertData(hier321ID, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), categoryID, eventID, dateTimeID, instances, TEST_VALUE_IMSI_1);
        insertData(hier321ID, SAMPLE_TAC, categoryID, eventID, dateTimeID, instances, TEST_VALUE_IMSI_2);
        insertData(hier321ID, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), categoryID, eventID, dateTimeID, instances, TEST_VALUE_IMSI_2);
        insertAggData(aggTable, hier321ID, categoryID, eventID, dateTimeID, instances);
    }

    private void insertAggData(final String aggTable, final String dateTimeID) throws Exception {
        insertDataForAggTest(TEST_VALUE_HIER321_ID_1, CATEGORY_ID_2_PREP, EventIDConstants.INTERNAL_PROC_UE_CTXT_RELEASE, 2, dateTimeID, aggTable);
        insertDataForAggTest(TEST_VALUE_HIER321_ID_1, CATEGORY_ID_2_EXEC, EventIDConstants.INTERNAL_PROC_ERAB_SETUP, 2, dateTimeID, aggTable);
        insertDataForAggTest(TEST_VALUE_HIER321_ID_1, CATEGORY_ID_2_EXEC, EventIDConstants.INTERNAL_PROC_INITIAL_CTXT_SETUP, 2, dateTimeID, aggTable);

        insertDataForAggTest(TEST_VALUE_HIER321_ID_2, CATEGORY_ID_2_PREP, EventIDConstants.INTERNAL_PROC_UE_CTXT_RELEASE, 2, dateTimeID, aggTable);
        insertDataForAggTest(TEST_VALUE_HIER321_ID_2, CATEGORY_ID_2_EXEC, EventIDConstants.INTERNAL_PROC_ERAB_SETUP, 2, dateTimeID, aggTable);
        insertDataForAggTest(TEST_VALUE_HIER321_ID_2, CATEGORY_ID_2_EXEC, EventIDConstants.INTERNAL_PROC_INITIAL_CTXT_SETUP, 2, dateTimeID, aggTable);
    }

    private String getJsonResultAgg(final String dateFrom, final String dateTo, final int categoryID) throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(QCI_ID_COLUMN, String.valueOf(TEST_VALUE_QCI_ID));
        requestParameters.putSingle(CATEGORY_ID_2, String.valueOf(categoryID));
        requestParameters.putSingle(GROUP_NAME_PARAM, TEST_VALUE_GROUP_NAME);
        return runQuery(lteHFAEcellQCIEventSummaryService, requestParameters);
    }

    @Test
    public void testEcellGroupQCIEventSummary15MinAggPrep() throws Exception {
        insertAggData(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResultAgg(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_PREP);

        final ResultTranslator<LTEHFAQOSEcellGroupQCIEventSummaryResult> rt = getTranslator();
        final List<LTEHFAQOSEcellGroupQCIEventSummaryResult> eventResult = rt.translateResult(json, LTEHFAQOSEcellGroupQCIEventSummaryResult.class);
        assertThat(eventResult.size(), is(1));
    }

    @Test
    public void testEcellGroupQCIEventSummary15MinAggExec() throws Exception {
        insertAggData(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResultAgg(DATE_FROM_15MIN, DATE_TO_15MIN, CATEGORY_ID_2_EXEC);

        final ResultTranslator<LTEHFAQOSEcellGroupQCIEventSummaryResult> rt = getTranslator();
        final List<LTEHFAQOSEcellGroupQCIEventSummaryResult> eventResult = rt.translateResult(json, LTEHFAQOSEcellGroupQCIEventSummaryResult.class);
        assertThat(eventResult.size(), is(2));
    }

    @Test
    public void testEcellGroupQCIEventSummaryDayAggPrep() throws Exception {
        insertAggData(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResultAgg(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_PREP);

        final ResultTranslator<LTEHFAQOSEcellGroupQCIEventSummaryResult> rt = getTranslator();
        final List<LTEHFAQOSEcellGroupQCIEventSummaryResult> eventResult = rt.translateResult(json, LTEHFAQOSEcellGroupQCIEventSummaryResult.class);
        assertThat(eventResult.size(), is(1));
    }

    @Test
    public void testEcellGroupQCIEventSummaryDayAggExec() throws Exception {
        insertAggData(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_DAY, DATETIME_ID_DAY_1);
        insertAggData(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_HIER321_EVENTID_QCI_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResultAgg(DATE_FROM_DAY, DATE_TO_DAY, CATEGORY_ID_2_EXEC);

        final ResultTranslator<LTEHFAQOSEcellGroupQCIEventSummaryResult> rt = getTranslator();
        final List<LTEHFAQOSEcellGroupQCIEventSummaryResult> eventResult = rt.translateResult(json, LTEHFAQOSEcellGroupQCIEventSummaryResult.class);
        assertThat(eventResult.size(), is(2));
    }
}
