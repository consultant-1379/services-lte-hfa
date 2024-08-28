/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qci;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.EcellGroupQCIEventSummaryTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.EcellQCIEventSummaryTestDataProvider;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis.EcellQCIEventSummaryService;


@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class EcellQCIEventSummaryIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHFAEcellQCIEventSummaryService")
    private EcellQCIEventSummaryService lteHFAEcellQCIEventSummaryService;

    @Test
    @Parameters(source = EcellQCIEventSummaryTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEcellQCIEventSummaryService);
    }

    @Test
    @Parameters(source = EcellGroupQCIEventSummaryTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEcellQCIEventSummaryService);
    }
}
