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
 * @author evijred
  *
 */

@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class LTEHandoverFailureERABDetailedEventAnalysisResource extends AbstractResource {

    private static final String LTE_HANDOVER_FAILURE_SUBSCRIBER_ERAB_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHandoverFailureSubscriberERABDEAService";

    @EJB(beanName = LTE_HANDOVER_FAILURE_SUBSCRIBER_ERAB_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureSubscriberERABDetailedEventAnalysisService;

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
    public String getLteHandoverFailureSubscriberERABDetailedEventAnalysisService() {
        return lteHandoverFailureSubscriberERABDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(IMSI)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSubscriberERABDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureSubscriberERABDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(),
                response);
    }

    @Path(URI_PATH_GROUP + PATH_SEPARATOR + IMSI)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSubscriberGroupERABDetailedEventAnalysisService() {
        return lteHandoverFailureSubscriberERABDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(URI_PATH_GROUP + PATH_SEPARATOR + IMSI)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSubscriberGroupERABDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureSubscriberERABDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(),
                response);
    }
}
