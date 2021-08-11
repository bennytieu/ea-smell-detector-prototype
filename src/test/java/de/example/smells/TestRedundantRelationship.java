package de.example.smells;

import java.util.Collections;

import de.example.main.ModelAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestRedundantRelationship {

    private RedundantRelationship detector;

    @Test
    void testCentralModel() {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);
        Detector.setModel(model);
        detector = new RedundantRelationship(Collections.singletonList("Serving"));

        assertEquals(1, detector.detect().size());
    }

    @Test
    void testSmellExample() {
        ModelAdapter model = new ModelAdapter("SmellExample.xml", null);
        Detector.setModel(model);
        detector = new RedundantRelationship(Collections.singletonList("Serving"));
        assertEquals(1, detector.detect().size());
    }
}
