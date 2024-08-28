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

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAECellCCListGroupTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAECellCCListTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

/**
 * @author ejamves
 * @since 2011
 *
 */

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFAECellCCListIntegTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureEcellCauseCodeListService")
    private LTEHandoverFailureEcellCauseCodeListService lteHandoverFailureEcellCauseCodeListService;

    @Test
    @Parameters(source = LTEHFAECellCCListTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellCauseCodeListService);
    }

    @Test
    @Parameters(source = LTEHFAECellCCListGroupTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEcellCauseCodeListService);
    }
}
