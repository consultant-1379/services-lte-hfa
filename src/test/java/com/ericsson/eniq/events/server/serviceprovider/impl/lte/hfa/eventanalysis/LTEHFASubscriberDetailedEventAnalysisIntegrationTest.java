/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAGroupDetailedEventAnalysisTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFASubscriberDetailedEventAnalysisTestDataProvider;
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
public class LTEHFASubscriberDetailedEventAnalysisIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureSubscriberDetailedEventAnalysisService")
    private LTEHandoverFailureSubscriberDetailedEAService lteHandoverFailureSubscriberDetailedEventAnalysisService;

    @Test
    @Parameters(source = LTEHFASubscriberDetailedEventAnalysisTestDataProvider.class)
    public void testCallSetupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureSubscriberDetailedEventAnalysisService);
    }

    @Test
    @Parameters(source = LTEHFAGroupDetailedEventAnalysisTestDataProvider.class)
    public void testCallSetupGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureSubscriberDetailedEventAnalysisService);
    }
}
