/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;


import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.TerminalEventSummaryTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.TerminalGroupEventSummaryTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;


/**
 * @author evaraks
 * @since 2011
 */

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:lte-hfa-service-context.xml"})
public class LTEHFATerminalEventSummaryIntegrationTest extends ServiceBaseTest {

    @Resource(name = "terminalEventSummaryService")
    private TerminalEventSummaryService terminalEventSummaryService;

  /* @Test
    @Parameters(source = TerminalEventSummaryRankingDrillTestDataProvider.class)
    public void testGetDataRankingDrill(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, terminalEventSummaryService);
    }*/

    @Test
    @Parameters(source = TerminalEventSummaryTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, terminalEventSummaryService);
    }

    @Test
    @Parameters(source = TerminalGroupEventSummaryTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, terminalEventSummaryService);
    }
}
