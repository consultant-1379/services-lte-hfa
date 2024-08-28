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
package com.ericsson.eniq.events.server.integritytests.lte.hfa.eventvolume.ecell;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.EventIDConstants.*;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;
import static com.ericsson.eniq.events.server.test.temptables.TempTableNames.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventvolume.LTEHFAEcellEventVolumeService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFAEventVolumeResult;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFAEcellGroupEventVolumeAggTest extends BaseDataIntegrityTest<LTEHFAEventVolumeResult> {
    private LTEHFAEcellEventVolumeService lteHFAEcellEventVolumeService;

    private static final String DATETIME_ID_DAY_1 = "2011-09-19 00:00:00";

    private static final String DATETIME_ID_DAY_2 = "2011-09-18 00:00:00";

    private static final String DATE_FROM_DAY = "13092011";

    private static final String DATE_TO_DAY = "20092011";

    private static final String DATETIME_ID_15MIN_1 = "2011-09-20 08:15:00";

    private static final String DATETIME_ID_15MIN_2 = "2011-09-20 12:15:00";

    private static final String DATE_FROM_15MIN = "20092011";

    private static final String DATE_TO_15MIN = "20092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "1530";

    private static final String TEST_VALUE_IMSI = "11111119";

    private static final long TEST_VALUE_HIER321_ID_1 = 3135210477467174988L;

    private static final long TEST_VALUE_HIER321_ID_2 = 4809532081614999117L;

    private static final String TEST_VALUE_GROUP_NAME = "eCellGroup";

    /**
     * 1.Create tables. 2.Insert test datas to the tables.
     * 
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHFAEcellEventVolumeService = new LTEHFAEcellEventVolumeService();
        attachDependencies(lteHFAEcellEventVolumeService);
        createTables();
        insertTopoData();
    }

    /**
     * Create the prepare test tables for testing. raw table: EVENT_E_LTE_HFA_RAW
     * 
     * @throws Exception
     */
    private void createTables() throws Exception {

        final Collection<String> columnsForEETable = new ArrayList<String>();
        columnsForEETable.add(EVENT_ID);
        columnsForEETable.add(DATETIME_ID);
        columnsForEETable.add(NO_OF_ERRORS);
        columnsForEETable.add(HIER321_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_15MIN, columnsForEETable);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_DAY, columnsForEETable);

        columnsForEETable.clear();
        columnsForEETable.add(EVENT_ID);
        columnsForEETable.add(DATETIME_ID);
        columnsForEETable.add(LOCAL_DATE_ID);
        columnsForEETable.add(TAC);
        columnsForEETable.add(IMSI);
        columnsForEETable.add(HIER321_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ERR_RAW, columnsForEETable);

        columnsForEETable.clear();
        columnsForEETable.add(HIER321_ID);
        columnsForEETable.add(GROUP_NAME);
        createTemporaryTable(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, columnsForEETable);
    }

    private void insertTopoData() throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();

        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_1);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_2);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, valuesForTable);
    }

    private void insertData(final long hier321ID, final String tableName, final String eventTime) throws SQLException {

        insertEvent(hier321ID, tableName, eventTime, INTERNAL_PROC_HO_PREP_X2_IN_HFA, 3);
        insertEvent(hier321ID, tableName, eventTime, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, 2);
        insertEvent(hier321ID, tableName, eventTime, INTERNAL_PROC_HO_PREP_S1_IN_HFA, 2);
        insertEvent(hier321ID, tableName, eventTime, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, 2);
        insertEvent(hier321ID, tableName, eventTime, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, 2);
        insertEvent(hier321ID, tableName, eventTime, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, 2);
        insertEvent(hier321ID, tableName, eventTime, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, 2);
        insertEvent(hier321ID, tableName, eventTime, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, 2);

    }

    private void insertEvent(final long hier321ID, final String tableName, final String dt, final String eventID, final int instances)
            throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(EVENT_ID, eventID);
        valuesForTable.put(DATETIME_ID, dt);
        valuesForTable.put(NO_OF_ERRORS, instances);
        valuesForTable.put(HIER321_ID, hier321ID);
        insertRow(tableName, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(DATETIME_ID, dt);
        valuesForTable.put(LOCAL_DATE_ID, dt.substring(0, 10));
        valuesForTable.put(EVENT_ID, eventID);
        valuesForTable.put(IMSI, TEST_VALUE_IMSI);
        valuesForTable.put(TAC, TEST_VALUE_TAC);
        valuesForTable.put(HIER321_ID, hier321ID);
        insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
    }

    private String getJsonResult(final String dateFrom, final String dateTo) {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(TYPE_PARAM, TYPE_CELL);
        requestParameters.putSingle(GROUP_NAME_PARAM, TEST_VALUE_GROUP_NAME);

        return runQuery(lteHFAEcellEventVolumeService, requestParameters);
    }

    private void assertResult(final List<LTEHFAEventVolumeResult> results) {
        assertThat("There should be exactly 2 results!", results.size(), is(2));
        for (final LTEHFAEventVolumeResult result : results) {
            assertThat(result.getINTERNAL_PROC_HO_PREP_X2_IN_FailCount(), is(6));
            assertThat(result.getINTERNAL_PROC_HO_PREP_X2_OUT_FailCount(), is(4));
            assertThat(result.getINTERNAL_PROC_HO_EXEC_X2_IN_FailCount(), is(4));
            assertThat(result.getINTERNAL_PROC_HO_EXEC_X2_OUT_FailCount(), is(4));
            assertThat(result.getINTERNAL_PROC_HO_PREP_S1_IN_FailCount(), is(4));
            assertThat(result.getINTERNAL_PROC_HO_PREP_S1_OUT_FailCount(), is(4));
            assertThat(result.getINTERNAL_PROC_HO_EXEC_S1_IN_FailCount(), is(4));
            assertThat(result.getINTERNAL_PROC_HO_EXEC_S1_OUT_FailCount(), is(4));
            assertThat(result.getImpactedSubscribers(), is(1));
            String resultEventTime = result.getTime();
            if (resultEventTime.equalsIgnoreCase(EXPECTED_DATETIME_ID_15MIN_1) || resultEventTime.equalsIgnoreCase(EXPECTED_DATETIME_ID_15MIN_2)
                    || resultEventTime.equalsIgnoreCase(EXPECTED_DATETIME_ID_DAY_1) || resultEventTime.equalsIgnoreCase(EXPECTED_DATETIME_ID_DAY_2)) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    }

    @Test
    public void testNetworkEventVolume15MIN() throws Exception {
        insertData(TEST_VALUE_HIER321_ID_1, TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertData(TEST_VALUE_HIER321_ID_1, TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        insertData(TEST_VALUE_HIER321_ID_2, TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_1);
        insertData(TEST_VALUE_HIER321_ID_2, TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_15MIN, DATETIME_ID_15MIN_2);

        final String json = getJsonResult(DATE_FROM_15MIN, DATE_TO_15MIN);

        final ResultTranslator<LTEHFAEventVolumeResult> rt = getTranslator();
        final List<LTEHFAEventVolumeResult> results = rt.translateResult(json, LTEHFAEventVolumeResult.class);
        assertResult(results);
    }

    @Test
    public void testNetworkEventVolumeDay() throws Exception {
        insertData(TEST_VALUE_HIER321_ID_1, TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertData(TEST_VALUE_HIER321_ID_1, TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        insertData(TEST_VALUE_HIER321_ID_2, TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_DAY, DATETIME_ID_DAY_1);
        insertData(TEST_VALUE_HIER321_ID_2, TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_DAY, DATETIME_ID_DAY_2);

        final String json = getJsonResult(DATE_FROM_DAY, DATE_TO_DAY);

        final ResultTranslator<LTEHFAEventVolumeResult> rt = getTranslator();
        final List<LTEHFAEventVolumeResult> results = rt.translateResult(json, LTEHFAEventVolumeResult.class);
        assertResult(results);
    }
}
