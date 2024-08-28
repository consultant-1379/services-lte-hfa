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

package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import static com.ericsson.eniq.events.server.common.ApplicationConfigConstants.*;
import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.TechPackData.*;

import java.util.*;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;

import com.ericsson.eniq.events.server.common.TechPackList;
import com.ericsson.eniq.events.server.common.tablesandviews.AggregationTableInfo;
import com.ericsson.eniq.events.server.query.QueryParameter;
import com.ericsson.eniq.events.server.serviceprovider.Service;
import com.ericsson.eniq.events.server.serviceprovider.impl.GenericService;
import com.ericsson.eniq.events.server.utils.FormattedDateTimeRange;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 
 * @since 2011
 */
@Stateless
@Local(Service.class)
public class LTEHandoverFailureEventHandoverStageService extends GenericService {

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getTemplatePath()
     */
    @Override
    public String getTemplatePath() {
        return LTE_HANDOVER_FAILURE_SUBSCRIBER_EVENT_HANDOVERSTAGE_ANALYSIS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificTemplateParameters(javax.ws.rs.core.MultivaluedMap
     * , com.ericsson.eniq.events.server.utils.DateTimeRange.FormattedDateTimeRange, com.ericsson.eniq.events.server.utils.techpacks.TechPackList)
     */
    @Override
    public Map<String, Object> getServiceSpecificTemplateParameters(final MultivaluedMap<String, String> requestParameters,
                                                                    final FormattedDateTimeRange dateTimeRange, final TechPackList techPackList) {
        final Map<String, Object> serviceSpecificTemplateParameters = new HashMap<String, Object>();
        final String groupName = requestParameters.getFirst(GROUP_NAME_PARAM);
        if (groupName != null) {
            serviceSpecificTemplateParameters.put(GROUP_NAME_PARAM, groupName);
        }
        final String imsi = requestParameters.getFirst(IMSI_PARAM);
        if (imsi != null) {
            serviceSpecificTemplateParameters.put(IMSI_PARAM, imsi);
        }
        return serviceSpecificTemplateParameters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificDataServiceParameters(javax.ws.rs.core.
     * MultivaluedMap)
     */
    @Override
    public Map<String, Object> getServiceSpecificDataServiceParameters(final MultivaluedMap<String, String> requestParameters) {
        final Map<String, Object> dataServiceParameters = new HashMap<String, Object>();
        dataServiceParameters.put(TZ_OFFSET, requestParameters.getFirst(TZ_OFFSET));
        return dataServiceParameters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificQueryParameters(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public Map<String, QueryParameter> getServiceSpecificQueryParameters(final MultivaluedMap<String, String> requestParameters) {
        final Map<String, QueryParameter> dataServiceParameters = new HashMap<String, QueryParameter>();
        final String categoryId = requestParameters.getFirst(CATEGORY_ID_PARAM);
        if (StringUtils.isNotBlank(categoryId)) {
            final Long longParam = Long.valueOf(categoryId);
            requestParameters.remove(CATEGORY_ID_PARAM);
            dataServiceParameters.put(CATEGORY_ID_SQL_PARAM, QueryParameter.createLongParameter(longParam));
        }
        return dataServiceParameters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getRequiredParametersForQuery()
     */
    @Override
    public List<String> getRequiredParametersForQuery() {
        final List<String> requiredParameters = new ArrayList<String>();
        requiredParameters.add(TZ_OFFSET);
        return requiredParameters;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getStaticParameters()
     */
    @Override
    public MultivaluedMap<String, String> getStaticParameters() {
        return new MultivaluedMapImpl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getDrillDownTypeForService()
     */
    @Override
    public String getDrillDownTypeForService(final MultivaluedMap<String, String> requestParameters) {
        final String categoryId = requestParameters.getFirst(CATEGORY_ID_PARAM);
        if (StringUtils.isNotBlank(categoryId)) {
            return CATEGORY_PARAM;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getAggregationView(java.lang.String)
     */
    @Override
    public AggregationTableInfo getAggregationView(final String type) {
        return new AggregationTableInfo(NO_AGGREGATION_AVAILABLE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getApplicableTechPacks(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public List<String> getApplicableTechPacks(final MultivaluedMap<String, String> requestParameters) {
        final List<String> techPacks = new ArrayList<String>();
        techPacks.add(EVENT_E_LTE_HFA);
        return techPacks;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#areRawTablesRequiredForAggregationQueries()
     */
    @Override
    public boolean areRawTablesRequiredForAggregationQueries() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getMaxAllowableSize()
     */
    @Override
    public int getMaxAllowableSize() {
        return MAXIMUM_POSSIBLE_CONFIGURABLE_MAX_JSON_RESULT_SIZE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#requiredToCheckValidParameterValue(javax.ws.rs.core.MultivaluedMap
     * )
     */
    @Override
    public boolean requiredToCheckValidParameterValue(final MultivaluedMap<String, String> requestParameters) {
        if (requestParameters.containsKey(GROUP_NAME_PARAM) || requestParameters.containsKey(IMSI_PARAM)) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getTableSuffixKey()
     */
    @Override
    public String getTableSuffixKey() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getMeasurementTypes()
     */
    @Override
    public List<String> getMeasurementTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getRawTableKeys()
     */
    @Override
    public List<String> getRawTableKeys() {
        final List<String> rawTableKeys = new ArrayList<String>();
        rawTableKeys.add(KEY_TYPE_ERR);
        return rawTableKeys;
    }
}