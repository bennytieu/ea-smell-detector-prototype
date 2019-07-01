package de.example.smells;

import de.example.main.ModelAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class Detector {
    static List<EASmell> smells;
    ModelAdapter model;

    public Detector(ModelAdapter model) {
        smells = new ArrayList<>();
        this.model = model;
    }

    public static List<EASmell> getSmells() {
        return smells;
    }

    void addToSmells(EASmell smell) {
        smells.add(smell);
    }

    abstract public List<EASmell> detect();

}
