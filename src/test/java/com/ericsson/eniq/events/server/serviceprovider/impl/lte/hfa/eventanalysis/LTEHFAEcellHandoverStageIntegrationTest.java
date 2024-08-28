/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAEcellHandoverStageTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAECellGroupEventHandoverStageTestDataProvider;
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
public class LTEHFAEcellHandoverStageIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureEcellHandoverStageService")
    private LTEHandoverFailureECellHandoverStageService lteHandoverFailureEcellHandoverStageService;

    @Test
    @Parameters(source = LTEHFAEcellHandoverStageTestDataProvider.class)
    public void testCallSetupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHandoverFailureEcellHandoverStageService);
    }

    @Test
    @Parameters(source = LTEHFAECellGroupEventHandoverStageTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellHandoverStageService);
    }

}
