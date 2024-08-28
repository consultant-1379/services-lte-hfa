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
public class LTEHandoverFailureSubscriberExecRankingResource extends AbstractResource {

    private static final String LTE_HANDOVER_FAILURE_SUBSCRIBER_EXEC_RANKING_SERVICE = "LTEHandoverFailureSubscriberExecRankingService";

    private static final String LTE_HANDOVER_FAILURE_ENODEB_EXEC_RANKING_SERVICE = "LTEHandoverFailureEnodeBExecRankingService";

    private static final String LTE_HANDOVER_FAILURE_ENODEB_PREP_RANKING_SERVICE = "LTEHandoverFailureEnodeBPrepRankingService";

    @EJB(beanName = LTE_HANDOVER_FAILURE_SUBSCRIBER_EXEC_RANKING_SERVICE)
    private Service lteHandoverFailureSubscriberExecRankingService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ENODEB_EXEC_RANKING_SERVICE)
    private Service lteHandoverFailureEnodeBExecRankingService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ENODEB_PREP_RANKING_SERVICE)
    private Service lteHandoverFailureEnodeBPrepRankingService;

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
    public String getLteHandoverFailureSubscriberExecRankingService() {
        return lteHandoverFailureSubscriberExecRankingService.getData(mapResourceLayerParameters());
    }

    @Path(IMSI)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSubscriberExecRankingServiceAsCSV() {
        return lteHandoverFailureSubscriberExecRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(ENODEB_FOR_EXEC_FAILURE)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureENodeBExecRankingService() {
        return lteHandoverFailureEnodeBExecRankingService.getData(mapResourceLayerParameters());
    }

    @Path(ENODEB_FOR_EXEC_FAILURE)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureENodeBExecRankingServiceAsCSV() {
        return lteHandoverFailureEnodeBExecRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(ENODEB_FOR_PREP_FAILURE)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureENodeBPrepRankingService() {
        return lteHandoverFailureEnodeBPrepRankingService.getData(mapResourceLayerParameters());
    }

    @Path(ENODEB_FOR_PREP_FAILURE)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureENodeBPrepRankingServiceAsCSV() {
        return lteHandoverFailureEnodeBPrepRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

}
