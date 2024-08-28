/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.integritytests.lte.hfa.qos.qcianalysis.enodeb;

import com.ericsson.eniq.events.server.integritytests.stubs.ReplaceTablesWithTempTablesTemplateUtils;
import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis.LTEHFAEnodeBQCICategorySummaryService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.qos.LTEHFAQOSENodeBGroupQCICategorySummaryResult;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;
import static com.ericsson.eniq.events.server.test.temptables.TempTableNames.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author echimma
 * @since 2011
 *
 */
public class LTEHFAENodeBGroupQCICategorySummaryServiceRawTest extends
        BaseDataIntegrityTest<LTEHFAQOSENodeBGroupQCICategorySummaryResult> {
    LTEHFAEnodeBQCICategorySummaryService lteHFAEnodeBQCICategarySummaryService;

    private static final String DATETIME_ID_RAW = "2011-09-20 08:12:00";

    private static final String DATE_FROM_RAW = "20092011";

    private static final String DATE_TO_RAW = "20092011";

    private static final String TIME_FROM = "0900";

    private static final String TIME_TO = "0930";

    private static final long TEST_VALUE_HIER3_ID_1 = 3135210477467174988L;

    private static final long TEST_VALUE_HIER3_ID_2 = 4809532081614999117L;

    private static final int TEST_VALUE_QCI_ID_1 = 1;

    private static final int TEST_VALUE_QCI_ID_2 = 2;

    private static final int TEST_VALUE_QCI_ID_3 = 3;

    private static final int CATEGORY_ID_2_CALL_SETUP = 1;

    private static final int CATEGORY_ID_2_CALL_DROP = 0;

    private static final String TEST_VALUE_GROUP_NAME = "tracGroup";

    private static final String TEST_VALUE_IMSI = "11111119";

    /**
     * 1. Create tables.
     * 2. Insert test datas to the tables.
     * @throws Exception
     */
    @Before
    public void onSetUp() throws Exception {
        lteHFAEnodeBQCICategarySummaryService = new LTEHFAEnodeBQCICategorySummaryService();
        attachDependencies(lteHFAEnodeBQCICategarySummaryService);
        ReplaceTablesWithTempTablesTemplateUtils.addTableNameToReplace(DIM_E_LTE_QCI, TEMP_DIM_E_LTE_QCI);
        createTable();
        insertTopoData();
        insertRawData();
    }

    private void createTable() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(IMSI);
        columnsForTable.add(CATEGORY_ID_2);
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(QCI_ID_COLUMN);
        columnsForTable.add(TAC);
        columnsForTable.add(DATETIME_ID);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_ERR_RAW, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(CATEGORY_ID_2);
        columnsForTable.add(CATEGORY_ID_2_DESC);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_EVENTTYPE, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(QCI_NUMBER_COLUMN);
        columnsForTable.add(QCI_ID_DESCRIPTION);
        createTemporaryTable(TEMP_DIM_E_LTE_QCI, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(GROUP_NAME);
        createTemporaryTable(TEMP_GROUP_TYPE_E_RAT_VEND_HIER3, columnsForTable);
    }

    private void insertTopoData() throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();

        valuesForTable.put(CATEGORY_ID_2, CATEGORY_ID_2_CALL_DROP);
        valuesForTable.put(CATEGORY_ID_2_DESC, "Call Drops");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(CATEGORY_ID_2, CATEGORY_ID_2_CALL_SETUP);
        valuesForTable.put(CATEGORY_ID_2_DESC, "Call Setup Failures");
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(QCI_NUMBER_COLUMN, 1);
        valuesForTable.put(QCI_ID_DESCRIPTION, "Conversational Voice");
        insertRow(TEMP_DIM_E_LTE_QCI, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(QCI_NUMBER_COLUMN, 2);
        valuesForTable.put(QCI_ID_DESCRIPTION, "Conversational Video (Live Streaming)");
        insertRow(TEMP_DIM_E_LTE_QCI, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(QCI_NUMBER_COLUMN, 3);
        valuesForTable.put(QCI_ID_DESCRIPTION, "Real Time Gaming");
        insertRow(TEMP_DIM_E_LTE_QCI, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(TAC, TEST_VALUE_EXCLUSIVE_TAC);
        valuesForTable.put(GROUP_NAME, EXCLUSIVE_TAC_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_TAC, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(HIER3_ID, TEST_VALUE_HIER3_ID_1);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER3, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(HIER3_ID, TEST_VALUE_HIER3_ID_1);
        valuesForTable.put(GROUP_NAME, TEST_VALUE_GROUP_NAME);
        insertRow(TEMP_GROUP_TYPE_E_RAT_VEND_HIER3, valuesForTable);
    }

    private void insertData(final String tac, final long hire3ID, final int categoryID, final int qciID,
            final String time, final int instances) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        for (int i = 0; i < instances; i++) {
            valuesForTable.put(TAC, tac);
            valuesForTable.put(IMSI_PARAM, TEST_VALUE_IMSI);
            valuesForTable.put(DATETIME_ID, time);
            valuesForTable.put(CATEGORY_ID_2, categoryID);
            valuesForTable.put(HIER3_ID, hire3ID);
            valuesForTable.put(QCI_ID_COLUMN, qciID);
            insertRow(TEMP_EVENT_E_LTE_HFA_ARRAY_ERAB_ERR_RAW, valuesForTable);
            valuesForTable.clear();
        }
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
        requestParameters.putSingle(GROUP_NAME_PARAM, TEST_VALUE_GROUP_NAME);
        
        return runQuery(lteHFAEnodeBQCICategarySummaryService, requestParameters);
    }

    private void insertRawData() throws Exception {
        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_1, DATETIME_ID_RAW,
                1);
        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_1, DATETIME_ID_RAW,
                1);

        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_1,
                DATETIME_ID_RAW, 1);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_1,
                DATETIME_ID_RAW, 1);

        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_1, DATETIME_ID_RAW,
                2);
        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_1, DATETIME_ID_RAW,
                2);

        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_1,
                DATETIME_ID_RAW, 2);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_1,
                DATETIME_ID_RAW, 2);

        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_2, DATETIME_ID_RAW,
                3);
        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_2, DATETIME_ID_RAW,
                3);

        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_2,
                DATETIME_ID_RAW, 3);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_2,
                DATETIME_ID_RAW, 3);

        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_2, DATETIME_ID_RAW,
                4);
        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_2, DATETIME_ID_RAW,
                4);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_2,
                DATETIME_ID_RAW, 4);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_2,
                DATETIME_ID_RAW, 4);

        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_3, DATETIME_ID_RAW,
                5);
        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_3, DATETIME_ID_RAW,
                5);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_3,
                DATETIME_ID_RAW, 5);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_DROP, TEST_VALUE_QCI_ID_3,
                DATETIME_ID_RAW, 5);

        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_3, DATETIME_ID_RAW,
                6);
        insertData(TEST_VALUE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_3, DATETIME_ID_RAW,
                6);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_1, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_3,
                DATETIME_ID_RAW, 6);
        insertData(TEST_VALUE_EXCLUSIVE_TAC, TEST_VALUE_HIER3_ID_2, CATEGORY_ID_2_CALL_SETUP, TEST_VALUE_QCI_ID_3,
                DATETIME_ID_RAW, 6);
    }

    private void assertResult(final List<LTEHFAQOSENodeBGroupQCICategorySummaryResult> results) {
        assertThat(results.size(), is(6));
        int i = 1;
        for (final LTEHFAQOSENodeBGroupQCICategorySummaryResult rs : results) {
            assertEquals(rs.getFailures(), i * 2);
            i++;
        }
    }

    @Test
    public void testENodeBGroupQCICategaryEventSummary() throws Exception {
        final String json = getJsonResult();
        final ResultTranslator<LTEHFAQOSENodeBGroupQCICategorySummaryResult> rt = getTranslator();
        final List<LTEHFAQOSENodeBGroupQCICategorySummaryResult> eventResult = rt.translateResult(json,
                LTEHFAQOSENodeBGroupQCICategorySummaryResult.class);
        assertResult(eventResult);
    }
}
