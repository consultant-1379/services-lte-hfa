/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.integritytests.lte.hfa.eventanalysis.handoverstage.eCell;

import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;

import com.ericsson.eniq.events.server.json.JSONException;
import com.ericsson.eniq.events.server.json.JSONObject;
import com.ericsson.eniq.events.server.test.queryresults.QueryResult;

/**
 * @author evijred
 * @since 2011
 *
 */ 

public class LTEHFATgtEcellEventSummaryResultRaw implements QueryResult {

    private String thier321Id;

    private String vendor;

    private String enodeB;
    
    private String eventType;
    
    private String eventId;

    private String noOfErrors;

    private String impactedSubscribers;
    
    private String ratDesc;

    public String getTHier321Id() {
        return thier321Id;
    }
    
    public String getVendor() {
        return vendor;
    }
    
    public String getEnodeB() {
        return enodeB;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public String getEventId() {
        return eventId;
    } 

    public String getNoOfErrors() {
        return noOfErrors;
    }

    public String getImpactedSubscribers() {
        return impactedSubscribers;
    }
    public String getRatDesc() {
        return ratDesc;
    }

    @Override
    public void parseJSONObject(final JSONObject jsonObject) throws JSONException {
        thier321Id = jsonObject.getString(INDEX_OF_FIRST_COLUMN_IN_JSON_RESULT);
        vendor = jsonObject.getString(INDEX_OF_SECOND_COLUMN_IN_JSON_RESULT);
        enodeB = jsonObject.getString(INDEX_OF_THIRD_COLUMN_IN_JSON_RESULT);
        eventType = jsonObject.getString(INDEX_OF_FOURTH_COLUMN_IN_JSON_RESULT);
        eventId = jsonObject.getString(INDEX_OF_FIFTH_COLUMN_IN_JSON_RESULT);
        noOfErrors = jsonObject.getString(INDEX_OF_SEVENTH_COLUMN_IN_JSON_RESULT);
        impactedSubscribers = jsonObject.getString(INDEX_OF_EIGHTH_COLUMN_IN_JSON_RESULT);
        ratDesc = jsonObject.getString(INDEX_OF_NINTH_COLUMN_IN_JSON_RESULT);
    }
}
