package de.example.smells;

import de.example.main.ModelAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestDeadComponent {

    private DeadComponent detector;

    @BeforeAll
    void init() {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);
        Detector.setModel(model);
        detector = new DeadComponent();
    }

    @Test
    void test() {
        assertEquals(detector.detect().size(), 0);
    }
}