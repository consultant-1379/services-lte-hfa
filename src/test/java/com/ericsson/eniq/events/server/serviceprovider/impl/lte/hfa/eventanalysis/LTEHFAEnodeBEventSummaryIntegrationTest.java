/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBEventSummaryRankingDrillTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBEventSummaryTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBGroupEventSummaryTestDataProvider;
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
public class LTEHFAEnodeBEventSummaryIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureEnodeBEventSummaryService")
    private LTEHandoverFailureENodeBEventSummaryService lteHandoverFailureEnodeBEventSummaryService;

    @Test
    @Parameters(source = LTEHFAEnodeBEventSummaryRankingDrillTestDataProvider.class)
    public void testGetDataRankingDrill(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBEventSummaryService);
    }

    @Test
    @Parameters(source = LTEHFAEnodeBEventSummaryTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBEventSummaryService);
    }

    @Test
    @Parameters(source = LTEHFAEnodeBGroupEventSummaryTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBEventSummaryService);
    }
}
