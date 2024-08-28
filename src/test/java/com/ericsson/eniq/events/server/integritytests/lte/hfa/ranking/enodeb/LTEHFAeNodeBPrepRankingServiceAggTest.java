/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.integritytests.lte.hfa.ranking.enodeb;

import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking.LTEHandoverFailureEnodeBPrepRankingService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHandoverFailureEnodeBRankingResult;
import com.ericsson.eniq.events.server.test.util.DateTimeUtilities;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.sql.SQLException;
import java.util.*;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.EventIDConstants.CATEGORY_ID_2_PREP;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;
import static com.ericsson.eniq.events.server.test.temptables.TempTableNames.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author evijred
 * @since 2011
 *
 */
public class LTEHFAeNodeBPrepRankingServiceAggTest extends
        BaseDataIntegrityTest<LTEHandoverFailureEnodeBRankingResult> {

    private LTEHandoverFailureEnodeBPrepRankingService lteHandoverFailureEnodeBPrepRankingService;

    private static final String TEST_VALUE_HIER3_ID_1 = "4578785454578545451";

    private static final String TEST_VALUE_ENODEB_NAME_1 = "eNodeB_HFA1";

    private static final String TEST_VALUE_HIER3_ID_2 = "4578785454578545452";

    private static final String TEST_VALUE_ENODEB_NAME_2 = "eNodeB_HFA2";

    private static final String TEST_VALUE_HIER3_ID_3 = "4578785454578545453";

    private static final String TEST_VALUE_ENODEB_NAME_3 = "eNodeB_HFA3";
    
    private static final int TEST_VALUE_RAT = 2;
    
    private static final String TEST_VALUE_RAT_DESC = "LTE";

    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureEnodeBPrepRankingService = new LTEHandoverFailureEnodeBPrepRankingService();
        attachDependencies(lteHandoverFailureEnodeBPrepRankingService);

        createTables();
        insertData();
    }

    private void createTables() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(NO_OF_ERRORS);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, columnsForTable);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(HIERARCHY_3);
        columnsForTable.add(VENDOR);
        columnsForTable.add(RAT);
        createTemporaryTable(TEMP_DIM_E_LTE_HIER321, columnsForTable);
        
        columnsForTable.clear();
        columnsForTable.add(RAT);
        columnsForTable.add(RAT_DESC);
        createTemporaryTable(TEMP_DIM_E_SGEH_RAT, columnsForTable);
    }

    private void insertTopoData(final String hier3ID, final String eNodeBName) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(HIER3_ID, hier3ID);
        valuesForTable.put(HIERARCHY_3, eNodeBName);
        valuesForTable.put(VENDOR, ERICSSON);
        valuesForTable.put(RAT, TEST_VALUE_RAT);
        insertRow(TEMP_DIM_E_LTE_HIER321, valuesForTable);
        
        valuesForTable.clear();
        valuesForTable.put(RAT, TEST_VALUE_RAT);
        valuesForTable.put(RAT_DESC, TEST_VALUE_RAT_DESC);
        insertRow(TEMP_DIM_E_SGEH_RAT, valuesForTable);     
    }

    private void inserteNodeB(final String hier3ID, final String dateTime, final int instances) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        for (int i = 0; i < instances; i++) {
            valuesForTable.clear();
            valuesForTable.put(HIER3_ID, hier3ID);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_PREP);
            valuesForTable.put(DATETIME_ID, dateTime);
            valuesForTable.put(NO_OF_ERRORS, 1);
            insertRow(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_DAY, valuesForTable);
            insertRow(TEMP_EVENT_E_LTE_HFA_HIER3_EVENTID_ERR_15MIN, valuesForTable);
        }
    }

    private void insertData() throws SQLException {
        insertTopoData(TEST_VALUE_HIER3_ID_1, TEST_VALUE_ENODEB_NAME_1);
        insertTopoData(TEST_VALUE_HIER3_ID_2, TEST_VALUE_ENODEB_NAME_2);
        insertTopoData(TEST_VALUE_HIER3_ID_3, TEST_VALUE_ENODEB_NAME_3);

        insertEnodeBWithDateTime(DateTimeUtilities.getDateTimeMinus55Minutes());
        insertEnodeBWithDateTime(DateTimeUtilities.getDateTimeMinus48Hours());
    }

    private void insertEnodeBWithDateTime(final String dateTime) throws SQLException {
        inserteNodeB(TEST_VALUE_HIER3_ID_1, dateTime, 3);
        inserteNodeB(TEST_VALUE_HIER3_ID_2, dateTime, 2);
        inserteNodeB(TEST_VALUE_HIER3_ID_3, dateTime, 1);
    }

    @Test
    public void testGetLTEHFAeNodeBPrepRankingDataAgg15MIN() throws Exception {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(TIME_QUERY_PARAM, ONE_DAY);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_PREP);

        final String json = runQuery(lteHandoverFailureEnodeBPrepRankingService, requestParameters);

        final ResultTranslator<LTEHandoverFailureEnodeBRankingResult> rt = getTranslator();
        final List<LTEHandoverFailureEnodeBRankingResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureEnodeBRankingResult.class);
        assertResult(rankingResult);
    }

    @Test
    public void testGetLTEHFAeNodeBPrepRankingDataAggDAY() throws Exception {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(TIME_QUERY_PARAM, TWO_WEEKS);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_PREP);

        final String json = runQuery(lteHandoverFailureEnodeBPrepRankingService, requestParameters);

        final ResultTranslator<LTEHandoverFailureEnodeBRankingResult> rt = getTranslator();
        final List<LTEHandoverFailureEnodeBRankingResult> rankingResult = rt.translateResult(json,
                LTEHandoverFailureEnodeBRankingResult.class);
        assertResult(rankingResult);
    }

    private void assertResult(final List<LTEHandoverFailureEnodeBRankingResult> rankingResult) {
        assertThat("There should be exactly 3 results!", rankingResult.size(), is(3));

        final LTEHandoverFailureEnodeBRankingResult result1 = rankingResult.get(0);
        assertThat(result1.getRank(), is(1));
        assertThat(result1.getCategoryID(), is(CATEGORY_ID_2_PREP));
        assertThat(result1.getHier3ID(), is(TEST_VALUE_HIER3_ID_1));
        assertThat(result1.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_1));
        assertThat(result1.getVendor(), is(ERICSSON));
        assertThat(result1.getRatDesc(), is("LTE"));
        assertThat(result1.getFailures(), is(3));

        final LTEHandoverFailureEnodeBRankingResult result2 = rankingResult.get(1);
        assertThat(result2.getRank(), is(2));
        assertThat(result2.getCategoryID(), is(CATEGORY_ID_2_PREP));
        assertThat(result2.getHier3ID(), is(TEST_VALUE_HIER3_ID_2));
        assertThat(result2.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_2));
        assertThat(result2.getVendor(), is(ERICSSON));
        assertThat(result2.getRatDesc(), is("LTE"));
        assertThat(result2.getFailures(), is(2));

        final LTEHandoverFailureEnodeBRankingResult result3 = rankingResult.get(2);
        assertThat(result3.getRank(), is(3));
        assertThat(result3.getCategoryID(), is(CATEGORY_ID_2_PREP));
        assertThat(result3.getHier3ID(), is(TEST_VALUE_HIER3_ID_3));
        assertThat(result3.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_3));
        assertThat(result3.getVendor(), is(ERICSSON));
        assertThat(result3.getRatDesc(), is("LTE"));
        assertThat(result3.getFailures(), is(1));
    }
}
