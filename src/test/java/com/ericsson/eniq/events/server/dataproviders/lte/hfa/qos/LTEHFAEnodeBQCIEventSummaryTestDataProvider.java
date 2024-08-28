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
import static com.ericsson.eniq.events.server.common.EventIDConstants.HIERARCHY_3_SQL;



/**
 * @author echimma
 * @since 2012
 *
 */
public class LTEHFAEnodeBQCIEventSummaryTestDataProvider {

    private LTEHFAEnodeBQCIEventSummaryTestDataProvider() {
    }

    public static Object[] provideTestData() {

        final CombinationGenerator<String> combinationGenerator = new CombinationGeneratorImpl.Builder<String>()
                .add(DISPLAY_PARAM, GRID_PARAM).add(TIME_QUERY_PARAM, FIVE_MINUTES, THIRTY_MINUTES, ONE_DAY, TWO_WEEKS)
                .add(TZ_OFFSET, TEST_VALUE_TIMEZONE_OFFSET).add(MAX_ROWS, DEFAULT_MAX_ROWS)
                .add(QCI_ID_COLUMN, TEST_VALUE_LTE_HFA_QCI)
                .add(NODE_PARAM, TEST_VALUE_ENODEB_NODE)
                .add(CATEGORY_ID_2, CATEGORY_ID_2_PREP, CATEGORY_ID_2_EXEC)
                .add(HIERARCHY_3_SQL, "ONRM_RootMo_R:LTE01ERBS00004")
                .add(HIER3_ID, TEST_VALUE_LTE_HFA_HIER3_ID).build();
        return convertToArrayOfMultivaluedMap(combinationGenerator.getAllCombinations());
    }
}
