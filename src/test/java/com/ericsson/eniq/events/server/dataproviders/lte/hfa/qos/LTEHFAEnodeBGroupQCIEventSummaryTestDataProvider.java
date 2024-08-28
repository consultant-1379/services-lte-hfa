/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.test.automation.util.CombinationUtils.*;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;

import com.ericsson.eniq.events.server.test.automation.util.CombinationGenerator;
import com.ericsson.eniq.events.server.test.automation.util.CombinationGeneratorImpl;
import static com.ericsson.eniq.events.server.common.EventIDConstants.CATEGORY_ID_2_PREP;
import static com.ericsson.eniq.events.server.common.EventIDConstants.CATEGORY_ID_2_EXEC;


/**
 * @author echimma
 * @since 2012
 *
 */
public class LTEHFAEnodeBGroupQCIEventSummaryTestDataProvider {

    private LTEHFAEnodeBGroupQCIEventSummaryTestDataProvider() {
    }

    public static Object[] provideTestData() {

        final CombinationGenerator<String> combinationGenerator = new CombinationGeneratorImpl.Builder<String>()
                .add(DISPLAY_PARAM, GRID_PARAM).add(TIME_QUERY_PARAM, FIVE_MINUTES, THIRTY_MINUTES, ONE_DAY, TWO_WEEKS)
                .add(TZ_OFFSET, TEST_VALUE_TIMEZONE_OFFSET).add(MAX_ROWS, DEFAULT_MAX_ROWS)
                .add(QCI_ID_COLUMN, TEST_VALUE_LTE_CFA_QCI)
                .add(CATEGORY_ID_2, CATEGORY_ID_2_PREP, CATEGORY_ID_2_EXEC)
                .add(GROUP_NAME_PARAM, SAMPLE_BSC_GROUP).build();
        return convertToArrayOfMultivaluedMap(combinationGenerator.getAllCombinations());
    }
}
