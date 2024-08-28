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
 * @author evijred
 * @since 2011
 *
 */
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class LTEHFAEventVolumeResource extends AbstractResource {

    private static final String LTE_HANDOVER_FAILURE_ENODEB_EVENT_VOLUME_SERVICE = "LTEHFAEnodeBEventVolumeService";

    private static final String LTE_HANDOVER_FAILURE_ECELL_EVENT_VOLUME_SERVICE = "LTEHFAEcellEventVolumeService";

    private static final String LTE_HANDOVER_FAILURE_NETWORK_EVENT_VOLUME_SERVICE = "LTEHFANetworkEventVolumeService";

    @EJB(beanName = LTE_HANDOVER_FAILURE_ENODEB_EVENT_VOLUME_SERVICE)
    private Service lteHFAEnodeBEventVolumeService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_ECELL_EVENT_VOLUME_SERVICE)
    private Service lteHFAEcellEventVolumeService;

    @EJB(beanName = LTE_HANDOVER_FAILURE_NETWORK_EVENT_VOLUME_SERVICE)
    private Service lteHFANetworkEventVolumeService;

    @Override
    protected Service getService() {
        throw new UnsupportedOperationException();
    }

    @Path(EVENT_VOLUME)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureEventVolume() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHFAEnodeBEventVolumeService.getData(reqParams);
        } else if (TYPE_CELL.equals(type)) {
            return lteHFAEcellEventVolumeService.getData(reqParams);
        }
        return lteHFAEnodeBEventVolumeService.getData(reqParams);
    }

    @Path(EVENT_VOLUME)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureEventVolumeAsCSV() {
        final MultivaluedMap<String, String> reqParams = mapResourceLayerParameters();
        final String type = reqParams.getFirst(TYPE_PARAM);
        if (TYPE_BSC.equals(type)) {
            return lteHFAEnodeBEventVolumeService.getDataAsCSV(mapResourceLayerParameters(), response);
        } else if (TYPE_CELL.equals(type)) {
            return lteHFAEcellEventVolumeService.getDataAsCSV(mapResourceLayerParameters(), response);
        }
        return lteHFAEnodeBEventVolumeService.getDataAsCSV(mapResourceLayerParameters(), response);
    }

    @Path(NETWORK_EVENT_VOLUME)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLteHandoverFailureNetworkEventVolume() {
        return lteHFANetworkEventVolumeService.getData(mapResourceLayerParameters());
    }

    @Path(NETWORK_EVENT_VOLUME)
    @GET
    @Produces(MediaTypeConstants.APPLICATION_CSV)
    public Response getLteHandoverFailureNetworkEventVolumeAsCSV() {
        return lteHFANetworkEventVolumeService.getDataAsCSV(mapResourceLayerParameters(), response);
    }
}
