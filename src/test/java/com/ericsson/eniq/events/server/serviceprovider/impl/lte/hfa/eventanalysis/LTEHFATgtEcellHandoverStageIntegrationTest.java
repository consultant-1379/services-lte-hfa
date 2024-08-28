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

import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFAEcellHandoverStageTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.LTEHFATgtEcellHandoverStageTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAECellGroupEventHandoverStageTestDataProvider;
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
public class LTEHFATgtEcellHandoverStageIntegrationTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureTgtEcellHandoverStageService")
    private LTEHandoverFailureTgtECellHandoverStageService lteHandoverFailureTgtECellHandoverStageService;

    @Test
    @Parameters(source = LTEHFATgtEcellHandoverStageTestDataProvider.class)
    public void testCallSetupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHandoverFailureTgtECellHandoverStageService);
    }

}
