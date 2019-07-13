package de.example.smells;

class Constants {

    // Dense Structure
    static final double MAX_AVG_DEGREE = 2;

    // Documentation
    static final int MAX_DOCUMENTATION_LENGTH = 256;

    // Duplication
    static final int DUPLICATED_WORDS = 2;
    static final double DUPLICATED_WORDS_RATIO = 0.75;

    // Hub-like Modularization
    static final int LARGE_FAN_IN = 10;
    static final int LARGE_FAN_OUT = 10;

    // Message Chain
    static final int MAX_SERVICE_CHAIN_LENGTH = 4;

    // Weakened Modularity
    static final int MIN_INTERNAL_RELATIONS = 3;
    static final double MODULARITY_RATIO = 1;

}
