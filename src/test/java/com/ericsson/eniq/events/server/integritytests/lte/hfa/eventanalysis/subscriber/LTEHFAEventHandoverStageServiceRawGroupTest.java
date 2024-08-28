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
package com.ericsson.eniq.events.server.integritytests.lte.hfa.eventanalysis.subscriber;

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
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis.LTEHandoverFailureEventHandoverStageService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHandoverFailureEventHandoverStageResultRawGroup;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFAEventHandoverStageServiceRawGroupTest extends BaseDataIntegrityTest<LTEHandoverFailureEventHandoverStageResultRawGroup> {

    private LTEHandoverFailureEventHandoverStageService lteHandoverFailureEventHandoverStageService;

    private static final String DATETIME_ID_RAW = "2011-09-20 08:12:00";

    private static final String DATE_FROM_RAW = "20092011";

    private static final String DATE_TO_RAW = "20092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final String TEST_VALUE_IMSI_1 = "123456789012345";

    private static final String DATE_FROM_AGG_DAY = "13092011";

    private static final String DATE_TO_AGG_DAY = "20092011";

    private static final String DATETIME_ID_RAW_Agg = "2011-09-18 08:12:00";

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     * 
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureEventHandoverStageService = new LTEHandoverFailureEventHandoverStageService();
        attachDependencies(lteHandoverFailureEventHandoverStageService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        createTable();
        insertTopoData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(IMSI);
        columnsForTable.add(TAC);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        //columnsForTable.add(CATEGORY_ID_2_DESC);
        columnsForTable.add(EVENT_ID);
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
        columnsForTable.add(IMSI);
        columnsForTable.add(GROUP_NAME);
        createTemporaryTable(TEMP_GROUP_TYPE_E_IMSI, columnsForTable);
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
        valuesForTable.put(IMSI, TEST_VALUE_IMSI_1);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_IMSI_GROUP);
        insertRow(TEMP_GROUP_TYPE_E_IMSI, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(IMSI, TEST_VALUE_IMSI);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_IMSI_GROUP);
        insertRow(TEMP_GROUP_TYPE_E_IMSI, valuesForTable);

    }

    private void insertData(final String imsi, final String eventID, final String categoryId, final int tac, final String time, final int instances)
            throws SQLException {
        final String localDateId = time.substring(0, 10);
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryId);
            valuesForTable.put(IMSI_PARAM, imsi);
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(TAC, tac);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(LOCAL_DATE_ID, localDateId);
            //valuesForTable.put(CATEGORY_ID_2_DESC, categoryIdDesc);
            insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
        }
    }

    private String getJsonResult(final String dateFromId, final String dateToId) throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFromId);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateToId);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(GROUP_NAME_PARAM, TEST_VALUE_IMSI_GROUP);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(TYPE_PARAM, IMSI);

        return runQuery(lteHandoverFailureEventHandoverStageService, requestParameters);
    }

    @Test
    public void testSubscriberEventHandoverStageSummary() throws Exception {

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW,
                1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC,
                DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW,
                1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC,
                DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW,
                1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC,
                DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW,
                1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC,
                DATETIME_ID_RAW, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResult(DATE_FROM_RAW, DATE_TO_RAW);
        final ResultTranslator<LTEHandoverFailureEventHandoverStageResultRawGroup> rt = getTranslator();
        final List<LTEHandoverFailureEventHandoverStageResultRawGroup> eventResult = rt.translateResult(json,
                LTEHandoverFailureEventHandoverStageResultRawGroup.class);
        assertResult(eventResult);

    }

    @Test
    public void testSubscriberEventHandoverStageSummary_WeekQuery() throws Exception {

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW_Agg, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC,
                DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW_Agg, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC,
                DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW_Agg, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC,
                DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC, DATETIME_ID_RAW_Agg, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, SAMPLE_TAC,
                DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW_Agg, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC,
                DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW_Agg, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC,
                DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW_Agg, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC,
                DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);

        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC, DATETIME_ID_RAW_Agg, 1);
        insertData(TEST_VALUE_IMSI_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, SAMPLE_TAC,
                DATETIME_ID_RAW_Agg, 1);
        insertData(Long.toString(TEST_VALUE_IMSI), INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW_Agg, 1);

        final String json = getJsonResult(DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);
        final ResultTranslator<LTEHandoverFailureEventHandoverStageResultRawGroup> rt = getTranslator();
        final List<LTEHandoverFailureEventHandoverStageResultRawGroup> eventResult = rt.translateResult(json,
                LTEHandoverFailureEventHandoverStageResultRawGroup.class);
        assertResult(eventResult);

    }

    private void assertResult(final List<LTEHandoverFailureEventHandoverStageResultRawGroup> results) {
        assertThat(results.size(), is(2));
        for (final LTEHandoverFailureEventHandoverStageResultRawGroup rs : results) {
            assertEquals("16", rs.getNoOfErrors());
            assertEquals("2", rs.getImpactedSubscribers());
        }
    }
}
