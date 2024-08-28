/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFATerminalGroupHostTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFATerminalHostTestDataProvider;
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
public class LTEHFATerminalHostIntegTest extends ServiceBaseTest {

    @Resource(name = "lteHFATerminalHandoverStageService")
    private TerminalHandoverStageService lteHFATerminalHandoverStageService;

    @Test
    @Parameters(source = LTEHFATerminalHostTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHFATerminalHandoverStageService);
    }

    @Test
    @Parameters(source = LTEHFATerminalGroupHostTestDataProvider.class)
    public void testGetGroupData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHFATerminalHandoverStageService);
    }
}
