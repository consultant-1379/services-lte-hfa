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
 * @author evijred
 * @since 2011
 *
 */
public class LTEHandoverFailureEnodeBCCListServiceAggTest extends
        BaseDataIntegrityTest<LTEHandoverFailureEnodeBCCListResult> {

    private LTEHandoverFailureEnodeBCauseCodeListService lteHandoverFailureEnodeBCauseCodeListService;

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

    private static final long TEST_VALUE_HIER3_ID = 3135210477467174988L;

    //private static final long TEST_VALUE_HIER3_ID_1 = 3135210477467174988L;

    private static final String TEST_VALUE_NODE_PARAM = "ONRM_RootMo_R:LTE01ERBS00001,Ericsson,LTE";

    /**
     * 1. Create tables.
     * 2. Insert test datas to the tables.
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureEnodeBCauseCodeListService = new LTEHandoverFailureEnodeBCauseCodeListService();
        attachDependencies(lteHandoverFailureEnodeBCauseCodeListService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        createTable();
        insertTopoData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();

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
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(CAUSE_CODE_COLUMN);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(CAUSE_CODE_COLUMN);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, columnsForTable);

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
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_IN_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_EXEC_X2_OUT_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_IN_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_PREP_S1_OUT_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        valuesForTable.put(CAUSE_CODE_DESC_PIE, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA
                + TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

    }

    private void insertHierCCData(final String aggTable, final long hier3Id, final String eventID,
            final String categoryId, final String time, final int instances, final int noOfErrors) throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryId);
            valuesForTable.put(HIER3_ID, hier3Id);
            valuesForTable.put(NO_OF_ERRORS, noOfErrors);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
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
        requestParameters.putSingle(TYPE_PARAM, TYPE_BSC);
        requestParameters.putSingle(NODE_PARAM, TEST_VALUE_NODE_PARAM);
        return runQuery(lteHandoverFailureEnodeBCauseCodeListService, requestParameters);
    }

    private String getJsonResult15MIN() throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, DATE_FROM_15MIN);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, DATE_TO_15MIN);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(TYPE_PARAM, TYPE_BSC);
        requestParameters.putSingle(NODE_PARAM, TEST_VALUE_NODE_PARAM);
        return runQuery(lteHandoverFailureEnodeBCauseCodeListService, requestParameters);
    }

    private void insertAggDataDay() throws Exception {
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_1, 1, 20);

        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_2, 1, 10);
    }

    private void insertAggData15Min() throws Exception {
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID,
                INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID,
                INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID,
                INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_1, 1, 20);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID,
                INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_1, 1, 20);

        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID,
                INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID,
                INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID,
                INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_2, 1, 10);
        insertHierCCData(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, TEST_VALUE_HIER3_ID,
                INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_2, 1, 10);
    }

    @Test
    public void testEnodeBCauseCodeListDay() throws Exception {
        insertAggDataDay();
        final String json = getJsonResultDay();
        final ResultTranslator<LTEHandoverFailureEnodeBCCListResult> rt = getTranslator();
        final List<LTEHandoverFailureEnodeBCCListResult> eventResult = rt.translateResult(json,
                LTEHandoverFailureEnodeBCCListResult.class);
        assertThat(eventResult.size(), is(8));
    }

    @Test
    public void testEnodeBCauseCodeList15min() throws Exception {
        insertAggData15Min();
        final String json = getJsonResult15MIN();
        final ResultTranslator<LTEHandoverFailureEnodeBCCListResult> rt = getTranslator();
        final List<LTEHandoverFailureEnodeBCCListResult> eventResult = rt.translateResult(json,
                LTEHandoverFailureEnodeBCCListResult.class);
        assertThat(eventResult.size(), is(8));
    }

}