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
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis.TerminalHandoverStageService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFATerminalHostResultRawGroup;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFATerminalHostServiceRawGroupTest extends BaseDataIntegrityTest<LTEHFATerminalHostResultRawGroup> {

    private TerminalHandoverStageService lteHFATerminalHandoverStageService;

    private static final String DATETIME_ID_RAW = "2011-09-20 08:12:00";

    private static final String DATE_FROM_RAW = "20092011";

    private static final String DATE_TO_RAW = "20092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final String TEST_VALUE_IMSI_1 = "123456789012345";

    private static final int TEST_VALUE_TAC_1 = 440107;

    private static final int TEST_VALUE_TAC_2 = 440195;

    private static final String TEST_GROUP_NAME = "tacGroup";

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
        insertDataRaw();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(IMSI);
        columnsForTable.add(TAC);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
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
        valuesForTable.put(TAC, TEST_VALUE_TAC_1);
        valuesForTable.put(GROUP_NAME, TEST_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TEST_VALUE_TAC_2);
        valuesForTable.put(GROUP_NAME, TEST_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, Integer.valueOf(TEST_VALUE_EXCLUSIVE_TAC));
        valuesForTable.put(GROUP_NAME, EXCLUSIVE_TAC_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

    }

    private void insertData(final int tac, final String categoryId, final String eventID, final String time, final int instances) throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryId);
            valuesForTable.put(IMSI_PARAM, TEST_VALUE_IMSI_1);
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(TAC, tac);
            valuesForTable.put(DATETIME_ID, time);
            insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
        }
    }

    private void insertDataRaw() throws Exception {

        insertData(TEST_VALUE_TAC_1, CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_1, CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_1, CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_1, CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_1, CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_1, CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_1, CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_1, CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_TAC_2, CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_2, CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_2, CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_2, CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_2, CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_2, CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_2, CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_TAC_2, CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 1);

        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 1);

        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 1);
        insertData(Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 1);
    }

    private String getJsonResult(final String groupName) throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, DATE_FROM_RAW);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, DATE_TO_RAW);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(GROUP_NAME_PARAM, groupName);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(TYPE_PARAM, TAC);

        return runQuery(lteHFATerminalHandoverStageService, requestParameters);
    }

    @Test
    public void testTerminalHostSummary() throws Exception {
        final String json = getJsonResult(TEST_GROUP_NAME);
        final ResultTranslator<LTEHFATerminalHostResultRawGroup> rt = getTranslator();
        final List<LTEHFATerminalHostResultRawGroup> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRawGroup.class);
        assertResult(eventResult);
    }

    @Test
    public void testTerminalHostSummaryForExclusiveTac() throws Exception {
        final String json = getJsonResult(EXCLUSIVE_TAC_GROUP);
        final ResultTranslator<LTEHFATerminalHostResultRawGroup> rt = getTranslator();
        final List<LTEHFATerminalHostResultRawGroup> eventResult = rt.translateResult(json, LTEHFATerminalHostResultRawGroup.class);
        assertResult(eventResult);
    }

    private void assertResult(final List<LTEHFATerminalHostResultRawGroup> results) {
        assertThat(results.size(), is(2));
        for (final LTEHFATerminalHostResultRawGroup rs : results) {
            assertEquals(rs.getFailures(), 8);
            assertEquals(rs.getImpactedSubscribers(), 1);
        }
    }
}
