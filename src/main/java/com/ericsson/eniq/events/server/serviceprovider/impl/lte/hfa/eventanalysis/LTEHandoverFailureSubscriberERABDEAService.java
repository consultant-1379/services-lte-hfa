/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventanalysis;

import static com.ericsson.eniq.events.server.common.ApplicationConfigConstants.*;
import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.TechPackData.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.MultivaluedMap;

import com.ericsson.eniq.events.server.common.TechPackList;
import com.ericsson.eniq.events.server.common.tablesandviews.AggregationTableInfo;
import com.ericsson.eniq.events.server.datasource.loadbalancing.LoadBalancingPolicy;
import com.ericsson.eniq.events.server.query.QueryParameter;
import com.ericsson.eniq.events.server.serviceprovider.Service;
import com.ericsson.eniq.events.server.serviceprovider.impl.GenericService;
import com.ericsson.eniq.events.server.utils.FormattedDateTimeRange;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author evijred
 */
@Stateless
@Local(Service.class)
public class LTEHandoverFailureSubscriberERABDEAService extends GenericService {

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#runQuery(java.lang.String, java.lang.String, java.util.Map, com.ericsson.eniq.events.server.datasource.loadbalancing.LoadBalancingPolicy, java.util.Map)
     */
    @Override
    public String runQuery(final String query, final String requestId,
            final Map<String, QueryParameter> queryParameters, final LoadBalancingPolicy loadBalancPolicy,
            final Map<String, Object> dataParams) {
        final String tzOffset = (String) dataParams.get(TZ_OFFSET);
        return getDataService().getGridData(requestId, query, queryParameters, LTE_HFA_ERAB_EVENT_TIME_COLUMN_INDEX,
                tzOffset, loadBalancPolicy);
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getTemplatePath()
     */
    @Override
    public String getTemplatePath() {
        return LTE_HANDOVER_FAILURE_SUBSCRIBER_ERAB_DRILLDOWN;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificTemplateParameters(javax.ws.rs.core.MultivaluedMap, com.ericsson.eniq.events.server.utils.DateTimeRange.FormattedDateTimeRange, com.ericsson.eniq.events.server.utils.techpacks.TechPackList)
     */
    @Override
    public Map<String, Object> getServiceSpecificTemplateParameters(
            final MultivaluedMap<String, String> requestParameters, final FormattedDateTimeRange dateTimeRange,
            final TechPackList techPackList) {
        final Map<String, Object> dataServiceParams = new HashMap<String, Object>();
        dataServiceParams.put("isSetupEvent", false);
        final String groupName = requestParameters.getFirst(GROUP_NAME_PARAM);
        if (groupName != null) {
            dataServiceParams.put(GROUP_NAME_PARAM, groupName);
        }
        final String eventID = requestParameters.getFirst(EVENT_ID_PARAM);
        if (eventID != null) {
            dataServiceParams.put(EVENT_ID_PARAM, eventID);
        }
        final String imsi = requestParameters.getFirst(IMSI_PARAM);
        if (imsi != null) {
            dataServiceParams.put(IMSI_PARAM, imsi);
        }
        final String tac = requestParameters.getFirst(TAC_PARAM_UPPER_CASE);
        if (tac != null) {
            dataServiceParams.put(TAC_PARAM_UPPER_CASE, tac);
        }
        return dataServiceParams;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificDataServiceParameters(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public Map<String, Object> getServiceSpecificDataServiceParameters(
            final MultivaluedMap<String, String> requestParameters) {
        final Map<String, Object> dataServiceParams = new HashMap<String, Object>();
        dataServiceParams.put(TZ_OFFSET, requestParameters.getFirst(TZ_OFFSET));
        return dataServiceParams;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificQueryParameters(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public Map<String, QueryParameter> getServiceSpecificQueryParameters(
            final MultivaluedMap<String, String> requestParameters) {
        final Map<String, QueryParameter> dataServiceParams = new HashMap<String, QueryParameter>();
        dataServiceParams.put(CELL_SQL_ID,
                QueryParameter.createStringParameter(requestParameters.getFirst(CELL_SQL_ID)));
        dataServiceParams.put(CONTROLLER_SQL_ID,
                QueryParameter.createStringParameter(requestParameters.getFirst(CONTROLLER_SQL_ID)));
        dataServiceParams.put(EVENT_TIME_COLUMN,
                QueryParameter.createStringParameter(requestParameters.getFirst(EVENT_TIME_COLUMN)));
        dataServiceParams.put(EVENT_ID_SQL_PARAM,
                QueryParameter.createStringParameter(requestParameters.getFirst(EVENT_ID_PARAM)));
        dataServiceParams.put(TAC, QueryParameter.createStringParameter(requestParameters.getFirst(TAC)));

        if (requestParameters.containsKey(GROUP_NAME_PARAM)) {
            dataServiceParams.put(GROUP_NAME_PARAM,
                    QueryParameter.createStringParameter(requestParameters.getFirst(GROUP_NAME_PARAM)));
        } else {
            dataServiceParams.put(IMSI, QueryParameter.createStringParameter(requestParameters.getFirst(IMSI_PARAM)));
        }
        return dataServiceParams;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getRequiredParametersForQuery()
     */
    @Override
    public List<String> getRequiredParametersForQuery() {
        final List<String> requiredParams = new ArrayList<String>();
        requiredParams.add(TZ_OFFSET);
        requiredParams.add(CELL_SQL_ID); // eNodeB ID
        requiredParams.add(CONTROLLER_SQL_ID); //eCell ID
        requiredParams.add(EVENT_TIME_COLUMN); //EventTime
        requiredParams.add(EVENT_ID_PARAM); //EventTime
        requiredParams.add(TAC); //EventTime
        return requiredParams;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getStaticParameters()
     */
    @Override
    public MultivaluedMap<String, String> getStaticParameters() {
        return new MultivaluedMapImpl();
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getDrillDownTypeForService()
     */
    @Override
    public String getDrillDownTypeForService(final MultivaluedMap<String, String> requestParameters) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getAggregationView(java.lang.String)
     */
    @Override
    public AggregationTableInfo getAggregationView(final String type) {
        // TODO Auto-generated method stub
        //        return new AggregationTableInfo("", EventDataSourceType.RAW);
        return new AggregationTableInfo(NO_AGGREGATION_AVAILABLE);
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getApplicableTechPacks(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public List<String> getApplicableTechPacks(final MultivaluedMap<String, String> requestParameters) {
        final List<String> techPacks = new ArrayList<String>();
        techPacks.add(EVENT_E_LTE_HFA);
        return techPacks;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#areRawTablesRequiredForAggregationQueries()
     */
    @Override
    public boolean areRawTablesRequiredForAggregationQueries() {
        return false;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getMaxAllowableSize()
     */
    @Override
    public int getMaxAllowableSize() {
        return MAXIMUM_POSSIBLE_CONFIGURABLE_MAX_JSON_RESULT_SIZE;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#requiredToCheckValidParameterValue(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public boolean requiredToCheckValidParameterValue(final MultivaluedMap<String, String> requestParameters) {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getTimeColumnIndices()
     */
    @Override
    public List<Integer> getTimeColumnIndices() {
        final List<Integer> columnIndices = new ArrayList<Integer>();
        columnIndices.add(1);
        return columnIndices;
    }

    @Override
    public List<String> getRawTableKeys() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getTableSuffixKey()
     */
    @Override
    public String getTableSuffixKey() {
        return KEY_TYPE_ERR;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getMeasurementTypes()
     */
    @Override
    public List<String> getMeasurementTypes() {
        final List<String> lst = new ArrayList<String>();
        lst.add("ARRAY_ERAB");
        // TODO Auto-generated method stub
        return lst;
    }

}