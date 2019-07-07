package de.example.smells;

import de.example.model.ElementType;

import java.util.List;

import static de.example.smells.Constants.MODULARITY_RATIO;

public class WeakenedModularity extends Detector {

    public WeakenedModularity() {
        super("Weakened Modularity");
    }

    public List<EASmell> detect() {
        List<ElementType> elements = model.getElements();
        for (ElementType element : elements) {
            //String type = element.getClass().getSimpleName();
            //if (!(type.contains("Process") || type.contains("Event") || type.contains("Function"))) {
            int internal = model.getReferencedElementsOf(element, "Composition").size() + model.getReferencedElementsOf(element, "Aggregation").size() + model.getElementsWithReferenceTo(element, "Aggregation").size();
            int external = model.getElementsWithReferenceTo(element, "Composition").size() + model.getElementsWithReferenceTo(element, "Triggering").size() + model.getReferencedElementsOf(element, "Triggering").size();
            double mr = (double) internal / (double) external;
            if (mr < MODULARITY_RATIO && mr > 0) {
                addToSmells(new EASmell("Weakened Modularity", element, " with a modularity ratio of " + mr));
            }
            //}
        }
        return result;
    }
}
