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




public class EcellQCIEventSummaryTestDataProvider {

    private EcellQCIEventSummaryTestDataProvider() {
    }

    public static Object[] provideTestData() {

        final CombinationGenerator<String> combinationGenerator = new CombinationGeneratorImpl.Builder<String>()
                .add(DISPLAY_PARAM, GRID_PARAM).add(TIME_QUERY_PARAM, FIVE_MINUTES, THIRTY_MINUTES, ONE_DAY, TWO_WEEKS)
                .add(TZ_OFFSET, TEST_VALUE_TIMEZONE_OFFSET).add(MAX_ROWS, DEFAULT_MAX_ROWS)
                .add(QCI_ID_COLUMN, TEST_VALUE_LTE_HFA_QCI)
                .add(CATEGORY_ID_2, LTE_HFA_PREP_CATEGORY_ID, LTE_HFA_EXEC_CATEGORY_ID)
                .add(CELL_SQL_NAME, TEST_VALUE_LTE_HFA_ECELL_NAME)
                .add(HIER321_ID, TEST_VALUE_LTE_HFA_HIER321_ID)
                .add(NODE_PARAM,TEST_VALUE_ECELL_NODE).build();
        return convertToArrayOfMultivaluedMap(combinationGenerator.getAllCombinations());
    }
}
