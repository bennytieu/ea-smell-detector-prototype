package de.example.smells;

import java.util.Collections;

import de.example.main.ModelAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMultipleRelationships {

    private MultipleRelationships detector;

    @Test
    void testCentralModel() {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);
        Detector.setModel(model);
        detector = new MultipleRelationships(Collections.singletonList("Serving"), 2);

        assertEquals(5, detector.detect().size());
    }

    @Test
    void testSmellExample() {
        ModelAdapter model = new ModelAdapter("SmellExample.xml", null);
        Detector.setModel(model);
        detector = new MultipleRelationships(Collections.singletonList("Serving"), 2);
        assertEquals(8, detector.detect().size());
    }
}
