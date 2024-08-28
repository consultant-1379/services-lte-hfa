/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFASubscriberEventSummaryTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFASubscriberGroupEventSummaryTestDataProvider;
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
public class LTEHFASubscriberEventSummaryIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureSubscriberEventSummaryService")
    private LTEHandoverFailureSubscriberEventSummaryService lteHandoverFailureSubscriberEventSummaryService;

    @Test
    @Parameters(source = LTEHFASubscriberEventSummaryTestDataProvider.class)
    public void testCallSetupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHandoverFailureSubscriberEventSummaryService);
    }

    @Test
    @Parameters(source = LTEHFASubscriberGroupEventSummaryTestDataProvider.class)
    public void testCallSetupGroupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHandoverFailureSubscriberEventSummaryService);
    }
}
