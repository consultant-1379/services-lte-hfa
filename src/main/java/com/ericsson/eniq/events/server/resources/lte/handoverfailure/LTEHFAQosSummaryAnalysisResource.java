/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
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
 * @author jegan
 * @since 2012
 *
 */
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class LTEHFAQosSummaryAnalysisResource extends AbstractResource {

    private static final String LTE_HFA_ENODEB_QCI_CATEGORY_SUMMARY_SERVICE = "LTEHFAEnodeBQCICategorySummaryService";

    private static final String LTE_HFA_ENODEB_QCI_EVENT_SUMMARY_SERVICE = "LTEHFAEnodeBQCIEventSummaryService";

    private static final String LTE_HFA_ECELL_QCI_CATEGORY_SUMMARY_SERVICE = "EcellQCICategorySummaryService";

    private static final String LTE_HFA_ECELL_QCI_EVENT_SUMMARY_SERVICE = "EcellQCIEventSummaryService";

    @EJB(beanName = LTE_HFA_ENODEB_QCI_CATEGORY_SUMMARY_SERVICE)
    private Service lteHFAEnodeBQCICategorySummaryService;

    @EJB(beanName = LTE_HFA_ENODEB_QCI_EVENT_SUMMARY_SERVICE)
    private Service lteHFAEnodeBQCIEventSummaryService;

    @EJB(beanName = LTE_HFA_ECELL_QCI_CATEGORY_SUMMARY_SERVICE)
    private Service lteHFAEcellQCICategorySummaryService;

    @EJB(beanName = LTE_HFA_ECELL_QCI_EVENT_SUMMARY_SERVICE)
    private Service lteHFAEcellQCIEventSummaryService;

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.resources.AbstractResource#getService()
     */
    @Override
    protected Service getService() {
        throw new UnsupportedOperationException();
    }

    @Path(ENODEB)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHFAQCICategorySummaryEnodeB() {
        return lteHFAEnodeBQCICategorySummaryService.getData(mapResourceLayerParameters());
    }

    @Path(ENODEB)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHFAQCICategorySummaryEnodeBAsCSV() {
        return lteHFAEnodeBQCICategorySummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(ECELL)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHFAQCICategorySummaryEcell() {
        return lteHFAEcellQCICategorySummaryService.getData(mapResourceLayerParameters());
    }

    @Path(ECELL)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHFAQCICategorySummaryEcellAsCSV() {
        return lteHFAEcellQCICategorySummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(QCI_CATEGARY_SUMMARY)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHFAQCICategorySummaryNode() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHFAEnodeBQCICategorySummaryService.getData(reqParams);
        }
        return lteHFAEcellQCICategorySummaryService.getData(reqParams);
    }

    @Path(QCI_CATEGARY_SUMMARY)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHFAQCICategorySummaryNodeAsCSV() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHFAEnodeBQCICategorySummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
        }
        return lteHFAEcellQCICategorySummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(QCI_EVENT_SUMMARY_ENODEB)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHFAEnodeBQCIEventSummaryService() {
        return lteHFAEnodeBQCIEventSummaryService.getData(mapResourceLayerParameters());
    }

    @Path(QCI_EVENT_SUMMARY_ENODEB)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHFAEnodeBQCIEventSummaryServiceAsCSV() {
        return lteHFAEnodeBQCIEventSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(QCI_EVENT_SUMMARY_ECELL)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHFAEcellQCIEventSummaryService() {
        return lteHFAEcellQCIEventSummaryService.getData(mapResourceLayerParameters());
    }

    @Path(QCI_EVENT_SUMMARY_ECELL)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHFAEcellQCIEventSummaryServiceAsCSV() {
        return lteHFAEcellQCIEventSummaryService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

}
