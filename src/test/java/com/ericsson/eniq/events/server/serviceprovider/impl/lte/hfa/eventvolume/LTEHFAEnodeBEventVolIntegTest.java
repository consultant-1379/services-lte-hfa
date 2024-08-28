/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
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

import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBEventVolTestDataProvider;
import com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure.LTEHFAEnodeBGrpEventVolTestDataProvider;
import com.ericsson.eniq.events.server.resources.automation.ServiceBaseTest;

/**
 * @author evijred
 * @since 2011
 *
 */

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = { "classpath:lte-hfa-service-context.xml" })
public class LTEHFAEnodeBEventVolIntegTest extends ServiceBaseTest {

    @Resource(name = "lteHFAEnodeBEventVolumeService")
    private LTEHFAEnodeBEventVolumeService lteHFAEnodeBEventVolumeService;

    @Test
    @Parameters(source = LTEHFAEnodeBEventVolTestDataProvider.class)
    public void testGetData(final MultivaluedMap<String, String> requestParameters) {
        runQuery(requestParameters, lteHFAEnodeBEventVolumeService);
    }
    
    @Test
    @Parameters(source = LTEHFAEnodeBGrpEventVolTestDataProvider.class)
    public void testGroupGetData(final MultivaluedMap<String, String> requestParameters) throws Exception {
        runQuery(requestParameters, lteHFAEnodeBEventVolumeService);
    }
}
