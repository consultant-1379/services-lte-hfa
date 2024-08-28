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
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.EcellGroupQCICategorySummaryTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.EcellQCICategorySummaryTestDataProvider;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis.EcellQCICategorySummaryService;


@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class EcellQCICategorySummaryIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHFAEcellQCICategorySummaryService")
    private EcellQCICategorySummaryService lteHFAEcellQCICategorySummaryService;

    @Test
    @Parameters(source = EcellQCICategorySummaryTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEcellQCICategorySummaryService);
    }

    @Test
    @Parameters(source = EcellGroupQCICategorySummaryTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEcellQCICategorySummaryService);
    }
}
