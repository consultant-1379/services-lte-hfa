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
package com.ericsson.eniq.events.server.integritytests.lte.hfa.networkeventvolume;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.EventIDConstants.*;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;
import static com.ericsson.eniq.events.server.test.temptables.TempTableNames.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URISyntaxException;
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
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventvolume.LTEHFANetworkEventVolumeService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFANetworkEventVolumeResult;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFANetworkEventVolServiceAggTest extends BaseDataIntegrityTest<LTEHFANetworkEventVolumeResult> {

    private LTEHFANetworkEventVolumeService lteHFANetworkEventVolumeService;

    private static final String DATETIME_ID_DAY_1 = "2011-09-19 08:15:00";

    private static final String DATETIME_ID_DAY_2 = "2011-09-18 08:15:00";

    private static final String DATE_FROM_DAY = "13092011";

    private static final String DATE_TO_DAY = "20092011";

    private static final String DATETIME_ID_15MIN_1 = "2011-09-20 08:15:00";

    private static final String DATETIME_ID_15MIN_2 = "2011-09-20 12:15:00";

    private static final String DATE_FROM_15MIN = "20092011";

    private static final String DATE_TO_15MIN = "20092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "1530";

    /**
     * 1.Create tables. 2.Insert test datas to the tables.
     * 
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHFANetworkEventVolumeService = new LTEHFANetworkEventVolumeService();
        attachDependencies(lteHFANetworkEventVolumeService);
        createTable();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.clear();
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, columnsForTable);
    }

    private void insertEventVolData(final String aggTable, final String eventID, final String time, final int noOfErrors, final int instances)
            throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(NO_OF_ERRORS, noOfErrors);
            valuesForTable.put(DATETIME_ID, time);
            insertRow(aggTable, valuesForTable);
        }
    }

    private String getJsonResultDay() throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, DATE_FROM_DAY);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, DATE_TO_DAY);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        return runQuery(lteHFANetworkEventVolumeService, requestParameters);
    }

    private String getJsonResult15Min() throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, DATE_FROM_15MIN);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, DATE_TO_15MIN);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        return runQuery(lteHFANetworkEventVolumeService, requestParameters);
    }

    private void insertAggDataDay() throws Exception {

        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATETIME_ID_DAY_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_DAY_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATETIME_ID_DAY_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_DAY_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_DAY_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_DAY_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_DAY_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_DAY_1, 1, 20);

        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATETIME_ID_DAY_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_DAY_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATETIME_ID_DAY_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_DAY_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_DAY_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_DAY_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_DAY_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_DAY_2, 1, 20);
    }

    private void insertAggData15Min() throws Exception {

        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATETIME_ID_15MIN_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_15MIN_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATETIME_ID_15MIN_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_15MIN_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_15MIN_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_15MIN_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_15MIN_1, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_15MIN_1, 1, 20);

        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATETIME_ID_15MIN_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_15MIN_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATETIME_ID_15MIN_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_15MIN_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_15MIN_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_15MIN_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_15MIN_2, 1, 20);
        insertEventVolData(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_15MIN_2, 1, 20);
    }

    @Test
    public void testNetworkEventVolumeDay() throws Exception {
        insertAggDataDay();
        final String json = getJsonResultDay();
        final ResultTranslator<LTEHFANetworkEventVolumeResult> rt = getTranslator();
        final List<LTEHFANetworkEventVolumeResult> eventResult = rt.translateResult(json, LTEHFANetworkEventVolumeResult.class);
        assertResult(eventResult);
    }

    @Test
    public void testNetworkEventVolume15min() throws Exception {
        insertAggData15Min();
        final String json = getJsonResult15Min();
        final ResultTranslator<LTEHFANetworkEventVolumeResult> rt = getTranslator();
        final List<LTEHFANetworkEventVolumeResult> eventResult = rt.translateResult(json, LTEHFANetworkEventVolumeResult.class);
        assertResult(eventResult);
    }

    private void assertResult(final List<LTEHFANetworkEventVolumeResult> results) {
        assertThat(results.size(), is(2));
        for (final LTEHFANetworkEventVolumeResult rs : results) {
            assertThat(rs.getINTERNAL_PROC_HO_PREP_X2_IN_FailCount(), is(20));
            assertThat(rs.getINTERNAL_PROC_HO_PREP_X2_OUT_FailCount(), is(20));
            assertThat(rs.getINTERNAL_PROC_HO_EXEC_X2_IN_FailCount(), is(20));
            assertThat(rs.getINTERNAL_PROC_HO_EXEC_X2_OUT_FailCount(), is(20));
            assertThat(rs.getINTERNAL_PROC_HO_PREP_S1_IN_FailCount(), is(20));
            assertThat(rs.getINTERNAL_PROC_HO_PREP_S1_OUT_FailCount(), is(20));
            assertThat(rs.getINTERNAL_PROC_HO_EXEC_S1_IN_FailCount(), is(20));
            assertThat(rs.getINTERNAL_PROC_HO_EXEC_S1_OUT_FailCount(), is(20));
            String resultEventTime = rs.getTime();
            if (resultEventTime.equalsIgnoreCase(EXPECTED_DATETIME_ID_15MIN_1) || resultEventTime.equalsIgnoreCase(EXPECTED_DATETIME_ID_15MIN_2)
                    || resultEventTime.equalsIgnoreCase(EXPECTED_DATETIME_ID_DAY_1) || resultEventTime.equalsIgnoreCase(EXPECTED_DATETIME_ID_DAY_2)) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    }
}
