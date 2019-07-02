package de.example.main;

import de.example.smells.*;

import java.util.List;

public class EASmellDetector {
    public static void main(String[] args) {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);

        DenseStructure denseStructure = new DenseStructure(model);
        WeakenedModularity weakenedModularity = new WeakenedModularity(model);
        HubLikeModularization hubLikeModularization = new HubLikeModularization(model);
        weakenedModularity.detect();
        denseStructure.detect();
        hubLikeModularization.detect();
        List<EASmell> smells = Detector.getSmells();
        for (EASmell smell : smells) {
            System.out.println(smell.toString());
        }
    }
}
