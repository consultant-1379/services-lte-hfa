/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.qos.qcianalysis;

import static com.ericsson.eniq.events.server.common.ApplicationConfigConstants.*;
import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.tablesandviews.TableKeys.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.MultivaluedMap;

import com.ericsson.eniq.events.server.common.EventDataSourceType;
import com.ericsson.eniq.events.server.common.TechPackList;
import com.ericsson.eniq.events.server.common.tablesandviews.AggregationTableInfo;
import com.ericsson.eniq.events.server.query.QueryParameter;
import com.ericsson.eniq.events.server.serviceprovider.Service;
import com.ericsson.eniq.events.server.serviceprovider.impl.GenericService;
import com.ericsson.eniq.events.server.utils.FormattedDateTimeRange;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author Jegan
 * @since 2012
 *
 */
@Stateless
@Local(Service.class)
public class LTEHFAEnodeBQCICategorySummaryService extends GenericService {

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getTemplatePath()
     */
    @Override
    public String getTemplatePath() {
        return LTE_HFA_QOS_QCI_ENODEB_CATEGORY_SUMMARY_ANALYSIS;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificTemplateParameters(javax.ws.rs.core.MultivaluedMap, com.ericsson.eniq.events.server.utils.FormattedDateTimeRange, com.ericsson.eniq.events.server.common.TechPackList)
     */
    @Override
    public Map<String, Object> getServiceSpecificTemplateParameters(
            final MultivaluedMap<String, String> requestParameters, final FormattedDateTimeRange dateTimeRange,
            final TechPackList techPackList) {
        final Map<String, Object> serviceSpecificTemplateParameters = new HashMap<String, Object>();
        serviceSpecificTemplateParameters.put(AGGREGATION_VEIW_LIST, techPackList.getTechPack(EVENT_E_LTE_HFA_TP_NAME)
                .getAggregationViewsWithMeasurementTypes());
        final String groupName = requestParameters.getFirst(GROUP_NAME_PARAM);
        if (groupName != null) {
            serviceSpecificTemplateParameters.put(GROUP_NAME_PARAM, groupName);
        }
        return serviceSpecificTemplateParameters;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificDataServiceParameters(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public Map<String, Object> getServiceSpecificDataServiceParameters(
            final MultivaluedMap<String, String> requestParameters) {
        final Map<String, Object> dataServiceParameters = new HashMap<String, Object>();
        dataServiceParameters.put(TZ_OFFSET, requestParameters.getFirst(TZ_OFFSET));
        return dataServiceParameters;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getServiceSpecificQueryParameters(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public Map<String, QueryParameter> getServiceSpecificQueryParameters(
            final MultivaluedMap<String, String> requestParameters) {
        final Map<String, QueryParameter> queryParameters = new HashMap<String, QueryParameter>();
        final String hashedIdForControllerAsString = requestParameters.getFirst(HIER3_ID);
        long hashedIdForController = 0;
        String hierarchy3 = null;
        if (hashedIdForControllerAsString == null) {
            final String node = requestParameters.getFirst(NODE_PARAM);
          
            if (node != null) {
            	queryParameters.put(NODE, QueryParameter.createStringParameter(node));
                final String[] allData = node.split(DELIMITER);
                hashedIdForController = getQueryUtils().createHashIDForController(allData[2], allData[0], allData[1]);
                hierarchy3 = allData[0];

            }
        } else {
            hashedIdForController = Long.parseLong(hashedIdForControllerAsString);
        }
        if (hashedIdForController != 0) {
            queryParameters.put(HIERARCHY_3_SQL, QueryParameter.createStringParameter(hierarchy3));
            queryParameters.put(HIER3_ID, QueryParameter.createLongParameter(hashedIdForController));
        }
        return queryParameters;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getRequiredParametersForQuery()
     */
    @Override
    public List<String> getRequiredParametersForQuery() {
        final List<String> requiredParams = new ArrayList<String>();
        requiredParams.add(TZ_OFFSET);
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
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getDrillDownTypeForService(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public String getDrillDownTypeForService(final MultivaluedMap<String, String> requestParameters) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getAggregationView(java.lang.String)
     */
    @Override
    public AggregationTableInfo getAggregationView(final String type) {
        return new AggregationTableInfo(HIER3_EVENTID_QCI, EventDataSourceType.AGGREGATED_15MIN,
                EventDataSourceType.AGGREGATED_DAY);
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getApplicableTechPacks(javax.ws.rs.core.MultivaluedMap)
     */
    @Override
    public List<String> getApplicableTechPacks(final MultivaluedMap<String, String> requestParameters) {
        final List<String> techPacks = new ArrayList<String>();
        techPacks.add(EVENT_E_LTE_HFA_TP_NAME);
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
        if (requestParameters.containsKey(GROUP_NAME_PARAM) || requestParameters.containsKey(NODE_PARAM)) {
            return true;
        }
        return false;

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
        final List<String> listOfMeasurementTypes = new ArrayList<String>();
        listOfMeasurementTypes.add(ARRAY_ERAB);
        return listOfMeasurementTypes;
    }

    /* (non-Javadoc)
     * @see com.ericsson.eniq.events.server.serviceprovider.impl.GenericServiceInterface#getRawTableKeys()
     */
    @Override
    public List<String> getRawTableKeys() {
        return null;
    }
}
