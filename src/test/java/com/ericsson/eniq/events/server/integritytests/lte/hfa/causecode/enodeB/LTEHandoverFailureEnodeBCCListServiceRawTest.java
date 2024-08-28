/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.integritytests.lte.hfa.causecode.enodeB;

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
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.causecodeanalysis.LTEHandoverFailureEnodeBCauseCodeListService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHandoverFailureEnodeBCCListResult;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author eeiyspn
 * @since 2011
 * 
 */
public class LTEHandoverFailureEnodeBCCListServiceRawTest extends
        BaseDataIntegrityTest<LTEHandoverFailureEnodeBCCListResult> {

    private LTEHandoverFailureEnodeBCauseCodeListService lteHandoverFailureEnodeBCauseCodeListService;

    private static final String DATETIME_ID_RAW = "2011-09-20 08:12:00";

    private static final String DATE_FROM_RAW = "20092011";

    private static final String DATE_TO_RAW = "20092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final long TEST_VALUE_HIER3_ID = 3135210477467174988L;

    private static final String TEST_VALUE_NODE_PARAM = "ONRM_RootMo_R:LTE01ERBS00001,Ericsson,LTE";

    /**
     * 1. Create tables. 2. Insert test data to the tables.
     * 
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureEnodeBCauseCodeListService = new LTEHandoverFailureEnodeBCauseCodeListService();
        attachDependencies(lteHandoverFailureEnodeBCauseCodeListService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        createTable();
        insertTopoData();
        insertRawData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(CAUSE_CODE_COLUMN);
        columnsForTable.add(TAC);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ERR_RAW, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(EVENT_ID_DESC);
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
        columnsForTable.add(CAUSE_CODE_DESC_PIE);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, columnsForTable);
    }

    private void insertTopoData() throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();

        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_S1_IN");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_S1_OUT");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_X2_IN");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_X2_OUT");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_S1_IN");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_S1_OUT");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_X2_IN");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
        valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_X2_OUT");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_IN_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_OUT_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_IN_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_OUT_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

    }

    private void insertData(final long hier3Id, final String eventID, final String causeCD, final int tac,
            final String time) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(HIER3_ID, hier3Id);
        valuesForTable.put(EVENT_ID, eventID);
        valuesForTable.put(CAUSE_CODE_COLUMN, causeCD);
        valuesForTable.put(TAC, tac);
        valuesForTable.put(DATETIME_ID, time);
        insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
    }

    private String getJsonResult() throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, DATE_FROM_RAW);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, DATE_TO_RAW);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(TYPE_PARAM, TYPE_BSC);
        requestParameters.putSingle(NODE_PARAM, TEST_VALUE_NODE_PARAM);
        return runQuery(lteHandoverFailureEnodeBCauseCodeListService, requestParameters);
    }

    private void insertRawData() throws Exception {
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE, SAMPLE_TAC,
                DATETIME_ID_RAW);
        insertData(TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_CAUSE_CODE,
                Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW);

    }

    @Test
    public void testEnodeBCauseCodeList() throws Exception {
        final String json = getJsonResult();
        final ResultTranslator<LTEHandoverFailureEnodeBCCListResult> rt = getTranslator();
        final List<LTEHandoverFailureEnodeBCCListResult> eventResult = rt.translateResult(json,
                LTEHandoverFailureEnodeBCCListResult.class);
        assertThat(eventResult.size(), is(8));
    }
}