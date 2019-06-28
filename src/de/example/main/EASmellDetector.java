package de.example.main;

import de.example.smells.DenseStructure;
import de.example.smells.Detector;
import de.example.smells.EASmell;
import de.example.smells.WeakenedModularity;

import java.util.List;

public class EASmellDetector {
    public static void main(String[] args) {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);

        DenseStructure denseStructure = new DenseStructure(model);
        WeakenedModularity weakenedModularity = new WeakenedModularity(model);
        weakenedModularity.detect();
        denseStructure.detect();
        List<EASmell> res = Detector.getSmells();
        System.out.println(res.get(0).getSmellName() + "\n" + res.get(1).getSmellName());
    }
}
