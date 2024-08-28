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
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.EcellGroupQCIDetailedEventAnalysisTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.EcellQCIDetailedEventAnalysisTestDataProvider;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis.EcellQCIDetailedEventAnalysisService;


@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class EcellQCIDetailedEventAnalysisIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHFAEcellQCIDetailedEventAnalysisService")
    private EcellQCIDetailedEventAnalysisService lteHFAEcellQCIDetailedEventAnalysisService;

    @Test
    @Parameters(source = EcellQCIDetailedEventAnalysisTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEcellQCIDetailedEventAnalysisService);
    }
 
    @Test
    @Parameters(source = EcellGroupQCIDetailedEventAnalysisTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEcellQCIDetailedEventAnalysisService);
    }
}
