/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAECellDetailedEventAnalysisTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAECellGroupDetailedEventAnalysisTestDataProvider;
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
public class LTEHFAEcellDetailedEventAnalysisIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureECellDetailedEventAnalysisService")
    private LTEHandoverFailureECellDetailedEAService lteHandoverFailureECellDetailedEventAnalysisService;

    @Resource(name = "lteHandoverFailureECellGroupDetailedEventAnalysisService")
    private LTEHandoverFailureECellGroupDetailedEAService lteHandoverFailureECellGroupDetailedEventAnalysisService;

    @Test
    @Parameters(source = LTEHFAECellDetailedEventAnalysisTestDataProvider.class)
    public void testHandoverFailureECellGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureECellDetailedEventAnalysisService);
    }

    @Test
    @Parameters(source = LTEHFAECellGroupDetailedEventAnalysisTestDataProvider.class)
    public void testHandoverFailureECellGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureECellGroupDetailedEventAnalysisService);
    }
}
