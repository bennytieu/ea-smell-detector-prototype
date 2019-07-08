package de.example.smells;

import de.example.main.ModelAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestDuplication {

    private Duplication detector;

    @BeforeAll
    void init() {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);
        Detector.setModel(model);
        detector = new Duplication();
    }

    @Test
    void test() {
        assertEquals(detector.detect().size(), 2);
    }
}