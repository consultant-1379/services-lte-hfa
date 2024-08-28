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

package com.ericsson.eniq.events.server.integritytests.lte.hfa.eventanalysis.enodeb;

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
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis.LTEHandoverFailureEnodeBEventHOStageService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFAEnodeBEventHandoverStageResultRaw;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFAEnodeBEventHandoverStageServiceAggTest extends BaseDataIntegrityTest<LTEHFAEnodeBEventHandoverStageResultRaw> {

    private LTEHandoverFailureEnodeBEventHOStageService lteHandoverFailureEnodeBEventHandoverStageService;

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

    private static final String TEST_VALUE_NODE_PARAM = "ONRM_RootMo_R:LTE01ERBS00001,Ericsson,LTE";

    private static final String TEST_VALUE_HIERARCHY_3 = "ENODEB";

    private static final String TEST_VALUE_VENDOR = "ERICSSON";

    private static final String TEST_VALUE_IMSI_1 = "11111119";

    private static final String TEST_VALUE_IMSI_2 = "22222229";

    private static final String TEST_VALUE_IMSI_3 = "33333339";

    private static final String TEST_VALUE_IMSI_4 = "44444449";

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     *
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureEnodeBEventHandoverStageService = new LTEHandoverFailureEnodeBEventHOStageService();
        attachDependencies(lteHandoverFailureEnodeBEventHandoverStageService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        createTable();
        insertTopoData();
        insertData();

    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(IMSI);
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(TAC);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
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
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(HIERARCHY_3);
        columnsForTable.add(VENDOR);
        createTemporaryTable(TEMP_DIM_E_LTE_HIER321, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(NO_OF_ERRORS);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, columnsForTable);
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
        valuesForTable.put(HIER3_ID, TEST_VALUE_HIER3_ID);
        valuesForTable.put(HIERARCHY_3, TEST_VALUE_HIERARCHY_3);
        valuesForTable.put(VENDOR, TEST_VALUE_VENDOR);
        insertRow(TEMP_DIM_E_LTE_HIER321, valuesForTable);
    }

    private void insertRawData(final String imsi, final long hier3Id, final int tac, final String categoryId, final String eventID,
                               final String time, final int instances) throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            final String localDateId1 = time.substring(0, 10);
            valuesForTable.put(IMSI_PARAM, imsi);
            valuesForTable.put(HIER3_ID, hier3Id);
            valuesForTable.put(TAC, tac);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryId);
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(LOCAL_DATE_ID, localDateId1);
            insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
        }

    }

    private void insertDataAgg(final String aggTable, final long hier3Id, final String eventID, final String categoryId, final String time,
                               final int instances, final int noOfErrors) throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(HIER3_ID, hier3Id);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, categoryId);
            valuesForTable.put(NO_OF_ERRORS, noOfErrors);
            valuesForTable.put(DATETIME_ID, time);
            insertRow(aggTable, valuesForTable);
        }
    }

    private String getJsonResult(final boolean isRankingDrillDown, final String dateFromId, final String dateToId) throws URISyntaxException {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFromId);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateToId);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(TYPE_PARAM, TYPE_BSC);
        if (isRankingDrillDown) {
            requestParameters.putSingle(HIER3_ID, Long.toString(TEST_VALUE_HIER3_ID));
        } else {
            requestParameters.putSingle(NODE_PARAM, TEST_VALUE_NODE_PARAM);
        }
        return runQuery(lteHandoverFailureEnodeBEventHandoverStageService, requestParameters);
    }

    private void insertData() throws Exception {
        insertRawData(TEST_VALUE_IMSI_1, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                DATETIME_ID_DAY_1, 1);
        insertRawData(TEST_VALUE_IMSI_2, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                DATETIME_ID_DAY_1, 1);
        insertRawData(TEST_VALUE_IMSI_3, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                DATETIME_ID_DAY_1, 1);
        insertRawData(TEST_VALUE_IMSI_4, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                DATETIME_ID_DAY_1, 1);
        insertRawData(TEST_VALUE_IMSI_1, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                DATETIME_ID_DAY_1, 1);
        insertRawData(TEST_VALUE_IMSI_2, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                DATETIME_ID_DAY_1, 1);
        insertRawData(TEST_VALUE_IMSI_3, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                DATETIME_ID_DAY_1, 1);
        insertRawData(TEST_VALUE_IMSI_4, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                DATETIME_ID_DAY_1, 1);

        insertRawData(TEST_VALUE_IMSI_1, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                DATETIME_ID_DAY_2, 1);
        insertRawData(TEST_VALUE_IMSI_2, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                DATETIME_ID_DAY_2, 1);
        insertRawData(TEST_VALUE_IMSI_3, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                DATETIME_ID_DAY_2, 1);
        insertRawData(TEST_VALUE_IMSI_4, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                DATETIME_ID_DAY_2, 1);
        insertRawData(TEST_VALUE_IMSI_1, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                DATETIME_ID_DAY_2, 1);
        insertRawData(TEST_VALUE_IMSI_2, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                DATETIME_ID_DAY_2, 1);
        insertRawData(TEST_VALUE_IMSI_3, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                DATETIME_ID_DAY_2, 1);
        insertRawData(TEST_VALUE_IMSI_4, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                DATETIME_ID_DAY_2, 1);

        insertRawData(TEST_VALUE_IMSI_1, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                DATETIME_ID_15MIN_1, 1);
        insertRawData(TEST_VALUE_IMSI_2, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                DATETIME_ID_15MIN_1, 1);
        insertRawData(TEST_VALUE_IMSI_3, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                DATETIME_ID_15MIN_1, 1);
        insertRawData(TEST_VALUE_IMSI_4, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                DATETIME_ID_15MIN_1, 1);
        insertRawData(TEST_VALUE_IMSI_1, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                DATETIME_ID_15MIN_1, 1);
        insertRawData(TEST_VALUE_IMSI_2, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                DATETIME_ID_15MIN_1, 1);
        insertRawData(TEST_VALUE_IMSI_3, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                DATETIME_ID_15MIN_1, 1);
        insertRawData(TEST_VALUE_IMSI_4, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                DATETIME_ID_15MIN_1, 1);

        insertRawData(TEST_VALUE_IMSI_1, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                DATETIME_ID_15MIN_2, 1);
        insertRawData(TEST_VALUE_IMSI_2, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                DATETIME_ID_15MIN_2, 1);
        insertRawData(TEST_VALUE_IMSI_3, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                DATETIME_ID_15MIN_2, 1);
        insertRawData(TEST_VALUE_IMSI_4, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_PREP, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                DATETIME_ID_15MIN_2, 1);
        insertRawData(TEST_VALUE_IMSI_1, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                DATETIME_ID_15MIN_2, 1);
        insertRawData(TEST_VALUE_IMSI_2, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                DATETIME_ID_15MIN_2, 1);
        insertRawData(TEST_VALUE_IMSI_3, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                DATETIME_ID_15MIN_2, 1);
        insertRawData(TEST_VALUE_IMSI_4, TEST_VALUE_HIER3_ID, SAMPLE_TAC, EventIDConstants.CATEGORY_ID_2_EXEC, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                DATETIME_ID_15MIN_2, 1);

    }

    private void insertAggDataDay() throws Exception {

        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_1, 1, 20);

        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_DAY_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_DAY_2, 1, 10);
    }

    private void insertAggData15Min() throws Exception {

        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_1, 1, 20);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_1, 1, 20);

        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_PREP_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_PREP, DATETIME_ID_15MIN_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_IN_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_2, 1, 10);
        insertDataAgg(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, TEST_VALUE_HIER3_ID, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA,
                EventIDConstants.CATEGORY_ID_2_EXEC, DATETIME_ID_15MIN_2, 1, 10);

    }

    @Test
    public void testEnodeBEventSummaryDay() throws Exception {
        insertAggDataDay();
        final String json = getJsonResult(false, DATE_FROM_DAY, DATE_TO_DAY);
        final ResultTranslator<LTEHFAEnodeBEventHandoverStageResultRaw> rt = getTranslator();
        final List<LTEHFAEnodeBEventHandoverStageResultRaw> eventResult = rt.translateResult(json, LTEHFAEnodeBEventHandoverStageResultRaw.class);
        assertResultDay(eventResult);
    }

    @Test
    public void testEnodeBEventSummary15min() throws Exception {
        insertAggData15Min();
        final String json = getJsonResult(false, DATE_FROM_15MIN, DATE_TO_15MIN);
        final ResultTranslator<LTEHFAEnodeBEventHandoverStageResultRaw> rt = getTranslator();
        final List<LTEHFAEnodeBEventHandoverStageResultRaw> eventResult = rt.translateResult(json, LTEHFAEnodeBEventHandoverStageResultRaw.class);
        assertResultDay(eventResult);
        //80
    }

    /*
     * @Test public void testEnodeBEventSummaryDayRankingDrillDown() throws Exception { insertAggDataDay(); final String json =
     * getJsonResultDay(true); final ResultTranslator<LTEHandoverFailureEnodeBEventHandoverStageResultRaw> rt = getTranslator(); final
     * List<LTEHandoverFailureEnodeBEventHandoverStageResultRaw> eventResult = rt.translateResult(json,
     * LTEHandoverFailureEnodeBEventHandoverStageResultRaw.class); assertResultDay(eventResult); }
     *
     * @Test public void testEnodeBEventSummary15minRankingDrillDown() throws Exception { insertAggData15Min(); final String json =
     * getJsonResult15MIN(true); final ResultTranslator<LTEHandoverFailureEnodeBEventHandoverStageResultRaw> rt = getTranslator(); final
     * List<LTEHandoverFailureEnodeBEventHandoverStageResultRaw> eventResult = rt.translateResult(json,
     * LTEHandoverFailureEnodeBEventHandoverStageResultRaw.class); assertResultDay(eventResult); //80 }
     */

    private void assertResultDay(final List<LTEHFAEnodeBEventHandoverStageResultRaw> results) {
        assertThat(results.size(), is(2));
        for (final LTEHFAEnodeBEventHandoverStageResultRaw rs : results) {
            assertEquals("120", rs.getNoOfErrors());
            assertEquals("4", rs.getImpactedSubscribers());
        }
    }
}