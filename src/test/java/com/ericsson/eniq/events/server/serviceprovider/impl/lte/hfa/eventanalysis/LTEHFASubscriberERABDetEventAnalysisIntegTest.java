/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFASubscriberERABDetailedEventAnalysisTestDataProvider;
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
 */

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class LTEHFASubscriberERABDetEventAnalysisIntegTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureSubscriberERABDetailedEventAnalysisService")
    private LTEHandoverFailureSubscriberERABDEAService lteHandoverFailureSubscriberERABDetailedEventAnalysisService;

    @Test
    @Parameters(source = LTEHFASubscriberERABDetailedEventAnalysisTestDataProvider.class)
    public void testCallSetupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureSubscriberERABDetailedEventAnalysisService);
    }

}