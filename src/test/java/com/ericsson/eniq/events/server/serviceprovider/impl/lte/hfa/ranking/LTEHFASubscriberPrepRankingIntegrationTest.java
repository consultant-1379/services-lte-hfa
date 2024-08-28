/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFASubscriberPrepRankingTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author ejamves
 * @since 2011
 */

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class LTEHFASubscriberPrepRankingIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureSubscriberPrepRankingService")
    private LTEHandoverFailureSubscriberPrepRankingService lteHandoverFailureSubscriberPrepRankingService;

    @Test
    @Parameters(source = LTEHFASubscriberPrepRankingTestDataProvider.class)
    public void testCallSetupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHandoverFailureSubscriberPrepRankingService);
    }
}
