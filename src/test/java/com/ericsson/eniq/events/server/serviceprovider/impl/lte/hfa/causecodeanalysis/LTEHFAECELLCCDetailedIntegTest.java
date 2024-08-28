/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.causecodeanalysis;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAEcellCCPieDrilldownTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAEcellGroupCCPieDrilldownTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAECELLCauseCodeDetailedTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAECELLGroupCauseCodeDetailedTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFAECELLCCDetailedIntegTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService")
    private LTEHFAEcellCCDetEventAnalysisService lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService;

    @Test
    @Parameters(source = LTEHFAECELLCauseCodeDetailedTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService);
    }

    @Test
    @Parameters(source = LTEHFAECELLGroupCauseCodeDetailedTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService);
    }

    @Test
    @Parameters(source = LTEHFAEcellCCPieDrilldownTestDataProvider.class)
    public void testGetDataPieDrilldown(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService);
    }

    @Test
    @Parameters(source = LTEHFAEcellGroupCCPieDrilldownTestDataProvider.class)
    public void testGroupGetDataPieDrilldown(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService);
    }
}
