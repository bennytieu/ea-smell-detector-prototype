package de.example.smells;

import de.example.main.ModelAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class Detector {
    static ModelAdapter model;
    static List<EASmell> smells;
    String smellName;
    List<EASmell> result;

    Detector() {
        smells = new ArrayList<>();
    }

    Detector(String name) {
        smells = new ArrayList<>();
        smellName = name;
        result = new ArrayList<>();
    }

    public static List<EASmell> getSmells() {
        return smells;
    }

    public static void setModel(ModelAdapter m) {
        model = m;
    }

    public String getSmellName() {
        return smellName;
    }

    void addToSmells(EASmell smell) {
        result.add(smell);
        smells.add(smell);
    }

    abstract public List<EASmell> detect();

}
