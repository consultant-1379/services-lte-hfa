/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.eniq.events.server.integritytests.lte.hfa.ranking.accessarea;

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

import com.ericsson.eniq.events.server.resources.BaseDataIntegrityTest;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking.LTEHandoverFailureSourceCellExecRankingService;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking.LTEHandoverFailureTargetCellExecRankingService;
import com.ericsson.eniq.events.server.test.queryresults.ResultTranslator;
import com.ericsson.eniq.events.server.test.queryresults.lte.handoverfailure.LTEHandoverFailureCellRankingResult;
import com.ericsson.eniq.events.server.test.util.DateTimeUtilities;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LTEHandoverFailureCellExecRankingServiceAggTest extends BaseDataIntegrityTest<LTEHandoverFailureCellRankingResult> {

    private LTEHandoverFailureSourceCellExecRankingService lteHandoverFailureSourceCellExecService;

    private LTEHandoverFailureTargetCellExecRankingService lteHandoverFailureTargetCellExecService;

    private static final String TEST_VALUE_HIER3_ID_1 = "3135210477467174988";

    private static final String TEST_VALUE_THIER3_ID_1 = "3135210477467174988";

    private static final String TEST_VALUE_HIER321_ID_1 = "7210756712490856541";

    private static final String TEST_VALUE_THIER321_ID_1 = "7210756712490856541";

    private static final String TEST_VALUE_ENODEB_NAME_1 = "eNodeB_1";

    private static final String TEST_VALUE_ECELL_NAME_1 = "eCell_1";

    private static final String TEST_VALUE_HIER3_ID_2 = "3135210477467174989";

    private static final String TEST_VALUE_THIER3_ID_2 = "3135210477467174989";

    private static final String TEST_VALUE_HIER321_ID_2 = "7210756712490856542";

    private static final String TEST_VALUE_THIER321_ID_2 = "7210756712490856542";

    private static final String TEST_VALUE_ENODEB_NAME_2 = "eNodeB_2";

    private static final String TEST_VALUE_ECELL_NAME_2 = "eCell_2";

    private static final String TEST_VALUE_HIER3_ID_3 = "3135210477467174990";

    private static final String TEST_VALUE_THIER3_ID_3 = "3135210477467174990";

    private static final String TEST_VALUE_HIER321_ID_3 = "7210756712490856543";

    private static final String TEST_VALUE_THIER321_ID_3 = "7210756712490856543";

    private static final String TEST_VALUE_ENODEB_NAME_3 = "eNodeB_3";

    private static final String TEST_VALUE_ECELL_NAME_3 = "eCell_3";

    private static final int TEST_VALUE_RAT = 2;

    private static final String TEST_VALUE_RAT_DESC = "LTE";

    private static final String TEST_VALUE_THIER321_ID_4 = "0";

    private static final String TEST_VALUE_THIER3_ID_4 = "0";

    @Before
    public void onSetUp() throws Exception {
        lteHandoverFailureSourceCellExecService = new LTEHandoverFailureSourceCellExecRankingService();
        attachDependencies(lteHandoverFailureSourceCellExecService);

        lteHandoverFailureTargetCellExecService = new LTEHandoverFailureTargetCellExecRankingService();
        attachDependencies(lteHandoverFailureTargetCellExecService);

        createTables();
        insertData();
    }

    private void createTables() throws Exception {
        final Collection<String> columnsForTable = new ArrayList<String>();
        columnsForTable.clear();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(NO_OF_ERRORS);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_DAY, columnsForTable);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_15MIN, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(THIER3_HASHID);
        columnsForTable.add(THIER321_HASHID);
        columnsForTable.add(CATEGORY_ID_SQL_PARAM);
        columnsForTable.add(DATETIME_ID);
        columnsForTable.add(NO_OF_ERRORS);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_THIER321_EVENTID_ERR_DAY, columnsForTable);
        createTemporaryTable(TEMP_EVENT_E_LTE_HFA_THIER321_EVENTID_ERR_15MIN, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(HIER3_ID);
        columnsForTable.add(HIER321_ID);
        columnsForTable.add(HIERARCHY_3);
        columnsForTable.add(HIERARCHY_1);
        columnsForTable.add(VENDOR);
        columnsForTable.add(RAT);
        createTemporaryTable(TEMP_DIM_E_LTE_HIER321, columnsForTable);

        columnsForTable.clear();
        columnsForTable.add(RAT);
        columnsForTable.add(RAT_DESC);
        createTemporaryTable(TEMP_DIM_E_SGEH_RAT, columnsForTable);
    }

    private void insertTopoData(final String hier3ID, final String hier321ID, final String eNodeBName, final String cellName) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        valuesForTable.put(HIER3_ID, hier3ID);
        valuesForTable.put(HIER321_ID, hier321ID);
        valuesForTable.put(HIERARCHY_3, eNodeBName);
        valuesForTable.put(HIERARCHY_1, cellName);
        valuesForTable.put(VENDOR, ERICSSON);
        valuesForTable.put(RAT, TEST_VALUE_RAT);
        insertRow(TEMP_DIM_E_LTE_HIER321, valuesForTable);

        valuesForTable.clear();
        valuesForTable.put(RAT, TEST_VALUE_RAT);
        valuesForTable.put(RAT_DESC, TEST_VALUE_RAT_DESC);
        insertRow(TEMP_DIM_E_SGEH_RAT, valuesForTable);
    }

    private void insertSourcecell(final String hier3ID, final String hier321ID, final String dateTime, final int instances) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        for (int i = 0; i < instances; i++) {
            valuesForTable.clear();
            valuesForTable.put(HIER3_ID, hier3ID);
            valuesForTable.put(HIER321_ID, hier321ID);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_EXEC);
            valuesForTable.put(DATETIME_ID, dateTime);
            valuesForTable.put(NO_OF_ERRORS, 1);
            // valuesForTable.put(TAC, SAMPLE_TAC);
            insertRow(TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_DAY, valuesForTable);
            insertRow(TEMP_EVENT_E_LTE_HFA_HIER321_EVENTID_ERR_15MIN, valuesForTable);
        }
    }

    private void insertTargetcell(final String thier3ID, final String thier321ID, final String dateTime, final int instances) throws SQLException {
        final Map<String, Object> valuesForTable = new HashMap<String, Object>();
        for (int i = 0; i < instances; i++) {
            valuesForTable.clear();
            valuesForTable.put(THIER3_HASHID, thier3ID);
            valuesForTable.put(THIER321_HASHID, thier321ID);
            valuesForTable.put(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_EXEC);
            valuesForTable.put(DATETIME_ID, dateTime);
            valuesForTable.put(NO_OF_ERRORS, 1);
            // valuesForTable.put(TAC, SAMPLE_TAC);
            insertRow(TEMP_EVENT_E_LTE_HFA_THIER321_EVENTID_ERR_DAY, valuesForTable);
            insertRow(TEMP_EVENT_E_LTE_HFA_THIER321_EVENTID_ERR_15MIN, valuesForTable);
        }
    }

    private void insertData() throws SQLException {
        // 1. Insert topology data
        insertTopoData(TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1, TEST_VALUE_ENODEB_NAME_1, TEST_VALUE_ECELL_NAME_1);
        insertTopoData(TEST_VALUE_HIER3_ID_2, TEST_VALUE_HIER321_ID_2, TEST_VALUE_ENODEB_NAME_2, TEST_VALUE_ECELL_NAME_2);
        insertTopoData(TEST_VALUE_HIER3_ID_3, TEST_VALUE_HIER321_ID_3, TEST_VALUE_ENODEB_NAME_3, TEST_VALUE_ECELL_NAME_3);

        // 2. Insert the Source Cell Data
        insertSourceCellWithDateTime(DateTimeUtilities.getDateTimeMinus55Minutes());
        insertSourceCellWithDateTime(DateTimeUtilities.getDateTimeMinus48Hours());

        // 3. Insert the Target Cell Data
        insertTargetCellWithDateTime(DateTimeUtilities.getDateTimeMinus55Minutes());
        insertTargetCellWithDateTime(DateTimeUtilities.getDateTimeMinus48Hours());
    }

    private void insertSourceCellWithDateTime(final String dateTime) throws SQLException {
        insertSourcecell(TEST_VALUE_HIER3_ID_1, TEST_VALUE_HIER321_ID_1, dateTime, 3);
        insertSourcecell(TEST_VALUE_HIER3_ID_2, TEST_VALUE_HIER321_ID_2, dateTime, 2);
        insertSourcecell(TEST_VALUE_HIER3_ID_2, TEST_VALUE_HIER321_ID_3, dateTime, 1);
    }

    private void insertTargetCellWithDateTime(final String dateTime) throws SQLException {
        insertTargetcell(TEST_VALUE_THIER3_ID_1, TEST_VALUE_THIER321_ID_1, dateTime, 3);
        insertTargetcell(TEST_VALUE_THIER3_ID_2, TEST_VALUE_THIER321_ID_2, dateTime, 2);
        insertTargetcell(TEST_VALUE_THIER3_ID_3, TEST_VALUE_THIER321_ID_3, dateTime, 1);
        insertTargetcell(TEST_VALUE_THIER3_ID_4, TEST_VALUE_THIER321_ID_4, dateTime, 1);
    }

    // SourceCellExec Testing
    @Test
    public void testGetLTEHFASourceCellExecRankingDataAgg15MIN() throws Exception {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(TIME_QUERY_PARAM, ONE_DAY);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_EXEC);

        final String json = runQuery(lteHandoverFailureSourceCellExecService, requestParameters);

        final ResultTranslator<LTEHandoverFailureCellRankingResult> rt = getTranslator();
        final List<LTEHandoverFailureCellRankingResult> rankingResult = rt.translateResult(json, LTEHandoverFailureCellRankingResult.class);
        assertResultSource(rankingResult);
    }

    @Test
    public void testGetLTEHFASourceCellExecRankingDataAggDay() throws Exception {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(TIME_QUERY_PARAM, TWO_WEEKS);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_EXEC);

        final String json = runQuery(lteHandoverFailureSourceCellExecService, requestParameters);

        final ResultTranslator<LTEHandoverFailureCellRankingResult> rt = getTranslator();
        final List<LTEHandoverFailureCellRankingResult> rankingResult = rt.translateResult(json, LTEHandoverFailureCellRankingResult.class);
        assertResultSource(rankingResult);
    }

    private void assertResultSource(final List<LTEHandoverFailureCellRankingResult> rankingResult) {

        assertThat("There should be exactly 3 results!", rankingResult.size(), is(3));

        final LTEHandoverFailureCellRankingResult result1 = rankingResult.get(0);
        assertThat(result1.getRank(), is(1));
        assertThat(result1.getCategoryID(), is(CATEGORY_ID_2_EXEC));
        assertThat(result1.getHier321ID(), is(TEST_VALUE_HIER321_ID_1));
        assertThat(result1.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_1));
        assertThat(result1.getCell(), is(TEST_VALUE_ECELL_NAME_1));
        assertThat(result1.getVendor(), is(ERICSSON));
        assertThat(result1.getRatDesc(), is("LTE"));
        assertThat(result1.getFailures(), is(3));

        final LTEHandoverFailureCellRankingResult result2 = rankingResult.get(1);
        assertThat(result2.getRank(), is(2));
        assertThat(result2.getCategoryID(), is(CATEGORY_ID_2_EXEC));
        assertThat(result2.getHier321ID(), is(TEST_VALUE_HIER321_ID_2));
        assertThat(result2.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_2));
        assertThat(result2.getCell(), is(TEST_VALUE_ECELL_NAME_2));
        assertThat(result2.getVendor(), is(ERICSSON));
        assertThat(result2.getRatDesc(), is("LTE"));
        assertThat(result2.getFailures(), is(2));

        final LTEHandoverFailureCellRankingResult result3 = rankingResult.get(2);
        assertThat(result3.getRank(), is(3));
        assertThat(result3.getCategoryID(), is(CATEGORY_ID_2_EXEC));
        assertThat(result3.getHier321ID(), is(TEST_VALUE_HIER321_ID_3));
        assertThat(result3.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_3));
        assertThat(result3.getCell(), is(TEST_VALUE_ECELL_NAME_3));
        assertThat(result3.getVendor(), is(ERICSSON));
        assertThat(result3.getRatDesc(), is("LTE"));
        assertThat(result3.getFailures(), is(1));
    }

    // TargetCell Exec Testing
    @Test
    public void testGetLTEHFATargetCellExecRankingDataAgg15MIN() throws Exception {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(TIME_QUERY_PARAM, ONE_DAY);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_EXEC);

        final String json = runQuery(lteHandoverFailureTargetCellExecService, requestParameters);

        final ResultTranslator<LTEHandoverFailureCellRankingResult> rt = getTranslator();
        final List<LTEHandoverFailureCellRankingResult> rankingResult = rt.translateResult(json, LTEHandoverFailureCellRankingResult.class);

        assertResultTarget(rankingResult);
    }

    @Test
    public void testGetLTEHFATargetCellExecRankingDataAggDay() throws Exception {
        final MultivaluedMap<String, String> requestParameters = new MultivaluedMapImpl();
        requestParameters.putSingle(DISPLAY_PARAM, GRID);
        requestParameters.putSingle(TIME_QUERY_PARAM, TWO_WEEKS);
        requestParameters.putSingle(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR);
        requestParameters.putSingle(MAX_ROWS, DEFAULT_MAX_ROWS);
        requestParameters.putSingle(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_EXEC);

        final String json = runQuery(lteHandoverFailureTargetCellExecService, requestParameters);

        final ResultTranslator<LTEHandoverFailureCellRankingResult> rt = getTranslator();
        final List<LTEHandoverFailureCellRankingResult> rankingResult = rt.translateResult(json, LTEHandoverFailureCellRankingResult.class);
        assertResultTarget(rankingResult);
    }

    private void assertResultTarget(final List<LTEHandoverFailureCellRankingResult> rankingResult) {

        assertThat("There should be exactly 3 results!", rankingResult.size(), is(3));

        final LTEHandoverFailureCellRankingResult result1 = rankingResult.get(0);
        assertThat(result1.getRank(), is(1));
        assertThat(result1.getCategoryID(), is(CATEGORY_ID_2_EXEC));
        assertThat(result1.getTHier321ID(), is(TEST_VALUE_THIER321_ID_1));
        assertThat(result1.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_1));
        assertThat(result1.getCell(), is(TEST_VALUE_ECELL_NAME_1));
        assertThat(result1.getVendor(), is(ERICSSON));
        assertThat(result1.getRatDesc(), is("LTE"));
        assertThat(result1.getFailures(), is(3));

        final LTEHandoverFailureCellRankingResult result2 = rankingResult.get(1);
        assertThat(result2.getRank(), is(2));
        assertThat(result2.getCategoryID(), is(CATEGORY_ID_2_EXEC));
        assertThat(result2.getTHier321ID(), is(TEST_VALUE_THIER321_ID_2));
        assertThat(result2.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_2));
        assertThat(result2.getCell(), is(TEST_VALUE_ECELL_NAME_2));
        assertThat(result2.getVendor(), is(ERICSSON));
        assertThat(result2.getRatDesc(), is("LTE"));
        assertThat(result2.getFailures(), is(2));

        final LTEHandoverFailureCellRankingResult result3 = rankingResult.get(2);
        assertThat(result3.getRank(), is(3));
        assertThat(result3.getCategoryID(), is(CATEGORY_ID_2_EXEC));
        assertThat(result3.getTHier321ID(), is(TEST_VALUE_THIER321_ID_3));
        assertThat(result3.getEnodeB(), is(TEST_VALUE_ENODEB_NAME_3));
        assertThat(result3.getCell(), is(TEST_VALUE_ECELL_NAME_3));
        assertThat(result3.getVendor(), is(ERICSSON));
        assertThat(result3.getRatDesc(), is("LTE"));
        assertThat(result3.getFailures(), is(1));

    }
}
