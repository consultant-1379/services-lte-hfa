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

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAEventHandoverStageTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAGroupEventHandoverStageTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFAEventHandoverStageIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureEventHandoverStageService")
    private LTEHandoverFailureEventHandoverStageService lteHandoverFailureEventHandoverStageService;

    @Test
    @Parameters(source = LTEHFAEventHandoverStageTestDataProvider.class)
    public void testCallSetupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHandoverFailureEventHandoverStageService);
    }

    @Test
    @Parameters(source = LTEHFAGroupEventHandoverStageTestDataProvider.class)
    public void testCallSetupGroupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHandoverFailureEventHandoverStageService);
    }
}
