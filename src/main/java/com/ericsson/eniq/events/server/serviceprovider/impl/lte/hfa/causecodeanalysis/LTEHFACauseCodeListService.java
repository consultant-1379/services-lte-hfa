/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.causecodeanalysis;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.ericsson.eniq.events.server.serviceprovider.Service;
import com.ericsson.eniq.events.server.serviceprovider.impl.GenericSimpleService;

/**
 * @author emohasu
 * @since 2012
 */
@Stateless
@Local(Service.class)
public class LTEHFACauseCodeListService extends GenericSimpleService {

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericSimpleServiceInterface#getTemplatePath()
     */
    @Override
    public String getTemplatePath() {
        return LTE_HANDOVER_FAILURE_CAUSE_CODE_TABLE_CC;
    }
}
