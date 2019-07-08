package de.example.smells;

import de.example.main.ModelAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCyclicDependency {

    private ModelAdapter model = new ModelAdapter("Test.xml", null);
    private CyclicDependency detector = new CyclicDependency();

    @Test
    void test() {
        Detector.setModel(model);
        assertEquals(detector.detect().size(), 3);
    }
}
