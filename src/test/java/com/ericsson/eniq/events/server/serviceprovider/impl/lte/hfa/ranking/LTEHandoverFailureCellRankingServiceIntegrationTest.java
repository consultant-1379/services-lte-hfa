/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHandoverFailureCellExecRankingDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHandoverFailureCellPrepRankingDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;
import com.ericsson.eniq.events.server.test.util.JSONTestUtils;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHandoverFailureCellRankingServiceIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHandoverFailureSourceCellExecRankingService")
    private LTEHandoverFailureSourceCellExecRankingService lteHandoverFailureSourceCellExecRankingService;

    @Resource(name = "lteHandoverFailureSourceCellPrepRankingService")
    private LTEHandoverFailureSourceCellPrepRankingService lteHandoverFailureSourceCellPrepRankingService;

    @Resource(name = "lteHandoverFailureTargetCellExecRankingService")
    private LTEHandoverFailureTargetCellExecRankingService lteHandoverFailureTargetCellExecRankingService;

    @Resource(name = "lteHandoverFailureTargetCellPrepRankingService")
    private LTEHandoverFailureTargetCellPrepRankingService lteHandoverFailureTargetCellPrepRankingService;

    @Test
    @Parameters(source = LTEHandoverFailureCellExecRankingDataProvider.class)
    public void testSourceCellExecRankingGetData(final MultivaluedMap<String, String> requestParameters) {
        final String result = runQuery(requestParameters, lteHandoverFailureSourceCellExecRankingService);
        JSONTestUtils.assertJSONSucceeds(result);
        System.out.println(result);
    }

    @Test
    @Parameters(source = LTEHandoverFailureCellExecRankingDataProvider.class)
    public void testSourceCellExecRanking_Drilldown_CSV(final MultivaluedMap<String, String> requestParameters) {
        runQueryForCSV(requestParameters, lteHandoverFailureSourceCellExecRankingService);
    }

    @Test
    @Parameters(source = LTEHandoverFailureCellPrepRankingDataProvider.class)
    public void testSourceCellPrepRankingGetData(final MultivaluedMap<String, String> requestParameters) {
        final String result = runQuery(requestParameters, lteHandoverFailureSourceCellPrepRankingService);
        JSONTestUtils.assertJSONSucceeds(result);
        System.out.println(result);
    }

    @Test
    @Parameters(source = LTEHandoverFailureCellPrepRankingDataProvider.class)
    public void testSourceCellPrepRanking_Drilldown_CSV(final MultivaluedMap<String, String> requestParameters) {
        runQueryForCSV(requestParameters, lteHandoverFailureSourceCellPrepRankingService);
    }

    @Test
    @Parameters(source = LTEHandoverFailureCellExecRankingDataProvider.class)
    public void testTargetCellExecRankingGetData(final MultivaluedMap<String, String> requestParameters) {
        final String result = runQuery(requestParameters, lteHandoverFailureTargetCellExecRankingService);
        JSONTestUtils.assertJSONSucceeds(result);
        System.out.println(result);
    }

    @Test
    @Parameters(source = LTEHandoverFailureCellExecRankingDataProvider.class)
    public void testTargetCellExecRanking_Drilldown_CSV(final MultivaluedMap<String, String> requestParameters) {
        runQueryForCSV(requestParameters, lteHandoverFailureTargetCellExecRankingService);
    }

    @Test
    @Parameters(source = LTEHandoverFailureCellPrepRankingDataProvider.class)
    public void testTargetCellPrepRankingGetData(final MultivaluedMap<String, String> requestParameters) {
        final String result = runQuery(requestParameters, lteHandoverFailureTargetCellPrepRankingService);
        JSONTestUtils.assertJSONSucceeds(result);
        System.out.println(result);
    }

    @Test
    @Parameters(source = LTEHandoverFailureCellPrepRankingDataProvider.class)
    public void testTargetCellPrepRanking_Drilldown_CSV(final MultivaluedMap<String, String> requestParameters) {
        runQueryForCSV(requestParameters, lteHandoverFailureTargetCellPrepRankingService);
    }

}
