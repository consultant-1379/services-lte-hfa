/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.integritytests.lte.hfa.ranking.causecode;

import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking.LTEHFACCPrepRankingService;

import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHFACauseCodeRankingResults;

import com.ericsson.eniq.events.server.test.util.DateTimeUtilities;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.sql.SQLException;
import java.util.*;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.EventIDConstants.*;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;
import static com.ericsson.eniq.events.server.test.temptables.TempTableNames.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author echimma
 * @since 2011
 *
 */
public class LTEHFACauseCodeRankingPrepServiceAggTest extends
        BaseDataIntegrityTest<LTEHFACauseCodeRankingResults> {

    private LTEHFACCPrepRankingService LTEHandoverFailureCCPrepRankingService;

    private static final String TEST_VALUE_CAUSE_CODE_ID_1 = "10";

    private static final String TEST_VALUE_CAUSE_CODE_ID_2 = "11";

    private static final String TEST_VALUE_CAUSE_CODE_ID_3 = "12";

    private static final String TEST_VALUE_CAUSE_CODE_ID_4 = "13";

    private static final String TEST_VALUE_CAUSE_CODE_DESC_1 = "Cause Code 1";

    private static final String TEST_VALUE_CAUSE_CODE_DESC_2 = "Cause Code 2";

    private static final String TEST_VALUE_CAUSE_CODE_DESC_3 = "Cause Code 3";

    private static final String TEST_VALUE_CAUSE_CODE_DESC_4 = "Cause Code 4";

    @Before
    public void onSetUp() throws Exception {
    	LTEHandoverFailureCCPrepRankingService = new LTEHFACCPrepRankingService();
        attachDependencies(LTEHandoverFailureCCPrepRankingService);

        createTables();
        insertData();
    }

    /**
     * @throws SQLException 
     * 
     */
    private void insertData() throws SQLException {
        insertTopoData(TEST_VALUE_CAUSE_CODE_ID_1, TEST_VALUE_CAUSE_CODE_DESC_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA);
        insertTopoData(TEST_VALUE_CAUSE_CODE_ID_2, TEST_VALUE_CAUSE_CODE_DESC_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA);
        insertTopoData(TEST_VALUE_CAUSE_CODE_ID_3, TEST_VALUE_CAUSE_CODE_DESC_3, INTERNAL_PROC_HO_PREP_S1_IN_HFA);
        insertTopoData(TEST_VALUE_CAUSE_CODE_ID_4, TEST_VALUE_CAUSE_CODE_DESC_4, INTERNAL_PROC_HO_PREP_S1_OUT_HFA);

        insertTopoData(INTERNAL_PROC_HO_PREP_X2_IN_HFA, "INTERNAL_PROC_HO_PREP_X2_IN_HFA");
        insertTopoData(INTERNAL_PROC_HO_PREP_X2_OUT_HFA, "INTERNAL_PROC_HO_PREP_X2_OUT_HFA");
        insertTopoData(INTERNAL_PROC_HO_PREP_S1_IN_HFA, "INTERNAL_PROC_HO_PREP_S1_IN_HFA");
        insertTopoData(INTERNAL_PROC_HO_PREP_S1_OUT_HFA, "INTERNAL_PROC_HO_PREP_S1_OUT_HFA");
        
        //insert data to raw tables
        insertEventsWithCCAndDateTime(DateTimeUtilities.getDateTimeMinus55Minutes());
        insertEventsWithCCAndDateTime(DateTimeUtilities.getDateTimeMinus48Hours());
    }

    private void insertEventsWithCCAndDateTime(final String dateTime) throws SQLException {
        insertEventWithCauseCode(TEST_VALUE_CAUSE_CODE_ID_1, INTERNAL_PROC_HO_PREP_X2_IN_HFA, dateTime, 4);
        insertEventWithCauseCode(TEST_VALUE_CAUSE_CODE_ID_2, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, dateTime, 1);
        insertEventWithCauseCode(TEST_VALUE_CAUSE_CODE_ID_3, INTERNAL_PROC_HO_PREP_S1_IN_HFA, dateTime, 3);
        insertEventWithCauseCode(TEST_VALUE_CAUSE_CODE_ID_4, INTERNAL_PROC_HO_PREP_S1_OUT_HFA, dateTime, 2);
        
    }

    /**
     * @throws SQLException 
     * 
     */
    private void insertTopoData(final String causeCodeID, final String causeCodeDesc, final String eventID)
            throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(CAUSE_CODE_COLUMN, causeCodeID);
        valuesForTable.put(CAUSE_CODE_DESC_COLUMN, causeCodeDesc);
        valuesForTable.put(EVENT_ID, eventID);
        insertRow(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, valuesForTable);
    }

    private void insertTopoData(final String eventID, final String eventTypeDesc) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(EVENT_ID, eventID);
        valuesForTable.put(EVENT_ID_DESC, eventTypeDesc);
        valuesForTable.put(CATEGORY_ID_2, LTE_HFA_PREP_CATEGORY_ID);
        insertRow(TEMP_DIM_E_LTE_HFA_EVENTTYPE, valuesForTable);
    }

    /**
     * 
     */
    private void insertEventWithCauseCode(final String causeCodeID, final String eventID, final String dateTime,
            final int instances) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        for (int i = 0; i < instances; i++) {
            valuesForTable.clear();
            valuesForTable.put(CAUSE_CODE_COLUMN, causeCodeID);
            valuesForTable.put(EVENT_ID, Integer.valueOf(eventID));
            valuesForTable.put(CATEGORY_ID_2, LTE_HFA_PREP_CATEGORY_ID);
            valuesForTable.put(DATETIME_ID, dateTime);
            valuesForTable.put(NO_OF_ERRORS, 1);
            insertRow(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, valuesForTable);
            insertRow(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, valuesForTable);
        }
    }

    /**
     * @throws Exception 
     * 
     */
    private void createTables() throws Exception {
        final Collection<String> columnsForEETable = new ArrayList<String>();
        columnsForEETable.add(CAUSE_CODE_COLUMN);
        columnsForEETable.add(EVENT_ID);
        columnsForEETable.add(DATETIME_ID);
        columnsForEETable.add(NO_OF_ERRORS);
        columnsForEETable.add(CATEGORY_ID_2);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_15MIN, columnsForEETable);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_CC_ERR_DAY, columnsForEETable);

        columnsForEETable.clear();
        columnsForEETable.add(CAUSE_CODE_COLUMN);
        columnsForEETable.add(CAUSE_CODE_DESC_COLUMN);
        columnsForEETable.add(EVENT_ID);
        
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_CAUSE_CODE, columnsForEETable);

        columnsForEETable.clear();
        columnsForEETable.add(EVENT_ID);
        columnsForEETable.add(CATEGORY_ID_2);
        columnsForEETable.add(EVENT_ID_DESC);
        createTemporaryTable(TEMP_DIM_E_LTE_HFA_EVENTTYPE, columnsForEETable);
    }

    private void assertResult(final List<LTEHFACauseCodeRankingResults> rankingResult) {
        assertThat("There should be exactly 4 results!", rankingResult.size(), is(4));

        final LTEHFACauseCodeRankingResults result1 = rankingResult.get(0);
        assertThat(result1.getRank(), is(1));
        assertThat(result1.getCauseCodeID(), is(TEST_VALUE_CAUSE_CODE_ID_1));
        assertThat(result1.getCauseCodeDesc(), is(TEST_VALUE_CAUSE_CODE_DESC_1));
        assertThat(result1.getFailures(), is(4));

        final LTEHFACauseCodeRankingResults result2 = rankingResult.get(1);
        assertThat(result2.getCauseCodeID(), is(TEST_VALUE_CAUSE_CODE_ID_3));
        assertThat(result2.getCauseCodeDesc(), is(TEST_VALUE_CAUSE_CODE_DESC_3));
        assertThat(result2.getRank(), is(2));
        assertThat(result2.getFailures(), is(3));
    }

    private String getJsonWithTimerange(final String timerange) {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(TIME_QUERY_PARAM, timerange);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);

        return runQuery(LTEHandoverFailureCCPrepRankingService, requestParameters);
    }

    @Test
    public void testGetLTEHFACauseCodeRankingDataAgg15MIN() throws Exception {
        final String json = getJsonWithTimerange(ONE_DAY);
        final ResultTranslator<LTEHFACauseCodeRankingResults> rt = getTranslator();
        final List<LTEHFACauseCodeRankingResults> rankingResult = rt.translateResult(json,
                LTEHFACauseCodeRankingResults.class);
        assertResult(rankingResult);
    }

    @Test
    public void testGetLTEHFACauseCodeRankingDataAggDAY() throws Exception {
        final String json = getJsonWithTimerange(TWO_WEEKS);
        final ResultTranslator<LTEHFACauseCodeRankingResults> rt = getTranslator();
        final List<LTEHFACauseCodeRankingResults> rankingResult = rt.translateResult(json,
                LTEHFACauseCodeRankingResults.class);
        assertResult(rankingResult);
    }
}
