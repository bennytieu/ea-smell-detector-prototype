package de.example.smells;

import de.example.main.ModelAdapter;
import de.example.model.ElementType;
import de.example.model.RelationshipType;

import java.util.ArrayList;
import java.util.List;

import static de.example.smells.Constants.MODULARITY_RATIO;

public class WeakenedModularity extends Detector {

    public WeakenedModularity(ModelAdapter model) {
        super(model);
    }

    public List<EASmell> detect() {
        List<ElementType> elements = model.getElements();
        for (ElementType element : elements) {
            //String type = element.getClass().getSimpleName();
            //if (!(type.contains("Process") || type.contains("Event") || type.contains("Function"))) {
            int internal = getReferencedElementsOf(element, "Composition").size() + getReferencedElementsOf(element, "Aggregation").size();
            int external = getElementsWithReferenceTo(element, "Composition").size() + getElementsWithReferenceTo(element, "Triggering").size() + getReferencedElementsOf(element, "Triggering").size();
            double mr = (double) internal / (double) external;
            if (mr < MODULARITY_RATIO && mr > 0) {
                EASmell wm = new EASmell("Weakened Modularity", element);
                wm.setContext(" with a modularity ratio of " + mr);
                addToSmells(wm);
            }
            //}
        }
        return smells;
    }

    private List<ElementType> getReferencedElementsOf(ElementType source, String type) {
        List<ElementType> res = new ArrayList<>();
        for (RelationshipType rel : model.getRelationships()) {
            ElementType sour = (ElementType) rel.getSource();
            if (sour.equals(source) && rel.getClass().getName().equals("de.example.model." + type)) {
                ElementType tar = (ElementType) rel.getTarget();
                res.add(tar);
            }
        }
        return res;
    }

    private List<ElementType> getElementsWithReferenceTo(ElementType target, String type) {
        List<ElementType> res = new ArrayList<>();
        for (RelationshipType rel : model.getRelationships()) {
            ElementType tar = (ElementType) rel.getTarget();
            if (tar.equals(target) && rel.getClass().getName().equals("de.example.model." + type)) {
                ElementType sour = (ElementType) rel.getSource();
                res.add(sour);
            }
        }
        return res;
    }
}
