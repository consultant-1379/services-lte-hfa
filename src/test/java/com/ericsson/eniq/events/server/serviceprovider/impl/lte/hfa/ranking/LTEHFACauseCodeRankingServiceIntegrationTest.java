/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking;


import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFACauseCodeRankingExecDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author echimma
 * @since 2011
 */
@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class LTEHFACauseCodeRankingServiceIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHandoverFailureCCPrepRankingService")
    private LTEHFACCPrepRankingService lteHandoverFailureCCPrepRankingService;

    @Resource(name = "lteHandoverFailureCCExecRankingService")
    private LTEHFACCExecRankingService lteHandoverFailureCCExecRankingService;

    @Test
    @Parameters(source = LTEHFACauseCodeRankingExecDataProvider.class)
    public void testCauseCodeExecRankingGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureCCExecRankingService);
    }

    @Test
    @Parameters(source = LTEHFACauseCodeRankingExecDataProvider.class)
    public void testCauseCodePrepRankingGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureCCPrepRankingService);
    }
}
