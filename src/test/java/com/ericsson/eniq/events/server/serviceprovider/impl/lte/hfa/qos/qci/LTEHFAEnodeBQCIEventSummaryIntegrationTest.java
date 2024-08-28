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
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.LTEHFAEnodeBGroupQCIEventSummaryTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos.LTEHFAEnodeBQCIEventSummaryTestDataProvider;
import com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis.LTEHFAEnodeBQCIEventSummaryService;

/**
 * @author echimma
 * @since 2012
 *
 */
@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class LTEHFAEnodeBQCIEventSummaryIntegrationTest extends ServiceBaseTest {
    @Resource(name = "lteHFAEnodeBQCIEventSummaryService")
    private LTEHFAEnodeBQCIEventSummaryService lteHFAEnodeBQCIEventSummaryService;

    @Test
    @Parameters(source = LTEHFAEnodeBQCIEventSummaryTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEnodeBQCIEventSummaryService);
    }

    @Test
    @Parameters(source = LTEHFAEnodeBGroupQCIEventSummaryTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEnodeBQCIEventSummaryService);
    }
}
