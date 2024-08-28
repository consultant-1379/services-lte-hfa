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
public class LTEHandoverFailureCauseCodeAnalysisResource extends AbstractResource {

    private static final String LTE_HFA_ECELL_CAUSE_CODE_PIE_CHART_SERVICE = "LTEHandoverFailureEcellCauseCodePieChartService";

    private static final String LTE_HFA_ECELL_CAUSE_CODE_SUMMARY_SERVICE = "LTEHandoverFailureEcellCauseCodeSummaryService";

    private static final String LTE_HFA_ECELL_CAUSE_CODE_LIST_SERVICE = "LTEHandoverFailureEcellCauseCodeListService";

    private static final String LTE_HFA_ENODEB_CAUSE_CODE_PIE_CHART_SERVICE = "LTEHandoverFailureEnodeBCauseCodePieChartService";

    private static final String LTE_HFA_ENODEB_CAUSE_CODE_LIST_SERVICE = "LTEHandoverFailureEnodeBCauseCodeListService";

    private static final String LTE_HFA_ENODEB_CAUSE_CODE_EVENT_SUMMARY_SERVICE = "LTEHFAEnodeBCCEventSummaryService";

    private static final String LTE_HFA_CAUSE_CODE_LIST_SERVICE = "LTEHFACauseCodeListService";

    private static final String LTE_HANDOVER_FAILURE_ECELL_CAUSE_CODE_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHFAEcellCCDetEventAnalysisService";

    private static final String LTE_HANDOVER_FAILURE_ENODEB_CAUSE_CODE_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHFAEnodeBCCDetEventAnalysisService";

    @EJB(beanName = LTE_HFA_ECELL_CAUSE_CODE_PIE_CHART_SERVICE)
    private Service lteHandoverFailureEcellCauseCodePieChartService;

    @EJB(beanName = LTE_HFA_ECELL_CAUSE_CODE_SUMMARY_SERVICE)
    private Service lteHandoverFailureEcellCauseCodeSummaryService;

    @EJB(beanName = LTE_HFA_ECELL_CAUSE_CODE_LIST_SERVICE)
    private Service lteHandoverFailureEcellCauseCodeListService;

    @EJB(beanName = LTE_HFA_ENODEB_CAUSE_CODE_PIE_CHART_SERVICE)
    private Service lteHandoverFailureEnodeBCauseCodePieChartService;

    @EJB(beanName = LTE_HFA_ENODEB_CAUSE_CODE_LIST_SERVICE)
    private Service lteHandoverFailureEnodeBCauseCodeListService;

    @EJB(beanName = LTE_HFA_ENODEB_CAUSE_CODE_EVENT_SUMMARY_SERVICE)
    private Service lteHandoverFailureEnodeBCauseCodeEventSummaryService;

    @EJB(beanName = LTE_HFA_CAUSE_CODE_LIST_SERVICE)
    private Service lteHandoverFailureCauseCodeListService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ECELL_CAUSE_CODE_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ENODEB_CAUSE_CODE_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHandoverFailureEnodeBCauseCodeDetailedEventAnalysisService;

    @Override
    protected Service getService() {
        throw new UnsupportedOperationException();
    }

    @Path(PIE_CHART_CC_LIST)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureCauseCodeChartList() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBCauseCodeListService.getData(reqParams);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureEcellCauseCodeListService.getData(reqParams);
        }
        return lteHandoverFailureEcellCauseCodeListService.getData(reqParams);
    }

    @Path(PIE_CHART_CC_LIST)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureCauseCodeChartListAsCSV() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBCauseCodeListService.getDataAsCSV(mapResourceLayerParameters(), response);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureEcellCauseCodeListService.getDataAsCSV(mapResourceLayerParameters(), response);
        }
        return lteHandoverFailureEcellCauseCodeListService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(CAUSE_CODE_ANALYSIS)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureCauseCodeAnalysis() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBCauseCodeEventSummaryService.getData(reqParams);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureEcellCauseCodeSummaryService.getData(reqParams);
        }
        return lteHandoverFailureEcellCauseCodeSummaryService.getData(reqParams);
    }

    @Path(CAUSE_CODE_ANALYSIS)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureCauseCodeAnalysisAsCSV() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBCauseCodeEventSummaryService.getDataAsCSV(mapResourceLayerParameters(),
                    response);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureEcellCauseCodeSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
        }
        return lteHandoverFailureEcellCauseCodeSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(PIE_CHART_CAUSE_CODE_ANALYSIS)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureCauseCodePieChart() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBCauseCodePieChartService.getData(reqParams);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureEcellCauseCodePieChartService.getData(reqParams);
        }
        return lteHandoverFailureEcellCauseCodePieChartService.getData(reqParams);
    }

    @Path(PIE_CHART_CAUSE_CODE_ANALYSIS)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureCauseCodePieChartAsCSV() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBCauseCodePieChartService
                    .getDataAsCSV(mapResourceLayerParameters(), response);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureEcellCauseCodePieChartService.getDataAsCSV(mapResourceLayerParameters(), response);
        }
        return lteHandoverFailureEcellCauseCodePieChartService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(LTE_HANDOVER_FAILURE_CAUSE_CODE_TABLE_CC)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureCauseCodeList() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        return lteHandoverFailureCauseCodeListService.getData(reqParams);
    }

    @Path(DETAIL_CAUSE_CODE_GRID)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureCCPieChartDrilldown() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBCauseCodeDetailedEventAnalysisService.getData(reqParams);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService.getData(reqParams);
        } else {
            return lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService.getData(reqParams);
        }
    }

    @Path(DETAIL_CAUSE_CODE_GRID)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureCCPieChartDrilldownAsCSV() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHandoverFailureEnodeBCauseCodeDetailedEventAnalysisService.getDataAsCSV(
                    mapResourceLayerParameters(), response);
        } else if (TYPE_CELL.equals(type)) {
            return lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService.getDataAsCSV(
                    mapResourceLayerParameters(), response);
        } else {
            return lteHandoverFailureEcellCauseCodeDetailedEventAnalysisService.getDataAsCSV(
                    mapResourceLayerParameters(), response);
        }
    }
}
