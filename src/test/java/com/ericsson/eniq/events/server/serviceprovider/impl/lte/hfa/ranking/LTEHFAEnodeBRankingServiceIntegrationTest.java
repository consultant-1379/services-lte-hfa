/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBExecRankingDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBPrepRankingDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author evijred
 * @since 2011
 */
@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class LTEHFAEnodeBRankingServiceIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHandoverFailureEnodeBExecRankingService")
    private LTEHandoverFailureEnodeBExecRankingService lteHandoverFailureEnodeBExecRankingService;

    @Resource(name = "lteHandoverFailureEnodeBPrepRankingService")
    private LTEHandoverFailureEnodeBPrepRankingService lteHandoverFailureEnodeBPrepRankingService;

    @Test
    @Parameters(source = LTEHFAEnodeBExecRankingDataProvider.class)
    public void testENodeBExecRankingGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBExecRankingService);
    }

    @Test
    @Parameters(source = LTEHFAEnodeBPrepRankingDataProvider.class)
    public void testENodeBPrepRankingGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBPrepRankingService);
    }

}
