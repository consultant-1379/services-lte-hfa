/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.resources.lte.handoverfailure;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ericsson.eniq.events.server.common.MediaTypeConstants;
import com.ericsson.eniq.events.server.resources.AbstractResource;
import com.ericsson.eniq.events.server.serviceprovider.Service;

/**
 * @author ejamves
  *
 */

@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class LTEHandoverFailureSubscriberPrepRankingResource extends AbstractResource {

    private static final String LTE_HANDOVER_FAILURE_SUBSCRIBER_PREP_RANKING_SERVICE = "LTEHandoverFailureSubscriberPrepRankingService";

    @EJB(beanName = LTE_HANDOVER_FAILURE_SUBSCRIBER_PREP_RANKING_SERVICE)
    private Service lteHandoverFailureSubscriberPrepRankingService;

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.resources.AbstractResource#getService()
     */
    @Override
    protected Service getService() {
        throw new UnsupportedOperationException();
    }

    @Path(IMSI)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSubscriberPrepRankingService() {
        return lteHandoverFailureSubscriberPrepRankingService.getData(mapResourceLayerParameters());
    }

    @Path(IMSI)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSubscriberPrepRankingServiceAsCSV() {
        return lteHandoverFailureSubscriberPrepRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }
}
