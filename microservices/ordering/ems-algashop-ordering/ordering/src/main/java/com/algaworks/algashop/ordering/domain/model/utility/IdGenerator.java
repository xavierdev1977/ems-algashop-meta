package com.algaworks.algashop.ordering.domain.model.utility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;
import io.hypersistence.tsid.TSID;

import java.util.UUID;

public class IdGenerator {

    private static final TimeBasedEpochRandomGenerator timeBasedEpochRandomGenerator
            = Generators.timeBasedEpochRandomGenerator();

    private static final TSID.Factory tsidFactory = TSID.Factory.INSTANCE;

    private IdGenerator() {
    }

    public static UUID generateTimeBasedUUID() {
        return timeBasedEpochRandomGenerator.generate();
    }

    /*
    * TSID_NODE
    * TSID_NODE_COUNT
     */
    public static TSID generateTSID(){
        return tsidFactory.generate();
    }
}
