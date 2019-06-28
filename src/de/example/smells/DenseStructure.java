package de.example.smells;

import de.example.main.ModelAdapter;

import java.util.List;

public class DenseStructure extends Detector {

    public DenseStructure(ModelAdapter model) {
        super(model);
    }

    public List<EASmell> detect() {
        addToSmells(new EASmell("Test"));
        return smells;
    }
}
