/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.causecodeanalysis;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAECellCCSummaryTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAECellGroupCCSummaryTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

/**
 * @author ejamves
 * @since 2011
 *
 */

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFAECellCCSummaryIntegTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureEcellCauseCodeSummaryService")
    private LTEHandoverFailureEcellCauseCodeSummaryService lteHandoverFailureEcellCauseCodeSummaryService;

    @Test
    @Parameters(source = LTEHFAECellCCSummaryTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellCauseCodeSummaryService);
    }

    @Test
    @Parameters(source = LTEHFAECellGroupCCSummaryTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellCauseCodeSummaryService);
    }
}
