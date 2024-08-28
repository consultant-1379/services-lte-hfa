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

import com.ericsson.eniq.events.server.dataproviders.lte.hfa.LTEHFATerminalExecRankingDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.LTEHFATerminalPrepRankingDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

/**
 * @author ejamves
 * @since 2012
 */
@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFATerminalRankingServiceIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHFATerminalExecRankingService")
    private LTEHFATerminalExecRankingService lteHFATerminalExecRankingService;

    @Resource(name = "lteHFATerminalPrepRankingService")
    private LTEHFATerminalPrepRankingService lteHFATerminalPrepRankingService;

    @Test
    @Parameters(source = LTEHFATerminalExecRankingDataProvider.class)
    public void testTACRankingExec(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFATerminalExecRankingService);
    }

    @Test
    @Parameters(source = LTEHFATerminalPrepRankingDataProvider.class)
    public void testTACRankingPrep(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFATerminalPrepRankingService);
    }
}
