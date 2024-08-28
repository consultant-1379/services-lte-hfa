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
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.LTEHFAEnodeBGroupQCIDetailedEventAnalysisTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.LTEHFAEnodeBQCIDetailedEventAnalysisTestDataProvider;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis.LTEHFAEnodeBQCIDetailedEventAnalysisService;

/**
 * @author echimma
 * @since 2012
 *
 */
@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class LTEHFAEnodeBQCIDetailedEventAnalysisIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHFAEnodeBQCIDetailedEventAnalysisService")
    private LTEHFAEnodeBQCIDetailedEventAnalysisService LTEHFAEnodeBQCIDetailedEventAnalysisService;

    @Test
    @Parameters(source = LTEHFAEnodeBQCIDetailedEventAnalysisTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, LTEHFAEnodeBQCIDetailedEventAnalysisService);
    }
 
    @Test
    @Parameters(source = LTEHFAEnodeBGroupQCIDetailedEventAnalysisTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, LTEHFAEnodeBQCIDetailedEventAnalysisService);
    }
}
