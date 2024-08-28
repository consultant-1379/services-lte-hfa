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
public class LTEHFAQoSDetailedEventAnalysisResource extends AbstractResource {

    private static final String LTE_HFA_ENODEB_QCI_DETAILED_EVENT_ANALYSIS_SERVICE = "LTEHFAEnodeBQCIDetailedEventAnalysisService";

    private static final String LTE_HFA_ECELL_QCI_DETAILED_EVENT_ANALYSIS_SERVICE = "EcellQCIDetailedEventAnalysisService";

    @EJB(beanName = LTE_HFA_ENODEB_QCI_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHFAEnodeBQCIDetailedEventAnalysisService;

    @EJB(beanName = LTE_HFA_ECELL_QCI_DETAILED_EVENT_ANALYSIS_SERVICE)
    private Service lteHFAEcellQCIDetailedEventAnalysisService;

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.resources.AbstractResource#getService()
     */
    @Override
    protected Service getService() {
        throw new UnsupportedOperationException();
    }

    @Path(QCI_ENODEB)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHFAEnodeBQCIDetailedEventAnalysisService() {
        return lteHFAEnodeBQCIDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(QCI_ENODEB)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHFAEnodeBQCIDetailedEventAnalysisServiceAsCSV() {
        return lteHFAEnodeBQCIDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(QCI_ECELL)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHFAEcellQCIDetailedEventAnalysisService() {
        return lteHFAEcellQCIDetailedEventAnalysisService.getData(mapResourceLayerParameters());
    }

    @Path(QCI_ECELL)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHFAEcellQCIDetailedEventAnalysisServiceAsCSV() {
        return lteHFAEcellQCIDetailedEventAnalysisService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

}
