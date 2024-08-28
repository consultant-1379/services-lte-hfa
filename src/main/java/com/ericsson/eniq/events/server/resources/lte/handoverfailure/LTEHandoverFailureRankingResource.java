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

@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class LTEHandoverFailureRankingResource extends AbstractResource {

    private static final String LTE_HANDOVER_FAILURE_SOURCECELL_EXEC_RANKING_SERVICE = "LTEHandoverFailureSourceCellExecRankingService";

    private static final String LTE_HANDOVER_FAILURE_SOURCECELL_PREP_RANKING_SERVICE = "LTEHandoverFailureSourceCellPrepRankingService";

    private static final String LTE_HANDOVER_FAILURE_TARGETCELL_EXEC_RANKING_SERVICE = "LTEHandoverFailureTargetCellExecRankingService";

    private static final String LTE_HANDOVER_FAILURE_TARGETCELL_PREP_RANKING_SERVICE = "LTEHandoverFailureTargetCellPrepRankingService";

    private static final String LTE_HANDOVER_FAILURE_CC_EXEC_RANKING_SERVICE = "LTEHFACCExecRankingService";

    private static final String LTE_HANDOVER_FAILURE_CC_PREP_RANKING_SERVICE = "LTEHFACCPrepRankingService";

    private static final String LTE_HFA_TAC_EXEC_RANKING_SERVICE = "LTEHFATerminalExecRankingService";

    private static final String LTE_HFA_TAC_PREP_RANKING_SERVICE = "LTEHFATerminalPrepRankingService";

    @EJB(beanName = LTE_HANDOVER_FAILURE_SOURCECELL_EXEC_RANKING_SERVICE)
    private Service lteHandoverFailureSourceCellExecRankingService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_SOURCECELL_PREP_RANKING_SERVICE)
    private Service lteHandoverFailureSourceCellPrepRankingService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_TARGETCELL_EXEC_RANKING_SERVICE)
    private Service lteHandoverFailureTargetCellExecRankingService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_TARGETCELL_PREP_RANKING_SERVICE)
    private Service lteHandoverFailureTargetCellPrepRankingService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_CC_PREP_RANKING_SERVICE)
    private Service lteHandoverFailureCCPrepRankingService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_CC_EXEC_RANKING_SERVICE)
    private Service lteHandoverFailureCCExecRankingService;

    @EJB(beanName = LTE_HFA_TAC_EXEC_RANKING_SERVICE)
    private Service lteHFATerminalExecRankingService;

    @EJB(beanName = LTE_HFA_TAC_PREP_RANKING_SERVICE)
    private Service lteHFATerminalPrepRankingService;

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.resources.AbstractResource#getService()
     */
    @Override
    protected Service getService() {
        throw new UnsupportedOperationException();
    }

    @Path(LTE_HFA_SOURCE_CELL_EXEC)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHandoverFailureSourceCellExecRankingService() {
        return lteHandoverFailureSourceCellExecRankingService.getData(mapResourceLayerParameters());
    }

    @Path(LTE_HFA_SOURCE_CELL_EXEC)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHandoverFailureSourceCellExecRankingServiceAsCSV() {
        return lteHandoverFailureSourceCellExecRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(LTE_HFA_SOURCE_CELL_PREP)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHandoverFailureSourceCellPrepRankingService() {
        return lteHandoverFailureSourceCellPrepRankingService.getData(mapResourceLayerParameters());
    }

    @Path(LTE_HFA_SOURCE_CELL_PREP)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHandoverFailureSourceCellPrepRankingServiceAsCSV() {
        return lteHandoverFailureSourceCellPrepRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(LTE_HFA_TARGET_CELL_EXEC)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHandoverFailureTargetCellExecRankingService() {
        return lteHandoverFailureTargetCellExecRankingService.getData(mapResourceLayerParameters());
    }

    @Path(LTE_HFA_TARGET_CELL_EXEC)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHandoverFailureTargetCellExecRankingServiceAsCSV() {
        return lteHandoverFailureTargetCellExecRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(LTE_HFA_TARGET_CELL_PREP)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHandoverFailureTargetCellPrepRankingService() {
        return lteHandoverFailureTargetCellPrepRankingService.getData(mapResourceLayerParameters());
    }

    @Path(LTE_HFA_TARGET_CELL_PREP)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHandoverFailureTargetCellPrepRankingServiceAsCSV() {
        return lteHandoverFailureTargetCellPrepRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(LTE_HFA_CAUSE_CODE_EXEC)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHandoverFailureCCExecRankingService() {
        return lteHandoverFailureCCExecRankingService.getData(mapResourceLayerParameters());
    }

    @Path(LTE_HFA_CAUSE_CODE_EXEC)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHandoverFailureCCRankingServiceAsCSV() {
        return lteHandoverFailureCCExecRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(LTE_HFA_CAUSE_CODE_PREP)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHandoverFailureCCPrepRankingService() {
        return lteHandoverFailureCCPrepRankingService.getData(mapResourceLayerParameters());
    }

    @Path(LTE_HFA_CAUSE_CODE_PREP)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHandoverFailureCCPrepRankingServiceAsCSV() {
        return lteHandoverFailureCCPrepRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(TYPE_TAC_EXEC)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHFAExecRankingTAC() {
        return lteHFATerminalExecRankingService.getData(mapResourceLayerParameters());
    }

    @Path(TYPE_TAC_EXEC)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHFAExecRankingTACAsCSV() {
        return lteHFATerminalExecRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(TYPE_TAC_PREP)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLTEHFAPrepRankingTAC() {
        return lteHFATerminalPrepRankingService.getData(mapResourceLayerParameters());
    }

    @Path(TYPE_TAC_PREP)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLTEHFAPrepRankingTACAsCSV() {
        return lteHFATerminalPrepRankingService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

}
