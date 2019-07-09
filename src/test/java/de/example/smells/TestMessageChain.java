package de.example.smells;

import de.example.main.ModelAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMessageChain {

    private MessageChain detector;

    @BeforeAll
    void init() {
        ModelAdapter model = new ModelAdapter("Test.xml", null);
        Detector.setModel(model);
        detector = new MessageChain();
    }

    @Test
    void test() {
        assertEquals(detector.detect().size(), 1);
    }
}
