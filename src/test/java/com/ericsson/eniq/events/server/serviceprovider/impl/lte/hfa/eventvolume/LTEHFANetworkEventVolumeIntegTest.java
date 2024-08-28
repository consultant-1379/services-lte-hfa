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

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFANetworkEventVolumeTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

/**
 * @author evijred
 * @since 2012
 *
 */
@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFANetworkEventVolumeIntegTest extends ServiceBaseTest {
    @Resource(name = "lteHFANetworkEventVolumeService")
    private LTEHFANetworkEventVolumeService lteHFANetworkEventVolumeService;

    @Test
    @Parameters(source = LTEHFANetworkEventVolumeTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFANetworkEventVolumeService);
    }
}
