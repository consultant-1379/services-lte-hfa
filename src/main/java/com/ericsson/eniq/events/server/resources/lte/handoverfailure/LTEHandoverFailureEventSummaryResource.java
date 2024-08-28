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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.ericsson.eniq.events.server.common.MediaTypeConstants;
import com.ericsson.eniq.events.server.resources.AbstractResource;
import com.ericsson.eniq.events.server.serviceprovider.Service;

/**
 * @author ejamves
 * @since 2011
 *
 */
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class LTEHandoverFailureEventSummaryResource extends AbstractResource {

    private static final String LTE_HANDOVER_FAILURE_SUBSCRIBER_EVENT_SUMMARY_SERVICE = "LTEHandoverFailureSubscriberEventSummaryService";

    private static final String LTE_HANDOVER_FAILURE_EVENT_HANDOVERSTAGE_SERVICE = "LTEHandoverFailureEventHandoverStageService";

    private static final String LTE_HANDOVER_FAILURE_ENODEB_EVENT_HANDOVERSTAGE_SERVICE = "LTEHandoverFailureEnodeBEventHOStageService";

    private static final String LTE_HANDOVER_FAILURE_ENODEB_EVENT_SUMMARY_SERVICE = "LTEHandoverFailureENodeBEventSummaryService";

    private static final String LTE_HANDOVER_FAILURE_ECELL_EVENT_SUMMARY_SERVICE = "LTEHandoverFailureECellEventSummaryService";

    private static final String LTE_HANDOVER_FAILURE_TARGET_ECELL_EVENT_SUMMARY_SERVICE = "LTEHandoverFailureTgtECellEventSummaryService";

    private static final String LTE_HANDOVER_FAILURE_ECELL_HANDOVER_STAGE_SERVICE = "LTEHandoverFailureECellHandoverStageService";

    private static final String LTE_HANDOVER_FAILURE_TARGET_ECELL_HANDOVER_STAGE_SERVICE = "LTEHandoverFailureTgtECellHandoverStageService";

    private static final String LTE_HANDOVER_FAILURE_TAC_HANDOVER_STAGE_SERVICE = "TerminalHandoverStageService";

    private static final String LTE_HANDOVER_FAILURE_TERMINAL_EVENT_SUMMARY_SERVICE = "TerminalEventSummaryService";

    @EJB(beanName = LTE_HANDOVER_FAILURE_SUBSCRIBER_EVENT_SUMMARY_SERVICE)
    private Service lteHandoverFailureSubscriberEventSummaryService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_EVENT_HANDOVERSTAGE_SERVICE)
    private Service lteHandoverFailureEventHandoverStageService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ENODEB_EVENT_HANDOVERSTAGE_SERVICE)
    private Service lteHandoverFailureEnodeBEventHandoverStageService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ENODEB_EVENT_SUMMARY_SERVICE)
    private Service lteHandoverFailureEnodeBEventSummaryService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ECELL_EVENT_SUMMARY_SERVICE)
    private Service lteHandoverFailureECellEventSummaryService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_TARGET_ECELL_EVENT_SUMMARY_SERVICE)
    private Service lteHandoverFailureTgtECellEventSummaryService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ECELL_HANDOVER_STAGE_SERVICE)
    private Service lteHandoverFailureECellHandoverStageService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_TARGET_ECELL_HANDOVER_STAGE_SERVICE)
    private Service lteHandoverFailureTgtECellHandoverStageService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_TAC_HANDOVER_STAGE_SERVICE)
    private Service lteHandoverFailureTACEventHandoverStageService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_TERMINAL_EVENT_SUMMARY_SERVICE)
    private Service lteHfaTerminalEventSummaryService;

    @Override
    protected Service getService() {
        throw new UnsupportedOperationException();
    }

    @Path(CATEGORY_ID)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventSummarySubscriber() {
        return lteHandoverFailureSubscriberEventSummaryService.getData(mapResourceLayerParameters());
    }

    @Path(CATEGORY_ID)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventSummarySubscriberAsCSV() {
        return lteHandoverFailureSubscriberEventSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(IMSI)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventSummaryHandoverStageSubscriber() {
        return lteHandoverFailureEventHandoverStageService.getData(mapResourceLayerParameters());
    }

    @Path(IMSI)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventSummaryHandoverStageSubscriberAsCSV() {
        return lteHandoverFailureEventHandoverStageService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(NODE)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHFAEventSummaryEnodeBAndECell() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBEventHandoverStageService.getData(reqParams);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureECellHandoverStageService.getData(reqParams);
        } else if (TYPE_TARGET_CELL.equals(type)) {
            return lteHandoverFailureTgtECellHandoverStageService.getData(reqParams);
        }
        return lteHandoverFailureECellHandoverStageService.getData(reqParams);
    }

    @Path(NODE)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHFAEventSummaryEnodeBAndECellAsCSV() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBEventHandoverStageService.getDataAsCSV(mapResourceLayerParameters(),
                    response);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureECellHandoverStageService.getDataAsCSV(mapResourceLayerParameters(), response);
        } else if (TYPE_TARGET_CELL.equals(type)) {
            return lteHandoverFailureTgtECellHandoverStageService.getDataAsCSV(mapResourceLayerParameters(), response);
        }
        return lteHandoverFailureECellHandoverStageService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(CATEGORY_ID_ENODEB)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventEnodeBAndECell() {
        return lteHandoverFailureEnodeBEventSummaryService.getData(mapResourceLayerParameters());
    }

    @Path(CATEGORY_ID_ENODEB)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventEnodeBAndECellAsCSV() {
        return lteHandoverFailureEnodeBEventSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(EVENT_ID)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventTypeECell() {
        return lteHandoverFailureECellEventSummaryService.getData(mapResourceLayerParameters());
    }

    @Path(EVENT_ID)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventTypeECellAsCSV() {
        return lteHandoverFailureECellEventSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(TARGET_EVENT_ID)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventTypeTgtECell() {
        return lteHandoverFailureTgtECellEventSummaryService.getData(mapResourceLayerParameters());
    }

    @Path(TARGET_EVENT_ID)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventTypeTgtECellAsCSV() {
        return lteHandoverFailureTgtECellEventSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(ENODEB)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventSummaryEnodeB() {
        return lteHandoverFailureEnodeBEventHandoverStageService.getData(mapResourceLayerParameters());
    }

    @Path(ENODEB)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventSummaryEnodeBAsCSV() {
        return lteHandoverFailureEnodeBEventHandoverStageService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(ACCESS_AREA)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventSummaryECellAsCSV() {
        return lteHandoverFailureECellHandoverStageService.getData(mapResourceLayerParameters());
    }

    @Path(ACCESS_AREA)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventSummaryECellAsCSV1() {
        return lteHandoverFailureECellHandoverStageService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(TARGET_ACCESS_AREA)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventSummaryTgtECellAsCSV() {
        return lteHandoverFailureTgtECellHandoverStageService.getData(mapResourceLayerParameters());
    }

    @Path(TARGET_ACCESS_AREA)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventSummaryTgtECellAsCSV1() {
        return lteHandoverFailureTgtECellHandoverStageService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(TAC)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureSetUpEventSummaryTAC() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        if (reqParams.containsKey(NODE_PARAM) || reqParams.containsKey(GROUP_NAME_PARAM)
                || reqParams.containsKey(TAC_PARAM) || reqParams.containsKey(TAC_PARAM_UPPER_CASE)) {
            return lteHandoverFailureTACEventHandoverStageService.getData(mapResourceLayerParameters());
        }
        return lteHfaTerminalEventSummaryService.getData(mapResourceLayerParameters());
    }

    @Path(TAC)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureSetUpEventSummaryTACAsCSV() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        if (reqParams.containsKey(NODE_PARAM) || reqParams.containsKey(GROUP_NAME_PARAM)
                || reqParams.containsKey(TAC_PARAM) || reqParams.containsKey(TAC_PARAM_UPPER_CASE)) {
            return lteHandoverFailureTACEventHandoverStageService.getDataAsCSV(mapResourceLayerParameters(), response);
        }
        return lteHfaTerminalEventSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(CATEGORISED_TERMINAL)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureEventSummaryTerminal() {
        return lteHfaTerminalEventSummaryService.getData(mapResourceLayerParameters());
    }

    @Path(CATEGORISED_TERMINAL)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureEventSummaryTerminalAsCSV() {
        return lteHfaTerminalEventSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

}
