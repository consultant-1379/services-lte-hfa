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

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBCCListDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBGroupCCListTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

/**
 * @author evijred
 * @since 2011
 *
 */

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFAEnodeBCCListIntegTest extends ServiceBaseTest {

    @Resource(name = "lteHandoverFailureEnodeBCauseCodeListService")
    private LTEHandoverFailureEnodeBCauseCodeListService lteHandoverFailureEnodeBCauseCodeListService;

    @Test
    @Parameters(source = LTEHFAEnodeBCCListDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBCauseCodeListService);
    }

    @Test
    @Parameters(source = LTEHFAEnodeBGroupCCListTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHandoverFailureEnodeBCauseCodeListService);
    }
}
