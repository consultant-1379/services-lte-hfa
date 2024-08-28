/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.eniq.events.server.serviceprovider.impl.lte.hfa.eventvolume;

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

import com.ericsson.eniq.events.server.common.EventDataSourceType;
import com.ericsson.eniq.events.server.common.TechPackList;
import com.ericsson.eniq.events.server.common.tablesandviews.AggregationTableInfo;
import com.ericsson.eniq.events.server.query.QueryParameter;
import com.ericsson.eniq.events.server.serviceprovider.Service;
import com.ericsson.eniq.events.server.serviceprovider.impl.GenericService;
import com.ericsson.eniq.events.server.utils.FormattedDateTimeRange;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Stateless
@Local(Service.class)
public class LTEHFAEnodeBEventVolumeService extends GenericService {

    private boolean isDayInterval = false;

    @Override
    public String getTemplatePath() {
        return LTE_HANDOVER_FAILURE_ENODEB_EVENT_VOLUME_ANALYSIS;
    }

    @Override
    public Map<String, Object> getServiceSpecificTemplateParameters(final MultivaluedMap<String, String> requestParameters,
                                                                    final FormattedDateTimeRange dateTimeRange, final TechPackList techPackList) {
        final Map<String, Object> serviceSpecificTemplateParameters = new HashMap<String, Object>();
        serviceSpecificTemplateParameters.put(AGGREGATION_VIEW, techPackList.getTechPack(EVENT_E_LTE_HFA).getErrAggregationView());
        serviceSpecificTemplateParameters.put(START_TIME, "'" + dateTimeRange.getStartDateTime() + "'");
        serviceSpecificTemplateParameters.put(END_TIME, "'" + dateTimeRange.getEndDateTime() + "'");
        serviceSpecificTemplateParameters.put(INTERVAL_PARAM, getInterval(dateTimeRange));
        if (getInterval(dateTimeRange) == MINUTES_IN_A_DAY) {
            isDayInterval = true;
        } else {
            isDayInterval = false;
        }
        return serviceSpecificTemplateParameters;
    }

    @Override
    public Map<String, Object> getServiceSpecificDataServiceParameters(final MultivaluedMap<String, String> requestParameters) {
        final Map<String, Object> dataServiceParameters = new HashMap<String, Object>();
        dataServiceParameters.put(TZ_OFFSET, requestParameters.getFirst(TZ_OFFSET));
        return dataServiceParameters;
    }

    @Override
    public Map<String, QueryParameter> getServiceSpecificQueryParameters(final MultivaluedMap<String, String> requestParameters) {

        final Map<String, QueryParameter> dataServiceParameters = new HashMap<String, QueryParameter>();
        long hashedIdForController = 0;
        final String hashedIdForControllerAsString = requestParameters.getFirst(CONTROLLER_SQL_ID); // HIER3_ID
        if (hashedIdForControllerAsString == null) {
            final String node = requestParameters.getFirst(NODE_PARAM);
            if (node != null) {
                final String[] allData = node.split(DELIMITER);
                hashedIdForController = getQueryUtils().createHashIDForController(allData[2], allData[0], allData[1]);
            }
        } else {
            hashedIdForController = Long.parseLong(hashedIdForControllerAsString);
        }
        if (hashedIdForController != 0) {
            dataServiceParameters.put(CONTROLLER_SQL_ID, QueryParameter.createLongParameter(hashedIdForController));
        }
        return dataServiceParameters;

    }

    @Override
    public List<String> getRequiredParametersForQuery() {
        final List<String> requiredParameters = new ArrayList<String>();
        requiredParameters.add(TZ_OFFSET);
        return requiredParameters;
    }

    @Override
    public MultivaluedMap<String, String> getStaticParameters() {
        return new MultivaluedMapImpl();
    }

    @Override
    public String getDrillDownTypeForService(final MultivaluedMap<String, String> requestParameters) {
        return null;
    }

    @Override
    public AggregationTableInfo getAggregationView(final String type) {
        return new AggregationTableInfo(HIER3_EVENTID, EventDataSourceType.AGGREGATED_15MIN, EventDataSourceType.AGGREGATED_DAY);
    }

    @Override
    public List<String> getApplicableTechPacks(final MultivaluedMap<String, String> requestParameters) {
        final List<String> techPacks = new ArrayList<String>();
        techPacks.add(EVENT_E_LTE_HFA);
        return techPacks;
    }

    @Override
    public boolean areRawTablesRequiredForAggregationQueries() {
        return false;
    }

    @Override
    public int getMaxAllowableSize() {
        return MAXIMUM_POSSIBLE_CONFIGURABLE_MAX_JSON_RESULT_SIZE;
    }

    @Override
    public boolean requiredToCheckValidParameterValue(final MultivaluedMap<String, String> requestParameters) {
        if (requestParameters.containsKey(GROUP_NAME_PARAM) || requestParameters.containsKey(NODE_PARAM)) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> getRawTableKeys() {
        final List<String> rawTableKeys = new ArrayList<String>();
        rawTableKeys.add(KEY_TYPE_ERR);
        return rawTableKeys;
    }

    @Override
    public String getTableSuffixKey() {
        return null;
    }

    @Override
    public List<String> getMeasurementTypes() {
        return null;
    }

    @Override
    public List<Integer> getTimeColumnIndices() {
        final List<Integer> columnIndices = new ArrayList<Integer>();
        if (isDayInterval) {
            return null;
        } else {
            columnIndices.add(1);
            return columnIndices;
        }
    }
}