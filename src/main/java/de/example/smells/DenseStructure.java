package de.example.smells;

import java.util.List;

import static de.example.smells.Constants.MAX_AVG_DEGREE;

public class DenseStructure extends Detector {

    public DenseStructure() {
        super("Dense Structure");
    }

    // could use clustering coefficient
    public List<EASmell> detect() {
        double v = model.getElements().size();
        double e = model.getRelationships().size();
        double avgDegree = e / (v/* * (v - 1)*/);
        if (avgDegree > MAX_AVG_DEGREE) {
            addToSmells(new EASmell("Dense Structure", null, " with average degree of " + avgDegree));
        }
        return result;
    }
}
