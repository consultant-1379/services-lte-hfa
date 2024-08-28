/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAEnodeBEventHandoverStageTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAEnodeBGroupEventHandoverStageTestDataProvider;
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
public class LTEHFAEnodeBEventHandoverStageIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureEnodeBEventHandoverStageService")
    private LTEHandoverFailureEnodeBEventHOStageService lteHandoverFailureEnodeBEventHandoverStageService;

    /*@Test
    @Parameters(source = LTEHandoverFailureEnodeBEventHandoverStageRankingDrillTestDataProvider.class)
    public void testGetDataRankingDrill(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBEventHandoverStageService);
    }*/

    @Test
    @Parameters(source = LTEHFAEnodeBEventHandoverStageTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBEventHandoverStageService);
    }

    @Test
    @Parameters(source = LTEHFAEnodeBGroupEventHandoverStageTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBEventHandoverStageService);
    }
}
