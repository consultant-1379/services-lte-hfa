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
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAGroupDetailedEventAnalysisTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.TerminalDetailedEventAnalysisTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFATerminalDetailedEventAnalysisIntegrationTest extends ServiceBaseTest {
    @Resource(name = "terminalDetailedEAService")
    private TerminalDetailedEAService terminalDetailedEAService;

    @Test
    @Parameters(source = TerminalDetailedEventAnalysisTestDataProvider.class)
    public void testHandoverGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, terminalDetailedEAService);
    }

    @Test
    @Parameters(source = TerminalDetailedEventAnalysisTestDataProvider.class)
    public void testHandoverGetCSVData(final MultivaluedMap<String, String> requestParameters) {
        runQueryForCSV(requestParameters, terminalDetailedEAService);
    }

    @Test
    @Parameters(source = LTEHFAGroupDetailedEventAnalysisTestDataProvider.class)
    public void testHandoverGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, terminalDetailedEAService);
    }
}
