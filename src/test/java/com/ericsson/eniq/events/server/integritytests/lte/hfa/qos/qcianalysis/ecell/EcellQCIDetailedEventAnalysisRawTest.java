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
package com.ericsson.eniq.events.server.integritytests.lte.hfa.qos.qcianalysis.ecell;

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

import com.ericsson.eniq.events.server.integritytests.stubs.ReplaceTablesWithTempTablesTemplateUtils;
import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis.EcellQCIDetailedEventAnalysisService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.*;
import com.ericsson.eniq.events.server.test.schema.Nullable;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class EcellQCIDetailedEventAnalysisRawTest extends BaseDataIntegrityTest<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> {
    EcellQCIDetailedEventAnalysisService lteQCIDetailedEventAnalysisService;

    private static final String DATETIME_ID_RAW = "2011-09-20 08:12:00";

    private static final String DATE_FROM_RAW = "20092011";

    private static final String DATE_TO_RAW = "20092011";

    private static final String DATE_FROM_AGG = "17092011";

    private static final String DATE_TO_AGG = "24092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final String TEST_VALUE_HIER3_ID_1 = "4809532081614999117";

    private static final String TEST_VALUE_HIER3_ID_2 = "4809532081614999118";

    private static final String TEST_VALUE_HIER321_ID_1 = "7210756712490856540";

    private static final String TEST_VALUE_HIER321_ID_2 = "7210756712490856541";

    private static final String TEST_VALUE_QCI_ID_1 = "1";

    private static final String TEST_VALUE_QCI_ID_2 = "2";

    private static final String EVENT_TIME_RAW = "2011-09-20 08:12:00.121";

    private static final String TEST_VALUE_IMSI_1 = "11111119";

    private static final String TEST_VALUE_GROUP_NAME = "ecellGroup";

    private static final int NULL_TAC_IDENTIFIER = -1;
    
    private static final String BLANK_TAC = "";

    /**
     * 1. Create tables. 2. Insert test datas to the tables.
     * 
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteQCIDetailedEventAnalysisService = new EcellQCIDetailedEventAnalysisService();
        attachDependencies(lteQCIDetailedEventAnalysisService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_TAC, TEMP_DIM_E_SGEH_TAC);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_SGEH_RAT, TEMP_DIM_E_SGEH_RAT);
        createTable();
        insertTopoData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        final Map<String, Nullable> columnsForTableRaw = new HashMap<String, Nullable>();
        columnsForTableRaw.put(HIER3_ID, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(EVENT_ID, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(HIER321_ID, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(THIER3_HASHID, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(THIER321_HASHID, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(MSISDN_PARAM_UPPER_CASE, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(IMSI, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(EVENT_TIME_COLUMN, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(TAC, Nullable.CAN_BE_NULL);
        columnsForTableRaw.put(SETUP_RESULT, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(HO_IN_PREP_ERAB_RESULT, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(HO_IN_PREP_ERAB_REQ, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(SETUP_REQ_QCI, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(SETUP_REQ_ARP, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(SETUP_REQ_PCI, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(SETUP_REQ_PVI, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(FAILURE_3GPP_CAUSE, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(SETUP_ATT_ACC_TYPE, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(SETUP_SUCC_ACC_TYPE, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(SETUP_FAIL_3GPP_CAUSE_GROUP, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(DATETIME_ID, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(LOCAL_DATE_ID, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(RELEASE_REQ_QCI, Nullable.CANNOT_BE_NULL);
        columnsForTableRaw.put(QCI_ID_COLUMN, Nullable.CANNOT_BE_NULL);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_ERR_RAW, columnsForTableRaw);

        columnsForTable.clear();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(VENDOR);
        columnsForTable.add(RAT);
        columnsForTable.add(HIERARCHY_1);
        columnsForTable.add(HIERARCHY_2);
        columnsForTable.add(HIERARCHY_3);
        columnsForTable.add(MCC);
        columnsForTable.add(MNC);
        createTemporaryTable(TEMP_DIM_E_LTE_HIER321, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(EVENT_ID);
        columnsForTable.add(EVENT_ID_DESC);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_EVENTTYPE, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(SETUP_REQ_PCI);
        columnsForTable.add(SETUP_REQ_PCI_DESC);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_ERAB_SETUP_REQ_PCI, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(SETUP_REQ_PVI);
        columnsForTable.add(SETUP_REQ_PVI_DESC);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_ERAB_SETUP_REQ_PVI, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(CAUSE_CODE_COLUMN);
        columnsForTable.add(CAUSE_CODE_DESC_COLUMN);
        columnsForTable.add(EVENT_ID);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(RAT);
        columnsForTable.add(RAT_DESC);
        createTemporaryTable(TEMP_DIM_E_SGEH_RAT, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(VENDOR_NAME);
        columnsForTable.add(MARKETING_NAME);
        columnsForTable.add(TAC);
        createTemporaryTable(TEMP_DIM_E_SGEH_TAC, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(GROUP_NAME);
        createTemporaryTable(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, columnsForTable);
    }

    private void insertTopoData() throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(HIER3_ID, TEST_VALUE_HIER3_ID_1);
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_1);
        valuesForTable.put(VENDOR, ERICSSON);
        valuesForTable.put(RAT, 2);
        valuesForTable.put(HIERARCHY_1, TEST_VALUE_CELL);
        valuesForTable.put(HIERARCHY_2, "");
        valuesForTable.put(HIERARCHY_3, TEST_VALUE_BSC);
        valuesForTable.put(MCC, TEST_VALUE_MCC);
        valuesForTable.put(MNC, TEST_VALUE_MNC);
        insertRow(TEMP_DIM_E_LTE_HIER321, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(HIER3_ID, TEST_VALUE_HIER3_ID_2);
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_2);
        valuesForTable.put(VENDOR, ERICSSON);
        valuesForTable.put(RAT, 2);
        valuesForTable.put(HIERARCHY_1, "cell,,bsc,nokia,gsm");
        valuesForTable.put(HIERARCHY_2, "");
        valuesForTable.put(HIERARCHY_3, "bsc,nokia,gsm");
        valuesForTable.put(MCC, "075");
        valuesForTable.put(MNC, "03");
        insertRow(TEMP_DIM_E_LTE_HIER321, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_1);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(HIER321_ID, TEST_VALUE_HIER321_ID_2);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER321, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(RAT, 2);
        valuesForTable.put(RAT_DESC, "LTE");
        insertRow(TEMP_DIM_E_SGEH_RAT, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(VENDOR_NAME, MANUFACTURER_FOR_SAMPLE_TAC);
        valuesForTable.put(MARKETING_NAME, MARKETING_NAME_FOR_SAMPLE_TAC);
        valuesForTable.put(TAC, SAMPLE_TAC);
        insertRow(TEMP_DIM_E_SGEH_TAC, valuesForTable);

    }

    private void insertData(final String eventID, final String time, final int instances, final String qciID, final String hire3ID,
                            final String hire321ID, int tac) throws SQLException {
        for (int i = 0; i < instances; i++) {
            final Map<String, Object> valuesForTable = new HashMap<String, Object>();
            valuesForTable.put(HIER3_ID, hire3ID);
            valuesForTable.put(EVENT_ID, eventID);
            valuesForTable.put(HIER321_ID, hire321ID);
            valuesForTable.put(THIER3_HASHID, TEST_VALUE_HIER3_ID_1);
            valuesForTable.put(THIER321_HASHID, TEST_VALUE_HIER321_ID_1);
            valuesForTable.put(MSISDN_PARAM_UPPER_CASE, TEST_VALUE_MSISDN);
            valuesForTable.put(IMSI, TEST_VALUE_IMSI_1);
            valuesForTable.put(EVENT_TIME_COLUMN, EVENT_TIME_RAW);
            if (tac == NULL_TAC_IDENTIFIER) {
                valuesForTable.put(TAC, TAC_NULL);
            } else {
                valuesForTable.put(TAC, tac);
            }
            valuesForTable.put(HO_IN_PREP_ERAB_REQ, TEST_VALUE_HO_IN_PREP_ERAB_REQ);
            valuesForTable.put(HO_IN_PREP_ERAB_RESULT, TEST_VALUE_HO_IN_PREP_ERAB_RESULT);
            valuesForTable.put(SETUP_RESULT, TEST_VALUE_SETUP_RESULT);
            valuesForTable.put(SETUP_RESULT, TEST_VALUE_SETUP_RESULT);
            valuesForTable.put(SETUP_REQ_QCI, TEST_VALUE_SETUP_REQ_QCI);
            valuesForTable.put(SETUP_REQ_ARP, TEST_VALUE_SETUP_REQ_ARP);
            valuesForTable.put(SETUP_REQ_PCI, TEST_VALUE_SETUP_REQ_PCI);
            valuesForTable.put(SETUP_REQ_PVI, TEST_VALUE_SETUP_REQ_PVI);
            valuesForTable.put(FAILURE_3GPP_CAUSE, TEST_VALUE_FAILURE_3GPP_CAUSE);
            valuesForTable.put(SETUP_ATT_ACC_TYPE, TEST_VALUE_SETUP_ATT_ACC_TYPE);
            valuesForTable.put(SETUP_SUCC_ACC_TYPE, TEST_VALUE_SETUP_SUCC_ACC_TYPE);
            valuesForTable.put(SETUP_FAIL_3GPP_CAUSE_GROUP, TEST_VALUE_SETUP_FAIL_3GPP_CAUSE_GROUP);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(LOCAL_DATE_ID, time.substring(0, 10));
            valuesForTable.put(RELEASE_REQ_QCI, TEST_VALUE_RELEASE_REQ_QCI);
            valuesForTable.put(QCI_ID_COLUMN, qciID);
            insertRow(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_ERR_RAW, valuesForTable);
        }
    }

    private String getJsonResult(final String eventId, final String dateFrom, final String dateTo) {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(EVENT_ID_PARAM, eventId);
        requestParameters.putSingle(EVENT_ID_SQL_PARAM, eventId);
        requestParameters.putSingle(HIER321_ID, TEST_VALUE_HIER321_ID_1);
        requestParameters.putSingle(HIER3_ID, TEST_VALUE_HIER3_ID_1);
        requestParameters.putSingle(QCI_ID_COLUMN, TEST_VALUE_QCI_ID_1);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);

        return runQuery(lteQCIDetailedEventAnalysisService, requestParameters);
    }

    private String getJsonResultGroup(final String eventId, String dateFrom, String dateTo) {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(DATE_FROM_QUERY_PARAM, dateFrom);
        requestParameters.putSingle(DATE_TO_QUERY_PARAM, dateTo);
        requestParameters.putSingle(TIME_FROM_QUERY_PARAM, TIME_FROM);
        requestParameters.putSingle(TIME_TO_QUERY_PARAM, TIME_TO);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(EVENT_ID_PARAM, eventId);
        requestParameters.putSingle(EVENT_ID_SQL_PARAM, eventId);
        requestParameters.putSingle(GROUP_NAME_PARAM, TEST_VALUE_GROUP_NAME);
        requestParameters.putSingle(QCI_ID_COLUMN, TEST_VALUE_QCI_ID_1);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);

        return runQuery(lteQCIDetailedEventAnalysisService, requestParameters);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepX2Out_Raw() throws Exception {

        insertData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecX2In_Raw() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecX2Out_Raw() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1Out_Raw() throws Exception {

        insertData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecS1Out_Raw() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_Raw() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepX2Out_Agg() throws Exception {

        insertData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecX2In_Agg() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecX2Out_Agg() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1Out_Agg() throws Exception {

        insertData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecS1Out_Agg() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_Agg() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_Agg_TacInRawAndNotInDim() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                TAC_NOT_IN_DIM);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                TAC_NOT_IN_DIM);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_Agg_ImeisvInvalidTacZero() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                TAC_EQUAL_TO_ZERO);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                TAC_EQUAL_TO_ZERO);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }
    
    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_Agg_ImeisvNullTacNull() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                NULL_TAC_IDENTIFIER);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                NULL_TAC_IDENTIFIER);

        final String json = getJsonResult(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepX2Out_RawGroup() throws Exception {

        insertData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecX2In_RawGroup() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecX2Out_RawGroup() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1Out_RawGroup() throws Exception {

        insertData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecS1Out_RawGroup() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_RawGroup() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_RAW, DATE_TO_RAW);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertThat(rankingResult.size(), is(2));
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepX2Out_RawGroupForWeek() throws Exception {

        insertData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecX2In_RawGroupForWeek() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_X2_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecX2Out_RawGroupForWeek() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_X2_OUT_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertThat(rankingResult.size(), is(2));
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1Out_RawGroupForWeek() throws Exception {

        insertData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertThat(rankingResult.size(), is(2));
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDExecS1Out_RawGroupForWeek() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_S1_OUT_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertThat(rankingResult.size(), is(2));
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_RawGroupForWeek() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                SAMPLE_TAC);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_RawGroupForWeek_TacInRawAndNotInDim() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                TAC_NOT_IN_DIM);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                TAC_NOT_IN_DIM);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_RawGroupForWeek_ImeisvInvalidTacZero() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                TAC_EQUAL_TO_ZERO);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                TAC_EQUAL_TO_ZERO);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testQCIecellDetailedAnalysisDrillDownEventIDPrepS1In_RawGroupForWeek_ImeisvNullTacNull() throws Exception {

        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_1, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                NULL_TAC_IDENTIFIER);
        insertData(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATETIME_ID_RAW, 2, TEST_VALUE_QCI_ID_2, TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1,
                NULL_TAC_IDENTIFIER);

        final String json = getJsonResultGroup(INTERNAL_PROC_HO_EXEC_S1_IN_HFA, DATE_FROM_AGG, DATE_TO_AGG);

        final ResultTranslator<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rt = getTranslator();
        final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureSubscriberQOSDrilldownSetupResult.class);
        assertResult(rankingResult);
    }

    private void assertResult(final List<LTEHandoverFailureSubscriberQOSDrilldownSetupResult> results) {
        assertThat(results.size(), is(2));
        for (final LTEHandoverFailureSubscriberQOSDrilldownSetupResult rs : results) {
            if (rs.getTac().equals(Integer.toString(TAC_NOT_IN_DIM))) {
                assertEquals(TERMINAL_MAKE_UNKNOWN, rs.getTerminalMake());
                assertEquals(TERMINAL_MODEL_UNKNOWN, rs.getTerminalModel());
            } else if (rs.getTac().equals(Integer.toString(TAC_EQUAL_TO_ZERO))) {
                assertEquals(TERMINAL_MAKE_INVALID, rs.getTerminalMake());
                assertEquals(TERMINAL_MODEL_INVALID, rs.getTerminalModel());
            } else if (rs.getTac().equals(Integer.toString(SAMPLE_TAC))) {
                assertEquals(MANUFACTURER_FOR_SAMPLE_TAC, rs.getTerminalMake());
                assertEquals(MARKETING_NAME_FOR_SAMPLE_TAC, rs.getTerminalModel());
            } else if (rs.getTac().equals(BLANK_TAC)) {
                assertEquals(TERMINAL_MAKE_EMPTY, rs.getTerminalMake());
                assertEquals(TERMINAL_MODEL_EMPTY, rs.getTerminalModel());
            }
        }
    }
}
