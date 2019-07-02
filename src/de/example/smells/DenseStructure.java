package de.example.smells;

import de.example.main.ModelAdapter;

import java.util.List;

import static de.example.smells.Constants.MAX_AVG_DEGREE;

public class DenseStructure extends Detector {

    public DenseStructure(ModelAdapter model) {
        super(model);
    }

    public List<EASmell> detect() {
        double v = model.getElements().size();
        double e = model.getRelationships().size();
        double avgDegree = e / (v/* * (v - 1)*/);
        if (avgDegree > MAX_AVG_DEGREE) {
            EASmell denseStructure = new EASmell("Dense Structure", null);
            denseStructure.setContext(" with average degree of " + avgDegree);
            addToSmells(denseStructure);
        }
        return smells;
    }
}
