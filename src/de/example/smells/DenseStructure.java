package de.example.smells;

import de.example.main.ModelAdapter;

import java.util.List;

public class DenseStructure extends Detector {

    public DenseStructure(ModelAdapter model) {
        super(model);
    }

    public List<EASmell> detect() {
        double avgDegree = ((double) model.getElements().size() / model.getRelationships().size());
        if (avgDegree > 0.5) {
            EASmell denseStructure = new EASmell("Dense Structure", null);
            denseStructure.setContext(" with average degree of " + avgDegree);
            addToSmells(denseStructure);
        }
        return smells;
    }
}
