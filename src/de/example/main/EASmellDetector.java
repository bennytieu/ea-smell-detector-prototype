package de.example.main;

import de.example.smells.DenseStructure;
import de.example.smells.EASmell;

import java.util.List;

public class EASmellDetector {
    public static void main(String[] args) {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);

        DenseStructure denseStructure = new DenseStructure(model);
        List<EASmell> res = denseStructure.detect();
        System.out.println(res.get(0).getSmellName());
    }
}
