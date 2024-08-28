/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2011 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.dataproviders.lte.handoverfailure;

import com.ericsson.eniq.events.server.test.automation.util.CombinationGenerator;
import com.ericsson.eniq.events.server.test.automation.util.CombinationGeneratorImpl;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.EventIDConstants.CATEGORY_ID_2_EXEC;
import static com.ericsson.eniq.events.server.test.automation.util.CombinationUtils.convertToArrayOfMultivaluedMap;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;

/**
 * @author echimma
 * @since 2011
 */
public class LTEHFACauseCodeRankingExecDataProvider {
    public static Object[] provideTestData() {
        final CombinationGenerator<String> combinationGenerator = new CombinationGeneratorImpl.Builder<String>()
                .add(DISPLAY_PARAM, GRID_PARAM).add(TIME_QUERY_PARAM, FIVE_MINUTES, THIRTY_MINUTES, ONE_DAY, TWO_WEEKS)
                .add(TZ_OFFSET, TEST_VALUE_TIMEZONE_OFFSET).add(MAX_ROWS, DEFAULT_MAX_ROWS)
                .add(CATEGORY_ID_SQL_PARAM, CATEGORY_ID_2_EXEC)
                .build();
        return convertToArrayOfMultivaluedMap(combinationGenerator.getAllCombinations());
    }

    private LTEHFACauseCodeRankingExecDataProvider() {

    }
}
