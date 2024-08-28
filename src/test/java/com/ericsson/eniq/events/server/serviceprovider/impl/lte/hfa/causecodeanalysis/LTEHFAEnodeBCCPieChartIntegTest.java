/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.causecodeanalysis;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBCauseCodePieChartTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBGroupCauseCodePieChartTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

/**
 * @author echchik
 * @since 2011
 *
 */

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFAEnodeBCCPieChartIntegTest extends ServiceBaseTest {

    @Resource(name = "lteHFAEnodeBCauseCodePieChartService")
    private LTEHandoverFailureEnodeBCauseCodePieChartService lteHFAEnodeBCauseCodePieChartService;

    @Test
    @Parameters(source = LTEHFAEnodeBCauseCodePieChartTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEnodeBCauseCodePieChartService);
    }

    @Test
    @Parameters(source = LTEHFAEnodeBGroupCauseCodePieChartTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEnodeBCauseCodePieChartService);
    }
}
