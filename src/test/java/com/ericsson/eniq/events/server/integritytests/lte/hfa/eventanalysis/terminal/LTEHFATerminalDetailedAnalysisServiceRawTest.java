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

import java.sql.SQLException;
import java.util.*;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.eniq.events.server.common.EventIDConstants;
import com.ericsson.eniq.events.server.integritytests.stubs.ReplaceTablesWithTempTablesTemplateUtils;
import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis.TerminalDetailedEAService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFATerminalDetailedAnalysisSetupResult;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHFATerminalDetailedAnalysisServiceRawTest extends BaseDataIntegrityTest<LTEHFATerminalDetailedAnalysisSetupResult> {
    private TerminalDetailedEAService lteHandoverFailureTerminalDetailedEventAnalysisService;

    private static final String DATETIME_ID_RAW = "2011-09-20 08:12:00";

    private static final String EVENT_TIME_RAW = "2011-09-20 08:12:00:1212";

    private static final String DATE_FROM_RAW = "20092011";

    private static final String DATE_TO_RAW = "20092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final String TEST_VALUE_HIER3_ID_1 = "4809532081614999117";

    private static final String TEST_VALUE_HIER3_ID_2 = "3135210477467174911";

    private static final String TEST_VALUE_HIER321_ID_1 = "7210756712490856540";

    private static final String TEST_VALUE_IMSI_1 = "11111119";

    private static final String TEST_VALUE_TAC_1 = "100081";

    private static final String TEST_VALUE_TAC_2 = "100082";

    private static final String TEST_VALUE_TAC_3 = "100083";

    private static final String TEST_VALUE_TAC_GROUP = "eCellGroup";

    private static final String TEST_VALUE_TAC_GROUP1 = "eCellGroup1";

    private static final String TEST_VALUE_ENODEB_GROUP = "enodebGroup";

    private static final String TEST_VALUE_ENODEB_GROUP_ZERO_TAC = "enodebGroupTacZero";

    private static final String TEST_VALUE_THIER3_ID_1 = "4809532081614999117";

    private static final String TEST_VALUE_THIER321_ID_1 = "7210756712490856540";

    private static final int TEST_VALUE_RAT = 2;

    private static final String TEST_VALUE_RAT_DESC = "LTE";

    private final static String EXPECTED_VALID_MANUFACTURER = "Novatel Wireless";

    private final static String EXPECTED_VALID_MARKETING_NAME = "Ovation MC547";

    private static final String DATE_FROM_AGG = "13092011";

    private static final String DATE_TO_AGG = "20092011";

    private static final String DATETIME_ID_AGG = "2011-09-19 08:12:00";

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     *
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureTerminalDetailedEventAnalysisService = new TerminalDetailedEAService();
        attachDependencies(lteHandoverFailureTerminalDetailedEventAnalysisService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        createTable();
        insertTopoData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(THIER3_HASHID);
        columnsForTable.add(THIER321_HASHID);
        columnsForTable.add(EVENT_TIME_COLUMN);
        columnsForTable.add(MSISDN_PARAM_UPPER_CASE);
        columnsForTable.add(IMSI);
        columnsForTable.add(TAC);
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(MCC);
        columnsForTable.add(MNC);
        columnsForTable.add(CAUSE_CODE_COLUMN);
        columnsForTable.add(DURATION);
        columnsForTable.add(NO_OF_ERABS);
        columnsForTable.add(HO_SOURCE_TARGET_TYPE);
        columnsForTable.add(HO_TYPE);
        columnsForTable.add(RANDOM_ACCESS_TYPE);
        columnsForTable.add(HO_TARGET_SELECTION_TYPE);
        columnsForTable.add(HO_PREP_OUT_ATTEMPT_CAUSE);
        columnsForTable.add(HO_PACKET_FORWARD);
        columnsForTable.add(SPID_VALUE);
        columnsForTable.add(DRX_CONFIG_INDEX);
        columnsForTable.add(HO_OUT_EXEC_ERAB_FAILBITMAP);
        columnsForTable.add(HO_OUT_PREP_ERAB_FAILBITMAP);
        columnsForTable.add(HO_OUT_PREP_ERAB_REQBITMAP);
        columnsForTable.add(HO_OUT_EXEC_ERAB_REQBITMAP);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(RAT);
        columnsForTable.add(PREP_IN_RESULT_UE_CTXT);
        columnsForTable.add(CAUSE_GROUP_3GPP);
        columnsForTable.add(CAUSE_3GPP);
        columnsForTable.add(SRVCC_TYPE);
        columnsForTable.add(HO_EXEC_OUT_ATTEMPT_CAUSE);
        columnsForTable.add(LOCAL_DATE_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ERR_RAW, columnsForTable);

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
        valuesForTable.put(TAC, Integer.valueOf(TEST_VALUE_EXCLUSIVE_TAC));
        valuesForTable.put(GROUP_NAME, EXCLUSIVE_TAC_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TEST_VALUE_TAC_1);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_TAC_GROUP);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TEST_VALUE_TAC_2);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_TAC_GROUP);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TEST_VALUE_TAC_3);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_TAC_GROUP1);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TAC_EQUAL_TO_ZERO);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_ENODEB_GROUP_ZERO_TAC);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        insertLookupData(TEST_VALUE_TAC_1, MANUFACTURER_FOR_SAMPLE_TAC, MARKETING_NAME_FOR_SAMPLE_TAC);
        insertLookupData(TEST_VALUE_TAC_2, MANUFACTURER_FOR_SAMPLE_TAC, MARKETING_NAME_FOR_SAMPLE_TAC);
        insertLookupData(TEST_VALUE_EXCLUSIVE_TAC, MANUFACTURER_FOR_SAMPLE_TAC, MARKETING_NAME_FOR_SAMPLE_TAC);

    }

    private void insertLookupData(final String tac, final String manufacturer, final String marketingName) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(VENDOR_NAME, manufacturer);
        valuesForTable.put(MARKETING_NAME, marketingName);
        valuesForTable.put(TAC, tac);
        insertRow(TEMP_DIM_E_SGEH_TAC, valuesForTable);
    }

    private void insertData(final String hierId, final String eventID, final int tac, final String time, final int instances) throws SQLException {
        for (int i = 0; i < instances; i++) {
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
            valuesForTable.put(TAC, tac);
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
            valuesForTable.put(LOCAL_DATE_ID, localDateId);
            valuesForTable.put(RAT, TEST_VALUE_RAT);
            valuesForTable.put(PREP_IN_RESULT_UE_CTXT, TEST_VALUE_PREP_IN_RESULT_UE_CTXT);
            valuesForTable.put(CAUSE_GROUP_3GPP, TEST_VALUE_CAUSE_GROUP_3GPP);
            valuesForTable.put(CAUSE_3GPP, TEST_VALUE_CAUSE_3GPP);
            valuesForTable.put(SRVCC_TYPE, TEST_VALUE_SRVCC_TYPE);
            valuesForTable.put(HO_EXEC_OUT_ATTEMPT_CAUSE, TEST_VALUE_HO_EXEC_OUT_ATTEMPT_CAUSE);
            insertRow(TEMP_EVENT_E_LTE_HFA_ERR_RAW, valuesForTable);
        }
    }

    private String getJsonResult(final String eventId, final String tac, final String dateFrom, final String dateTo) {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(EVENT_ID_PARAM, eventId);
        requestParameters.putSingle(TAC_PARAM, tac);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);

        return runQuery(lteHandoverFailureTerminalDetailedEventAnalysisService, requestParameters);
    }

    private String getJsonResultForGroup(final String eventId, final String groupName, final String dateFrom, final String dateTo) {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(EVENT_ID_PARAM, eventId);
        requestParameters.putSingle(GROUP_NAME_PARAM, groupName);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);

        return runQuery(lteHandoverFailureTerminalDetailedEventAnalysisService, requestParameters);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2InEvent_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_TAC_1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2InEvent_InValidTac_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_IN_HFA, String.valueOf(TAC_EQUAL_TO_ZERO), DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2OutEvent_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_TAC_1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2OutEvent_InValidTac_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, String.valueOf(TAC_EQUAL_TO_ZERO), DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2InEvent_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_TAC_1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2InEvent_InValidTac_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 2);
        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, String.valueOf(TAC_EQUAL_TO_ZERO), DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testETerminalAnalysisDetailedExecX2OutEvent_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_TAC_1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testETerminalAnalysisDetailedExecX2OutEvent_InValidTAc_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, String.valueOf(TAC_EQUAL_TO_ZERO), DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1InEvent_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_TAC_1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1InEvent_InValidTac_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_IN_HFA, String.valueOf(TAC_EQUAL_TO_ZERO), DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_TAC_1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_InValidTAc_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, String.valueOf(TAC_EQUAL_TO_ZERO), DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalBAnalysisDetailedExecS1InEvent_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_TAC_1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testTerminalBAnalysisDetailedExecS1InEvent_InValidTacRaw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, String.valueOf(TAC_EQUAL_TO_ZERO), DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testTerminalBAnalysisDetailedExecS1OutEvent_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_TAC_1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testTerminalBAnalysisDetailedExecS1OutEvent_InValidTac_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, String.valueOf(TAC_EQUAL_TO_ZERO), DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2InEvent_Raw_ForExclusiveTAC() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_EXCLUSIVE_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2OutEvent_Raw_ForExclusiveTAC() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_EXCLUSIVE_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2InEvent_Raw_ForExclusiveTAC() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_EXCLUSIVE_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testETerminalAnalysisDetailedExecX2OutEvent_Raw_ForExclusiveTAC() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_EXCLUSIVE_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1InEvent_Raw_ForExclusiveTAC() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_EXCLUSIVE_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_Raw_ForExclusiveTAC() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_EXCLUSIVE_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalBAnalysisDetailedExecS1InEvent_Raw_ForExclusiveTAC() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_EXCLUSIVE_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testTerminalBAnalysisDetailedExecS1OutEvent_Raw_ForExclusiveTAC() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_EXCLUSIVE_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2InEvent_RawGrpForExclusiveTacs() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_IN_HFA, EXCLUSIVE_TAC_GROUP_NAME, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2OutEvent_RawGrpForExclusiveTacs() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, EXCLUSIVE_TAC_GROUP_NAME, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2InEvent_RawGrpForExclusiveTacs() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, EXCLUSIVE_TAC_GROUP_NAME, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2OutEvent_RawGrpForExclusiveTacs() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, EXCLUSIVE_TAC_GROUP_NAME, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1InEvent_RawGrpForExclusiveTacs() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_IN_HFA, EXCLUSIVE_TAC_GROUP_NAME, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_RawGrpForExclusiveTacs() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, EXCLUSIVE_TAC_GROUP_NAME, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecS1InEvent_RawGrpForExclusiveTacs() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, EXCLUSIVE_TAC_GROUP_NAME, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecS1OutEvent_RawGrpForExclusiveTacs() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, EXCLUSIVE_TAC_GROUP_NAME, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2InEvent_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2InEvent_InValidTac_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_ENODEB_GROUP_ZERO_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2InEvent_TacNotPresentIntoDim__RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_TAC_GROUP1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2OutEvent_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2OutEvent_InvalidTAc_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_ENODEB_GROUP_ZERO_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2OutEvent_TacNotPresentIntoDim_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_TAC_GROUP1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2InEvent_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2InEvent_InValidTac_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_ENODEB_GROUP_ZERO_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2InEvent_TacNotPresentIntoDim_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_TAC_GROUP1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2OutEvent_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2OutEvent_TacNotPresentIntoDim_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_TAC_GROUP1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2OutEvent_InValidTac_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_ENODEB_GROUP_ZERO_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1InEvent_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1InEvent_TacNotPresentIntoDim_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_TAC_GROUP1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1InEvent_InValidTac_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_ENODEB_GROUP_ZERO_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecS1InEvent_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecS1InEvent_TacNotPresentIntoDim_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_TAC_GROUP1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecS1InEvent_InValidTac_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_ENODEB_GROUP_ZERO_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecS1OutEvent_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecS1OutEvent_TacNotPresentIntoDim_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_TAC_GROUP1, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecS1OutEvent_InvalidTac_RawGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TAC_EQUAL_TO_ZERO, DATETIME_ID_RAW, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_ENODEB_GROUP_ZERO_TAC, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testENodeBAnalysisDetailedPrepX2InEvent_TacNotPresentIntoDim_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_IN_HFA, TEST_VALUE_TAC_3, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepX2OutEvent_TacNotPresentIntoDim_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, TEST_VALUE_TAC_3, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedExecX2InEvent_TacNotPresentIntoDim_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, TEST_VALUE_TAC_3, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_IN_HFA);
    }

    @Test
    public void testETerminalAnalysisDetailedExecX2OutEvent_TacNotPresentIntoDim_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, TEST_VALUE_TAC_3, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_X2_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1InEvent_TacNotPresentIntoDim_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_IN_HFA, TEST_VALUE_TAC_3, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_TacNotPresentIntoDim_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_TAC_3, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalBAnalysisDetailedExecS1InEvent_TacNotPresenIntoDim_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_IN_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, TEST_VALUE_TAC_3, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_IN_HFA);
    }

    @Test
    public void testTerminalBAnalysisDetailedExecS1OutEvent_TacNotPresentIntoDim_Raw() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_RAW, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, TEST_VALUE_TAC_3, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_EXEC_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_DayAgg() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_AGG, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_AGG, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_TAC_3, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_DayAggGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_1), DATETIME_ID_AGG, 1);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_AGG, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_2), DATETIME_ID_AGG, 1);
        insertData(TEST_VALUE_HIER3_ID_2, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_AGG, 1);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_TAC_GROUP, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_TacNotPresentIntoDim_DayAgg() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_AGG, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_AGG, 2);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_TAC_3, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    @Test
    public void testTerminalAnalysisDetailedPrepS1OutEvent_TacNotPresentIntoDim_DayAggGrp() throws Exception {

        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_TAC_3), DATETIME_ID_AGG, 2);
        insertData(TEST_VALUE_HIER3_ID_1, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, Integer.parseInt(TEST_VALUE_EXCLUSIVE_TAC), DATETIME_ID_AGG, 2);

        final String json = getJsonResultForGroup(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, TEST_VALUE_TAC_GROUP1, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHFATerminalDetailedAnalysisSetupResult> rt = getTranslator();
        final List<LTEHFATerminalDetailedAnalysisSetupResult> eventResult = rt.translateResult(json, LTEHFATerminalDetailedAnalysisSetupResult.class);
        assertResult(eventResult, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);
    }

    private void assertResult(final List<LTEHFATerminalDetailedAnalysisSetupResult> results, final String eventId) {
        assertThat(results.size(), is(2));
        for (final LTEHFATerminalDetailedAnalysisSetupResult rs : results) {
            assertEquals(rs.getEventId(), eventId);
            if (rs.getTac().equals(TEST_VALUE_TAC_3)) {
                assertEquals(TERMINAL_MAKE_UNKNOWN, rs.getTerminalMake());
                assertEquals(TERMINAL_MODEL_UNKNOWN, rs.getTerminalModel());
            } else if (rs.getTac().equals(Integer.toString(TAC_EQUAL_TO_ZERO))) {
                assertEquals(TERMINAL_MAKE_INVALID, rs.getTerminalMake());
                assertEquals(TERMINAL_MODEL_INVALID, rs.getTerminalModel());
            } else if (rs.getTac().equals(TEST_VALUE_TAC_1)) {
                assertEquals(EXPECTED_VALID_MANUFACTURER, rs.getTerminalMake());
                assertEquals(EXPECTED_VALID_MARKETING_NAME, rs.getTerminalModel());
            }
        }
    }
}