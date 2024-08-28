/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.test.automation.util.CombinationUtils.convertToArrayOfMultivaluedMap;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;

import com.ericsson.eniq.events.server.test.automation.util.CombinationGenerator;
import com.ericsson.eniq.events.server.test.automation.util.CombinationGeneratorImpl;
import static com.ericsson.eniq.events.server.common.EventIDConstants.CATEGORY_ID_2_PREP;
import static com.ericsson.eniq.events.server.common.EventIDConstants.CATEGORY_ID_2_EXEC;

/**
 * @author evijred
 * @since 2011
 *
 */
public class LTEHFAEnodeBEventSummaryRankingDrillTestDataProvider {
    public static Object[] provideTestData() {

        final CombinationGenerator<String> combinationGenerator = new CombinationGeneratorImpl.Builder<String>()
                .add(DISPLAY_PARAM, GRID_PARAM)
                .add(TIME_QUERY_PARAM, FIVE_MINUTES, THIRTY_MINUTES, ONE_DAY, ONE_WEEK)
                .add(TZ_OFFSET, TEST_VALUE_TIMEZONE_OFFSET).add(MAX_ROWS, DEFAULT_MAX_ROWS)
                .add(HIER3_ID, "123456789012345")
                .add(CATEGORY_ID_PARAM, CATEGORY_ID_2_PREP, CATEGORY_ID_2_EXEC)
                .build();
        return convertToArrayOfMultivaluedMap(combinationGenerator.getAllCombinations());
    }
}