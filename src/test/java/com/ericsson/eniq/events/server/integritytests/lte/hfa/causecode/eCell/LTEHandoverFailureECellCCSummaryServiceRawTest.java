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

package com.ericsson.eniq.events.server.integritytests.lte.hfa.causecode.eCell;

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
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.causecodeanalysis.LTEHandoverFailureEcellCauseCodeSummaryService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHandoverFailureECellCCSummaryResultRaw;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHandoverFailureECellCCSummaryServiceRawTest extends BaseDataIntegrityTest<LTEHandoverFailureECellCCSummaryResultRaw> {

    private LTEHandoverFailureEcellCauseCodeSummaryService lteHandoverFailureECellCauseCodeSummaryService;

    private static final String DATETIME_ID_RAW = "2011-09-20 08:12:00";

    private static final String DATE_FROM_RAW = "20092011";

    private static final String DATE_TO_RAW = "20092011";

    private static final String DATE_FROM_AGG = "14092011";

    private static final String DATE_TO_AGG = "21092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final long TEST_VALUE_HIER321_ID_1 = 5345046159527374400L;

    private static final String TEST_VALUE_NODE_PARAM = "LTE01ERBS00001-2,,ONRM_RootMo_R:LTE01ERBS00001,Ericsson,LTE";

    private static final String TEST_VALUE_IMSI = "11111119";

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     * 
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureECellCauseCodeSummaryService = new LTEHandoverFailureEcellCauseCodeSummaryService();
        attachDependencies(lteHandoverFailureECellCauseCodeSummaryService);
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
        columnsForTable.add(CAUSE_CODE_COLUMN);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(LOCAL_DATE_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ERR_RAW, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(EVENT_ID_DESC);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(CATEGORY_ID_2_DESC);
        columnsForTable.add(CAUSE_CODE_COLUMN);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_EVENTTYPE, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(VENDOR_NAME);
        columnsForTable.add(MARKETING_NAME);
        columnsForTable.add(TAC);
        createTemporaryTable(TEMP_DIM_E_SGEH_TAC, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(CAUSE_CODE_COLUMN);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(CAUSE_CODE_DESC_COLUMN);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, columnsForTable);
    }

    private void insertTopoData() throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();

        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_S1_IN");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_EXEC);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.EXECUTION_CATEGORY_ID_2_DESC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_S1_OUT");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_EXEC);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.EXECUTION_CATEGORY_ID_2_DESC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_X2_IN");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_EXEC);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.EXECUTION_CATEGORY_ID_2_DESC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_X2_OUT");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_EXEC);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.EXECUTION_CATEGORY_ID_2_DESC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_S1_IN");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_PREP);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.PREPARATION_CATEGORY_ID_2_DESC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_S1_OUT");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_PREP);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.PREPARATION_CATEGORY_ID_2_DESC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_X2_IN");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_PREP);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.PREPARATION_CATEGORY_ID_2_DESC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_X2_OUT");
        valuesForTable.put(CATEGORY_ID_SQL_PARAM, EventIDConstants.CATEGORY_ID_2_PREP);
        valuesForTable.put(CATEGORY_ID_2_DESC, EventIDConstants.PREPARATION_CATEGORY_ID_2_DESC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, Integer.valueOf(TEST_VALUE_EXCLUSIVE_TAC));
        valuesForTable.put(GROUP_NAME, EXCLUSIVE_TAC_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);
    }

    private void insertData(final long hier321Id, final String eventID, final String causeCD, final int tac, final String time, final int instances)
            throws SQLException {
        for (int i = 0; i < instances; i++) {
            final String localDateId = time.substring(0, 10);
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(HIER321_ID, hier321Id);
            valuesForTable.put(CAUSE_CODE_COLUMN, causeCD);
            valuesForTable.put(TAC, tac);
            valuesForTable.put(IMSI_PARAM, TEST_VALUE_IMSI);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(LOCAL_DATE_ID, localDateId);
            insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
        }
    }

    private String getJsonResult(final String dateFrom, final String dateTo) throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(NODE_PARAM, TEST_VALUE_NODE_PARAM);
        requestParameters.putSingle(TYPE_PARAM, TYPE_CELL);

        return runQuery(lteHandoverFailureECellCauseCodeSummaryService, requestParameters);
    }

    private void insertRawData() throws Exception {

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER321_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC),
                DATETIME_ID_RAW, 1);
    }

    @Test
    public void testECellCCSummaryRaw() throws Exception {
        final String json = getJsonResult(DATE_FROM_RAW, DATE_TO_RAW);
        final ResultTranslator<LTEHandoverFailureECellCCSummaryResultRaw> rt = getTranslator();
        final List<LTEHandoverFailureECellCCSummaryResultRaw> eventResult = rt.translateResult(json, LTEHandoverFailureECellCCSummaryResultRaw.class);
        assertResult(eventResult);
    }

    @Test
    public void testECellCCSummaryAgg() throws Exception {
        final String json = getJsonResult(DATE_FROM_AGG, DATE_TO_AGG);
        final ResultTranslator<LTEHandoverFailureECellCCSummaryResultRaw> rt = getTranslator();
        final List<LTEHandoverFailureECellCCSummaryResultRaw> eventResult = rt.translateResult(json, LTEHandoverFailureECellCCSummaryResultRaw.class);
        assertResult(eventResult);
    }

    private void assertResult(final List<LTEHandoverFailureECellCCSummaryResultRaw> results) {
        assertThat(results.size(), is(8));
        for (final LTEHandoverFailureECellCCSummaryResultRaw rs : results) {
            assertEquals(rs.getNoOfErrors(), "2");
            assertEquals(rs.getImpactedSubscribers(), "1");
        }
    }
}