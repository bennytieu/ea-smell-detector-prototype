package de.example.main;

import de.example.smells.*;

import java.util.ArrayList;
import java.util.List;

public class EASmellDetector {
    public static void main(String[] args) {
        ModelAdapter model = new ModelAdapter("Test.xml", null);
        Detector.setModel(model);

        List<Detector> detectors = new ArrayList<>();
        detectors.add(new CyclicDependency());
        detectors.add(new DenseStructure());
        detectors.add(new WeakenedModularity());
        detectors.add(new HubLikeModularization());

        System.out.println("\n");
        for (Detector detector : detectors) {
            System.out.println("Start detection of " + detector.getSmellName() + " ...");
            printSmells(detector.detect());
            System.out.println("Finished detection of " + detector.getSmellName() + "\n");
        }

        printSmells(Detector.getSmells());
    }

    private static void printSmells(List<EASmell> smells) {
        for (EASmell smell : smells) {
            System.out.println(smell.toString());
        }
    }
}
