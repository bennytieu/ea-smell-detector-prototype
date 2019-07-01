package de.example.smells;

import de.example.main.ModelAdapter;

import java.util.List;

public class WeakenedModularity extends Detector {

    public WeakenedModularity(ModelAdapter model) {
        super(model);
    }

    public List<EASmell> detect() {
        addToSmells(new EASmell("Weakened Modularity", null));
        return smells;
    }
}
