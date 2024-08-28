/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.dataproviders.lte;

import com.ericsson.eniq.events.server.test.automation.util.CombinationGenerator;
import com.ericsson.eniq.events.server.test.automation.util.CombinationGeneratorImpl;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.test.automation.util.CombinationUtils.convertToArrayOfMultivaluedMap;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;

/**
 * @author evijred
 * @since 2011
 *
 */
public class LTEHFASubscriberERABDetailedEventAnalysisTestDataProvider {
    public static Object[] provideTestData() {

        final CombinationGenerator<String> combinationGenerator = new CombinationGeneratorImpl.Builder<String>()
                .add(DISPLAY_PARAM, GRID_PARAM).add(DATE_FROM_QUERY_PARAM, "20092011")
                .add(DATE_TO_QUERY_PARAM, "27102011").add(TIME_FROM_QUERY_PARAM, "0900")
                .add(TIME_TO_QUERY_PARAM, "0930").add(TZ_OFFSET, TIME_ZONE_OFFSET_OF_PLUS_ONE_HOUR)
                .add(EVENT_ID_PARAM, "4111", "4110", "4113", "4112", "4103", "4102", "4105", "4104")
                .add(HIER3_ID, "4809532081614999117").add(HIER321_ID, "7210756712490856540")
                .add(IMSI_PARAM, "123456789012345").add(EVENT_TIME, "2011-10-26 08:12:00.121")
                .add(TAC, String.valueOf(SAMPLE_TAC)).add(MAX_ROWS, DEFAULT_MAX_ROWS).build();
        return convertToArrayOfMultivaluedMap(combinationGenerator.getAllCombinations());
    }
}