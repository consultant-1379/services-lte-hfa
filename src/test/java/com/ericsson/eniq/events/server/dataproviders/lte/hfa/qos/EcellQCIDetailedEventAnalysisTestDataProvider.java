/**
 * -----------------------------------------------------------------------
 *     Copyright (C) 2012 LM Ericsson Limited.  All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.ericsson.eniq.events.server.dataproviders.lte.hfa.qos;

import static com.ericsson.eniq.events.server.common.ApplicationConstants.*;
import static com.ericsson.eniq.events.server.common.EventIDConstants.*;
import static com.ericsson.eniq.events.server.test.automation.util.CombinationUtils.*;
import static com.ericsson.eniq.events.server.test.common.ApplicationTestConstants.*;

import com.ericsson.eniq.events.server.test.automation.util.CombinationGenerator;
import com.ericsson.eniq.events.server.test.automation.util.CombinationGeneratorImpl;

/**
 * @author echimma
 * @since 2012
 *
 */
public class EcellQCIDetailedEventAnalysisTestDataProvider {
    private EcellQCIDetailedEventAnalysisTestDataProvider() {
    }

    public static Object[] provideTestData() {

        final CombinationGenerator<String> combinationGenerator = new CombinationGeneratorImpl.Builder<String>()
                .add(DISPLAY_PARAM, GRID_PARAM)
                .add(TIME_QUERY_PARAM, FIVE_MINUTES, THIRTY_MINUTES, ONE_DAY, TWO_WEEKS)
                .add(TZ_OFFSET, TEST_VALUE_TIMEZONE_OFFSET)
                .add(MAX_ROWS, DEFAULT_MAX_ROWS)
                .add(HIER321_ID, TEST_VALUE_LTE_HFA_HIER321_ID)
                .add(QCI_ID_COLUMN, TEST_VALUE_LTE_HFA_QCI)
                .add(EVENT_ID_PARAM, INTERNAL_PROC_HO_PREP_X2_OUT_HFA, INTERNAL_PROC_HO_EXEC_X2_IN_HFA,
                		INTERNAL_PROC_HO_EXEC_X2_OUT_HFA,INTERNAL_PROC_HO_PREP_S1_OUT_HFA,INTERNAL_PROC_HO_EXEC_S1_IN_HFA,INTERNAL_PROC_HO_EXEC_S1_OUT_HFA)
                        .build();
        return convertToArrayOfMultivaluedMap(combinationGenerator.getAllCombinations());
    }
}
