package de.example.smells;

import de.example.model.ElementType;
import de.example.model.RelationshipType;

import java.util.List;
import java.util.Set;

import static de.example.smells.Constants.MODULARITY_RATIO;

public class WeakenedModularity extends Detector {

    public WeakenedModularity() {
        super("Weakened Modularity");
    }

//    public List<EASmell> detect() {
//        List<ElementType> elements = model.getElements();
//        for (ElementType element : elements) {
//            //String type = element.getClass().getSimpleName();
//            //if (!(type.contains("Process") || type.contains("Event") || type.contains("Function"))) {
//            String[] internalReferencedElements = {"Composition", "Aggregation", "Realization"};
//            String[] internalElementsWithReference = {"Assignment"};
//            int internal = model.getReferencedElementsOf(element, internalReferencedElements).size() + model.getElementsWithReferenceTo(element, internalElementsWithReference).size();
//            String[] externalReferencedElements = {"Influence", "Access", "Serving", "Triggering", "Flow", "Association"};
//            String[] externalElementsWithReference = {"Composition", "Triggering"};
//            int external = model.getElementsWithReferenceTo(element, externalReferencedElements).size() + model.getReferencedElementsOf(element, externalReferencedElements).size();
//            double mr = (double) internal / (double) external;
//            if (mr < MODULARITY_RATIO && mr > 0) {
//                addToSmells(new EASmell("Weakened Modularity", element, " with a modularity ratio of " + mr));
//            }
//            //}
//        }
//        return result;
//    }

    // cluster scope
    public List<EASmell> detect() {
        for (ElementType element : model.getElements()) {
            Set<ElementType> cluster = model.getCluster(element);
            int internal = 0;
            int external = 0;
            for (RelationshipType relationship : model.getRelationships()) {
                ElementType target = (ElementType) relationship.getTarget();
                ElementType source = (ElementType) relationship.getSource();
                if (cluster.contains(source) && cluster.contains(target)) {
                    internal++;
                } else if (isNotStructural(relationship) && ((cluster.contains(source) && !cluster.contains(target)) || (cluster.contains(target) && !cluster.contains(source)))) {
                    external++;
                }
            }
//            String[] externalReferencedElements = {"Influence", "Access", "Serving", "Triggering", "Flow", "Association"};
//            int external = model.getElementsWithReferenceTo(element, externalReferencedElements).size() + model.getReferencedElementsOf(element, externalReferencedElements).size();
            double mr = (double) internal / (double) external;
            if (mr < MODULARITY_RATIO && internal > 3) {
                addToSmells(new EASmell("Weakened Modularity", element, " with a modularity ratio of " + mr));
            }
        }
        return result;
    }

    private boolean isNotStructural(RelationshipType relationship) {
        String type = relationship.getClass().getSimpleName();
        return !type.contains("Realization") && !type.contains("Assignment") && !type.contains("Aggregation") && !type.contains("Composition");
    }

}
