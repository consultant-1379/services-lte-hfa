/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.integritytests.lte.hfa.eventanalysis.handoverstage.eCell;

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

import com.ericsson.eniq.events.server.common.EventIDConstants;
import com.ericsson.eniq.events.server.integritytests.stubs.ReplaceTablesWithTempTablesTemplateUtils;
import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis.LTEHandoverFailureECellEventSummaryService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author evijred
 * @since 2011
 *
 */
public class LTEHFAEcellEventSummaryServiceRawTest extends BaseDataIntegrityTest<LTEHFAEcellEventSummaryResultRaw> {

    private LTEHandoverFailureECellEventSummaryService lteHandoverFailureEcellEventTypeService;

    private static final String DATETIME_ID_RAW = "2011-11-14 08:12:00";

    private static final String DATE_FROM_RAW = "14112011";

    private static final String DATE_TO_RAW = "14112011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final long TEST_VALUE_HIER321_ID = 5345046159527374400L;

    private static final String TEST_VALUE_NODE_PARAM = "ONRM_RootMo_R:LTE01ERBS00001,Ericsson,LTE";

    private static final String TEST_VALUE_HIERARCHY_3 = "ECELL";

    private static final String TEST_VALUE_HIERARCHY_1 = "LTE01ERBS00001-2";

    private static final String TEST_VALUE_VENDOR = "ERICSSON";

    private static final String TEST_VALUE_IMSI = "11111119";

    /**
     * 1. Create tables.
     * 2. Insert test datas to the tables.
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureEcellEventTypeService = new LTEHandoverFailureECellEventSummaryService();
        attachDependencies(lteHandoverFailureEcellEventTypeService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        createTable();
        insertTopoData();
        insertRawData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(IMSI);
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(TAC);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
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
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(HIERARCHY_3);
        columnsForTable.add(HIERARCHY_1);
        columnsForTable.add(VENDOR);
        createTemporaryTable(TEMP_DIM_E_LTE_HIER321, columnsForTable);
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
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID);
        valuesForTable.put(HIERARCHY_3, TEST_VALUE_HIERARCHY_3);
        valuesForTable.put(HIERARCHY_1, TEST_VALUE_HIERARCHY_1);
        valuesForTable.put(VENDOR, TEST_VALUE_VENDOR);
        insertRow(TEMP_DIM_E_LTE_HIER321, valuesForTable);
    }

    private void insertData(final long hier3Id, final String eventID, final String categoryId, final int tac,
            final String time, final int instances) throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(HIER321_ID, hier3Id);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryId);
            valuesForTable.put(TAC, tac);
            valuesForTable.put(IMSI_PARAM, TEST_VALUE_IMSI);
            valuesForTable.put(DATETIME_ID, time);
            insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
        }
    }

    private String getJsonResult(final boolean isRankingDrillDown, final String categoryId) throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, DATE_FROM_RAW);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, DATE_TO_RAW);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(CATEGORY_ID_SQL_PARAM, categoryId);

        // if (isRankingDrillDown) {
        requestParameters.putSingle(HIER321_ID, Long.toString(TEST_VALUE_HIER321_ID));
        // } else {
        // requestParameters.putSingle(NODE_PARAM, TEST_VALUE_NODE_PARAM);
        //}
        return runQuery(lteHandoverFailureEcellEventTypeService, requestParameters);
    }

    private void insertRawData() throws Exception {
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                SAMPLE_TAC, DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                SAMPLE_TAC, DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                SAMPLE_TAC, DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                SAMPLE_TAC, DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                SAMPLE_TAC, DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                SAMPLE_TAC, DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                SAMPLE_TAC, DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                SAMPLE_TAC, DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER321_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    }

    @Test
    public void testEcellBEventSummaryPrep() throws Exception {
        final String json = getJsonResult(false, EventIDConstants.CATEGORY_ID_2_PREP);
        final ResultTranslator<LTEHFAEcellEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFAEcellEventSummaryResultRaw> eventResult = rt.translateResult(json,
                LTEHFAEcellEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testEcellBEventSummaryExec() throws Exception {
        final String json = getJsonResult(false, EventIDConstants.CATEGORY_ID_2_EXEC);
        final ResultTranslator<LTEHFAEcellEventSummaryResultRaw> rt = getTranslator();
        final List<LTEHFAEcellEventSummaryResultRaw> eventResult = rt.translateResult(json,
                LTEHFAEcellEventSummaryResultRaw.class);
        assertResult(eventResult);
    }

    //    @Test
    //    public void testEcellEventSummaryRankingDrilldownPrep() throws Exception {
    //        final String json = getJsonResult(true, EventIDConstants.CATEGORY_ID_2_PREP);
    //        final ResultTranslator<LTEHandoverFailureEcellEventSummaryResultRaw> rt = getTranslator();
    //        final List<LTEHandoverFailureEcellEventSummaryResultRaw> eventResult = rt.translateResult(json,
    //                LTEHandoverFailureEcellEventSummaryResultRaw.class);
    //        assertResult(eventResult);
    //    }
    //    
    //    @Test
    //    public void testEnodeBEventSummaryRankingDrilldownExec() throws Exception {
    //        final String json = getJsonResult(true, EventIDConstants.CATEGORY_ID_2_EXEC);
    //        final ResultTranslator<LTEHandoverFailureEcellEventSummaryResultRaw> rt = getTranslator();
    //        final List<LTEHandoverFailureEcellEventSummaryResultRaw> eventResult = rt.translateResult(json,
    //                LTEHandoverFailureEcellEventSummaryResultRaw.class);
    //        assertResult(eventResult);
    //    }

    private void assertResult(final List<LTEHFAEcellEventSummaryResultRaw> results) {
        assertThat(results.size(), is(4));
        for (final LTEHFAEcellEventSummaryResultRaw rs : results) {
            assertEquals(rs.getNoOfErrors(), "2");
        }
    }
}