/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventvolume;

import javax.annotation.Resource;
import javax.ws.rs.core.MultivaluedMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEcellEventVolTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEcellGrpEventVolTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;


@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFAEcellEventVolIntegTest extends ServiceBaseTest {
    @Resource(name = "lteHFAEcellEventVolumeService")
    private LTEHFAEcellEventVolumeService lteHFAEcellEventVolumeService;

    @Test
    @Parameters(source = LTEHFAEcellEventVolTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEcellEventVolumeService);
    }

    @Test
    @Parameters(source = LTEHFAEcellGrpEventVolTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) {
       runQuery(requestParameters, lteHFAEcellEventVolumeService);
    }
}
