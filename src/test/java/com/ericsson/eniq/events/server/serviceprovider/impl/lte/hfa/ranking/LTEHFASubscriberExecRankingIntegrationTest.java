/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.ranking;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFASubscriberExecRankingTestDataProvider;
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
public class LTEHFASubscriberExecRankingIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureSubscriberExecRankingService")
    private LTEHandoverFailureSubscriberExecRankingService lteHandoverFailureSubscriberExecRankingService;

    @Test
    @Parameters(source = LTEHFASubscriberExecRankingTestDataProvider.class)
    public void testCallSetupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHandoverFailureSubscriberExecRankingService);
    }
}
