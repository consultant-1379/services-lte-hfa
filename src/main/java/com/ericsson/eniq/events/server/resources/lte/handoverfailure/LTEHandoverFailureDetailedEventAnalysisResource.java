/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
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
 * 
 * @since 2014
 *
 */

@Stateless
@LocalBean
public class LTEHandoverFailureDetailedEventAnalysisResource extends AbstractResource {

    private static final String LTE_HANDOVER_FAILURE_SUBSCRIBER_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHandoverFailureSubscriberDetailedEAService";

    private static final String LTE_HANDOVER_FAILURE_ENODEB_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHandoverFailureENodeBDetailedEAService";

    private static final String LTE_HANDOVER_FAILURE_ECELL_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHandoverFailureECellDetailedEAService";

    private static final String LTE_HANDOVER_FAILURE_TARGET_ECELL_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHandoverFailureTgtECellDetailedEAService";

    private static final String LTE_HANDOVER_FAILURE_ECELL_GROUP_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHandoverFailureECellGroupDetailedEAService";

    private static final String LTE_HANDOVER_FAILURE_ECELL_CAUSE_CODE_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHFAEcellCCDetEventAnalysisService";

    private static final String LTE_HANDOVER_FAILURE_ENODEB_CAUSE_CODE_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHFAEnodeBCCDetEventAnalysisService";

    private static final String LTE_HFA_TERMINAL_DETAILED_EVENT_ANALYSIS_SERVICE = "TerminalDetailedEAService";

    @EJB(beanName = LTE_HANDOVER_FAILURE_SUBSCRIBER_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureSubscriberDetailedEventAnalysisService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ENODEB_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureENodeBDetailedEventAnalysisService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ECELL_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureEcellDetailedEventAnalysisService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_TARGET_ECELL_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureTgtEcellDetailedEventAnalysisService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ECELL_GROUP_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureEcellGroupDetailedEventAnalysisService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ECELL_CAUSE_CODE_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ENODEB_CAUSE_CODE_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureEnodeBCauseCodeDetailedEventAnalysisService;

    @EJB(beanName = LTE_HFA_TERMINAL_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service terminalDetailedEAService;

    @Override
    protected Service getService() {
        throw new UnsupportedOperationException();
    }

    @Path(IMSI)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSubscriberDetailedEventAnalysisService() {
        return lteHandoverFailureSubscriberDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(IMSI)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSubscriberDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureSubscriberDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(),
                response);
    }

    @Path(ENODEB)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureENodeBDetailedEventAnalysisService() {
        return lteHandoverFailureENodeBDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(ENODEB)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureENodeBDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureENodeBDetailedEventAnalysisService
                .getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(ACCESS_AREA)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureEcellDetailedEventAnalysisService() {
        return lteHandoverFailureEcellDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(ACCESS_AREA)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureEcellDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureEcellDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(TARGET_ACCESS_AREA)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureTgtEcellDetailedEventAnalysisService() {
        return lteHandoverFailureTgtEcellDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(TARGET_ACCESS_AREA)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureTgtEcellDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureTgtEcellDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(),
                response);
    }

    @Path(ACCESS_AREA_GROUP)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureEcellGroupDetailedEventAnalysisService() {
        return lteHandoverFailureEcellGroupDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(ACCESS_AREA_GROUP)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureEcellGroupDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureEcellGroupDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(),
                response);
    }

    @Path(CAUSE_CODE_ACCESS_AREA)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHFAFailureEcellCauseCodeDetailedEventAnalysisService() {
        return lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(CAUSE_CODE_ACCESS_AREA)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHFAFailureEcellCauseCodeDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(),
                response);
    }

    @Path(CAUSE_CODE_ENODEB)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureEnodeBCauseCodeDetailedEventAnalysisService() {
        return lteHandoverFailureEnodeBCauseCodeDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(CAUSE_CODE_ENODEB)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureEnodeBCauseCodeDetailedEventAnalysisServiceAsCSV() {
        return lteHandoverFailureEnodeBCauseCodeDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(),
                response);
    }

    @Path(TERMINAL_SERVICES)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHfaTerminalDetailedEventAnalysisService() {
        return terminalDetailedEAService.getData(mapResourceLayerParameters());
    }

    @Path(TERMINAL_SERVICES)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHfaTerminalDetailedEventAnalysisServiceAsCSV() {
        return terminalDetailedEAService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

}
