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

package com.ericsson.eniq.events.server.integritytests.lte.hfa.causecode.enodeB;

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

import com.ericsson.eniq.events.server.common.EventIDConstants;
import com.ericsson.eniq.events.server.integritytests.stubs.ReplaceTablesWithTempTablesTemplateUtils;
import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.causecodeanalysis.LTEHFAEnodeBCCDetEventAnalysisService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFAEnodeBDetailedAnalysisCCSetupResult;
import com.ericsson.eniq.events.server.test.schema.Nullable;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFAENodeBCCDetailedAnalysisServiceRawTest extends BaseDataIntegrityTest<LTEHFAEnodeBDetailedAnalysisCCSetupResult> {
    private LTEHFAEnodeBCCDetEventAnalysisService lteHandoverFailureENodeBCauseCodeDetailedEventAnalysisService;

    private static final String DATETIME_ID_RAW = "2011-09-20 08:12:00";

    private static final String EVENT_TIME_RAW = "2011-09-20 08:12:00:1212";

    private static final String DATE_FROM_RAW = "20092011";

    private static final String DATE_TO_RAW = "20092011";

    private static final String DATE_FROM_AGG_DAY = "20092011";

    private static final String DATE_TO_AGG_DAY = "27092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final String TEST_VALUE_HIER3_ID_1 = "4809532081614999117";

    private static final String TEST_VALUE_HIER3_ID_2 = "3135210477467174911";

    private static final String TEST_VALUE_HIER321_ID_1 = "7210756712490856540";

    private static final String TEST_VALUE_IMSI_1 = "11111119";

    private static final String TEST_VALUE_ENODEB_GROUP = "enodebGroup";

    private static final String TEST_VALUE_THIER3_ID_1 = "4809532081614999117";

    private static final String TEST_VALUE_THIER321_ID_1 = "7210756712490856540";

    private static final int TEST_VALUE_RAT = 2;

    private static final String TEST_VALUE_RAT_DESC = "LTE";

    private static final int NULL_TAC_IDENTIFIER = -1;

    private static final String BLANK_TAC = "";

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     *
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
    lteHandoverFailureENodeBCauseCodeDetailedEventAnalysisService = new LTEHFAEnodeBCCDetEventAnalysisService();
    attachDependencies(lteHandoverFailureENodeBCauseCodeDetailedEventAnalysisService);
    ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
    createTable();
    insertTopoData();
    }

    private void createTable() throws Exception {
    final Collection<String> columnsForTable = new ArrayList<String>();
    final Map<String, Nullable> columnsForTableRaw = new HashMap<String, Nullable>();
    columnsForTableRaw.put(HIER3_ID, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HIER321_ID, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(THIER3_HASHID, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(THIER321_HASHID, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(EVENT_TIME_COLUMN, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(MSISDN_PARAM_UPPER_CASE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(IMSI, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(TAC, Nullable.CAN_BE_NULL);
    columnsForTableRaw.put(EVENT_ID, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(MCC, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(MNC, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(CAUSE_CODE_COLUMN, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(DURATION, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(NO_OF_ERABS, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_SOURCE_TARGET_TYPE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_TYPE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(RANDOM_ACCESS_TYPE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_TARGET_SELECTION_TYPE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_PREP_OUT_ATTEMPT_CAUSE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_PACKET_FORWARD, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(SPID_VALUE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(DRX_CONFIG_INDEX, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_OUT_EXEC_ERAB_FAILBITMAP, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_OUT_PREP_ERAB_FAILBITMAP, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_OUT_PREP_ERAB_REQBITMAP, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_OUT_EXEC_ERAB_REQBITMAP, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(DATETIME_ID, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(LOCAL_DATE_ID, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(RAT, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(PREP_IN_RESULT_UE_CTXT, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(CAUSE_GROUP_3GPP, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(CAUSE_3GPP, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(SRVCC_TYPE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(HO_EXEC_OUT_ATTEMPT_CAUSE, Nullable.CANNOT_BE_NULL);
    columnsForTableRaw.put(LOCAL_DATE_ID, Nullable.CANNOT_BE_NULL);
    createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ERR_RAW, columnsForTableRaw);

    columnsForTable.clear();
    columnsForTable.add(HIER3_ID);
    columnsForTable.add(HIER321_ID);
    columnsForTable.add(VENDOR);
    columnsForTable.add(HIERARCHY_1);
    columnsForTable.add(HIERARCHY_3);
    columnsForTable.add(MCC);
    columnsForTable.add(MNC);
    columnsForTable.add(RAT);
    createTemporaryTable(TEMP_DIM_E_LTE_HIER321, columnsForTable);

    columnsForTable.clear();
    columnsForTable.add(EVENT_ID);
    columnsForTable.add(EVENT_ID_DESC);
    createTemporaryTable(TEMP_DIM_E_LTE_HFA_EVENTTYPE, columnsForTable);

    columnsForTable.clear();
    columnsForTable.add(CAUSE_CODE_COLUMN);
    columnsForTable.add(EVENT_ID);
    columnsForTable.add(CAUSE_CODE_DESC_COLUMN);
    createTemporaryTable(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, columnsForTable);

    columnsForTable.clear();
    columnsForTable.add(VENDOR_NAME);
    columnsForTable.add(MARKETING_NAME);
    columnsForTable.add(TAC);
    createTemporaryTable(TEMP_DIM_E_SGEH_TAC, columnsForTable);

    columnsForTable.clear();
    columnsForTable.add(HIER3_ID);
    columnsForTable.add(GROUP_NAME);
    createTemporaryTable(TEMP_GROUP_TYPE_E_RAT_VEND_HIER3, columnsForTable);

    columnsForTable.clear();
    columnsForTable.add(RAT);
    columnsForTable.add(RAT_DESC);
    createTemporaryTable(TEMP_DIM_E_SGEH_RAT, columnsForTable);
    }

    private void insertTopoData() throws SQLException {
    final Map<String, Object> valuesForTable = new HashMap<String, Object>();
    valuesForTable.put(HIER3_ID, TEST_VALUE_HIER3_ID_1);
    valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_1);
    valuesForTable.put(VENDOR, ERICSSON);
    valuesForTable.put(RAT, TEST_VALUE_RAT);
    valuesForTable.put(HIERARCHY_1, TEST_VALUE_CELL);
    valuesForTable.put(HIERARCHY_3, TEST_VALUE_BSC);
    valuesForTable.put(MCC, TEST_VALUE_MCC);
    valuesForTable.put(MNC, TEST_VALUE_MNC);
    insertRow(TEMP_DIM_E_LTE_HIER321, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_X2_IN");
    insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_PREP_X2_OUT");
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
    valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_S1_IN");
    insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    valuesForTable.put(EVENT_ID_DESC, "INTERNAL_PROC_HO_EXEC_S1_OUT");
    insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(TAC, Integer.valueOf(TEST_VALUE_EXCLUSIVE_TAC));
    valuesForTable.put(GROUP_NAME, EXCLUSIVE_TAC_GROUP_NAME);
    insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

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
    valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
    insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
    valuesForTable.put(EVENT_ID, EventIDConstants.INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    valuesForTable.put(CAUSE_CODE_DESC_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE_DESC);
    insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(HIER3_ID, TEST_VALUE_HIER3_ID_1);
    valuesForTable.put(GROUP_NAME, TEST_VALUE_ENODEB_GROUP);
    insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER3, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(HIER3_ID, TEST_VALUE_HIER3_ID_2);
    valuesForTable.put(GROUP_NAME, TEST_VALUE_ENODEB_GROUP);
    insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER3, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(RAT, TEST_VALUE_RAT);
    valuesForTable.put(RAT_DESC, TEST_VALUE_RAT_DESC);
    insertRow(TEMP_DIM_E_SGEH_RAT, valuesForTable);

    valuesForTable.clear();
    valuesForTable.put(TAC, SAMPLE_TAC_2);
    valuesForTable.put(VENDOR_NAME, MANUFACTURER_FOR_SAMPLE_TAC_2);
    valuesForTable.put(MARKETING_NAME, MARKETING_NAME_FOR_SAMPLE_TAC_2);
    insertRow(TEMP_DIM_E_SGEH_TAC, valuesForTable);
    }

    private void insertData(final String hierId, final String eventID, final int tac, final String time, final int instances) throws SQLException {
    for (int i = 0; i < instances; i++) {
        final String localDate = time.substring(0, 10);
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        final String localDateId = time.substring(0, 10);
        valuesForTable.put(HIER3_ID, hierId);
        valuesForTable.put(EVENT_ID, eventID);
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_1);
        valuesForTable.put(THIER3_HASHID, TEST_VALUE_THIER3_ID_1);
        valuesForTable.put(THIER321_HASHID, TEST_VALUE_THIER321_ID_1);
        valuesForTable.put(MSISDN_PARAM_UPPER_CASE, TEST_VALUE_MSISDN);
        valuesForTable.put(IMSI_PARAM, TEST_VALUE_IMSI_1);
        valuesForTable.put(EVENT_TIME_COLUMN, EVENT_TIME_RAW);
        if (tac == NULL_TAC_IDENTIFIER) {
        valuesForTable.put(TAC, TAC_NULL);
        } else {
        valuesForTable.put(TAC, tac);
        }
        valuesForTable.put(MCC, TEST_VALUE_MCC);
        valuesForTable.put(MNC, TEST_VALUE_MNC);
        valuesForTable.put(CAUSE_CODE_COLUMN, TEST_VALUE_LTE_HFA_CAUSE_CODE);
        valuesForTable.put(DURATION, TEST_VALUE_DURATION);
        valuesForTable.put(NO_OF_ERABS, TEST_VALUE_NO_OF_ERABS);
        valuesForTable.put(HO_SOURCE_TARGET_TYPE, TEST_VALUE_HO_SOURCE_TARGET_TYPE);
        valuesForTable.put(HO_TYPE, TEST_VALUE_HO_TYPE);
        valuesForTable.put(RANDOM_ACCESS_TYPE, TEST_VALUE_RANDOM_ACCESS_TYPE);
        valuesForTable.put(HO_TARGET_SELECTION_TYPE, TEST_VALUE_HO_TARGET_SELECTION_TYPE);
        valuesForTable.put(HO_PREP_OUT_ATTEMPT_CAUSE, TEST_VALUE_HO_PREP_OUT_ATTEMPT_CAUSE);
        valuesForTable.put(HO_PACKET_FORWARD, TEST_VALUE_HO_PACKET_FORWARD);
        valuesForTable.put(SPID_VALUE, TEST_VALUE_SPID_VALUE);
        valuesForTable.put(DRX_CONFIG_INDEX, TEST_VALUE_DRX_CONFIG_INDEX);
        valuesForTable.put(HO_OUT_EXEC_ERAB_FAILBITMAP, TEST_VALUE_HO_OUT_EXEC_ERAB_FAILBITMAP);
        valuesForTable.put(HO_OUT_PREP_ERAB_FAILBITMAP, TEST_VALUE_HO_OUT_PREP_ERAB_FAILBITMAP);
        valuesForTable.put(HO_OUT_PREP_ERAB_REQBITMAP, TEST_VALUE_HO_OUT_PREP_ERAB_REQBITMAP);
        valuesForTable.put(HO_OUT_EXEC_ERAB_FAILBITMAP, TEST_VALUE_HO_OUT_EXEC_ERAB_FAILBITMAP);
        valuesForTable.put(HO_OUT_EXEC_ERAB_REQBITMAP, TEST_VALUE_HO_OUT_EXEC_ERAB_REQBITMAP);
        valuesForTable.put(DATETIME_ID, time);
        valuesForTable.put(LOCAL_DATE_ID, localDate);
        valuesForTable.put(RAT, TEST_VALUE_RAT);
        valuesForTable.put(PREP_IN_RESULT_UE_CTXT, TEST_VALUE_PREP_IN_RESULT_UE_CTXT);
        valuesForTable.put(CAUSE_GROUP_3GPP, TEST_VALUE_CAUSE_GROUP_3GPP);
        valuesForTable.put(CAUSE_3GPP, TEST_VALUE_CAUSE_3GPP);
        valuesForTable.put(SRVCC_TYPE, TEST_VALUE_SRVCC_TYPE);
        valuesForTable.put(HO_EXEC_OUT_ATTEMPT_CAUSE, TEST_VALUE_HO_EXEC_OUT_ATTEMPT_CAUSE);
        valuesForTable.put(LOCAL_DATE_ID, localDateId);
        insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
    }
    }

    private String getJsonResult(final String eventId, final String dateFromId, final String dateToId) {
    final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
    requestParameters.putSingle(DISPLAY_PARAM, GRID);
    requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFromId);
    requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateToId);
    requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
    requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
    requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
    requestParameters.putSingle(EVENT_ID_PARAM, eventId);
    requestParameters.putSingle(CAUSE_CODE_PARAM, Integer.toString(TEST_VALUE_LTE_HFA_CAUSE_CODE));
    requestParameters.putSingle(HIER3_ID, TEST_VALUE_HIER3_ID_1);
    requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);

    return runQuery(lteHandoverFailureENodeBCauseCodeDetailedEventAnalysisService, requestParameters);
    }

    private String getJsonResultForGroup(final String eventId, final String dateFromId, final String dateToId) {
    final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
    requestParameters.putSingle(DISPLAY_PARAM, GRID);
    requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFromId);
    requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateToId);
    requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
    requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
    requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
    requestParameters.putSingle(EVENT_ID_PARAM, eventId);
    requestParameters.putSingle(CAUSE_CODE_PARAM, Integer.toString(TEST_VALUE_LTE_HFA_CAUSE_CODE));
    requestParameters.putSingle(GROUP_NAME_PARAM, TEST_VALUE_ENODEB_GROUP);
    requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);

    return runQuery(lteHandoverFailureENodeBCauseCodeDetailedEventAnalysisService, requestParameters);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2InEvent_Raw() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2InEvent_Raw_WeekQuery() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);

    assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2OutEvent_Raw() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);

    assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2OutEvent_Raw_WeekQuery() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);

    assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecX2InEvent_Raw() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecX2InEvent_Raw_WeekQuery() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecX2OutEvent_Raw() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);

    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecX2OutEvent_Raw_WeekQuery() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);

    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepS1InEvent_Raw() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepS1InEvent_Raw_WeekQuery() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepS1OutEvent_Raw() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepS1OutEvent_Raw_WeekQuery() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecS1InEvent_Raw() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecS1InEvent_Raw_WeekQuery() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecS1OutEvent_Raw() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecS1OutEvent_Raw_WeekQuery() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

    final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2InEvent_RawGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2OutEvent_RawGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecX2InEvent_RawGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecX2OutEvent_RawGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepS1InEvent_RawGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepS1OutEvent_RawGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecS1InEvent_RawGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecS1OutEvent_RawGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedExecS1OutEvent_AggGrp() throws Exception {

    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, SAMPLE_TAC, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, SAMPLE_TAC_2, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, NULL_TAC_IDENTIFIER, DATETIME_ID_RAW, 1);
    insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

    final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATE_FROM_AGG_DAY, DATE_TO_AGG_DAY);

    final ResultTranslator<LTEHFAEnodeBDetailedAnalysisCCSetupResult> rt = getTranslator();
    final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> eventResult = rt.translateResult(json, LTEHFAEnodeBDetailedAnalysisCCSetupResult.class);
    assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    private void assertResult(final List<LTEHFAEnodeBDetailedAnalysisCCSetupResult> results, final String eventId) {
    assertThat(results.size(), is(4));
    for (final LTEHFAEnodeBDetailedAnalysisCCSetupResult rs : results) {
        assertEquals(rs.getEventId(), eventId);
        if (rs.getTac().equals(Integer.toString(SAMPLE_TAC_2))) {
        assertEquals(rs.getTerminalMake(), "Apple");
        assertEquals(rs.getTerminalModel(), "iPad 2 A1396");
        } else if (rs.getTac().equals(Integer.toString(TAC_EQUAL_TO_ZERO))) {
        assertEquals(TERMINAL_MAKE_INVALID, rs.getTerminalMake());
        assertEquals(TERMINAL_MODEL_INVALID, rs.getTerminalModel());
        } else if (rs.getTac().equals(BLANK_TAC)) {
        assertEquals(TERMINAL_MAKE_EMPTY, rs.getTerminalMake());
        assertEquals(TERMINAL_MODEL_EMPTY, rs.getTerminalModel());
        } else {
        assertEquals(rs.getTerminalMake(), "Manufacturer Unknown");
        assertEquals(rs.getTerminalModel(), "Model Unknown");
        }
    }
    }
}