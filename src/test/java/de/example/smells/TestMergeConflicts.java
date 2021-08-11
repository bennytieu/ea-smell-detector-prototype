package de.example.smells;

import java.util.Collections;

import de.example.main.ModelAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMergeConflicts {

    private MergeConflicts detector;

    @Test
    void testCentralModel() {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);
        Detector.setModel(model);
        detector = new MergeConflicts(Collections.singletonList("Assignment"), 2);

        assertEquals(3, detector.detect().size());
    }

    @Test
    void testSmellExample() {
        ModelAdapter model = new ModelAdapter("SmellExample.xml", null);
        Detector.setModel(model);
        detector = new MergeConflicts(Collections.singletonList("Assignment"), 2);
        assertEquals(0, detector.detect().size());
    }
}
