package de.example.smells;

import de.example.model.ElementType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeadComponent extends Detector {

    public DeadComponent() {
        super("Dead Component");
    }

    public List<EASmell> detect() {
        List<ElementType> elements = model.getElements();
        for (ElementType element : elements) {
            // no incoming and outgoing relations
            if (model.getReferencedElementsOf(element).isEmpty() && model.getElementsWithReferenceTo(element).isEmpty()) {
                addToSmells(new EASmell(getSmellName(), element));
            }
        }
        return result;
    }

//    public List<EASmell> detect() {
//        List<ElementType> elements = model.getElements();
//        for (ElementType element : elements) {
//            for (ElementType root : getPossibleRoots(element)) {
//                System.out.println(element.getNameGroup().get(0).getValue() + ": " + root.getNameGroup().get(0).getValue());
//                // element or its root element have no incoming and outgoing relations (except structural ones)
//                String[] relationships = {"Influence", "Access", "Serving", "Triggering", "Flow", "Specialization", "Association", "Realization", "Composition"};
//                if (model.getElementsWithReferenceTo(root, relationships).isEmpty() && model.getReferencedElementsOf(root, relationships).isEmpty()) {
//                    addToSmells(new EASmell(getSmellName(), element));
//                }
//            }
//        }
//        return result;
//    }

    private Set<ElementType> getPossibleRoots(ElementType element) {
        Set<ElementType> elementAsSet = new HashSet<>();
        elementAsSet.add(element);
        List<ElementType> businessElements = model.getElementsInLayer("Business");
        if (businessElements.contains(element)) {
            return getPossibleRoots(elementAsSet, businessElements);
        } else {
            List<ElementType> applicationElements = model.getElementsInLayer("Application");
            if (applicationElements.contains(element)) {
                return getPossibleRoots(elementAsSet, applicationElements);
            } else {
                List<ElementType> technologyElements = model.getElementsInLayer("Technology");
                if (technologyElements.contains(element)) {
                    return getPossibleRoots(elementAsSet, technologyElements);
                }
                return new HashSet<>();
            }
        }
    }

    private Set<ElementType> getPossibleRoots(Set<ElementType> elements, List<ElementType> elementsInSameLayer) {
        Set<ElementType> res = new HashSet<>();
        String[] structuralRelationsIn = {"Aggregation", "Realization", "Composition"};
        String[] structuralRelationsOut = {"Assignment"};
        for (ElementType element : elements) {
            Set<ElementType> parents = new HashSet<>(model.getElementsWithReferenceTo(element, structuralRelationsIn));
            parents.addAll(model.getReferencedElementsOf(element, structuralRelationsOut));
            // filter parents to only contain elements of same layer
            parents = parents.stream().filter(elementsInSameLayer::contains).collect(Collectors.toSet());
            if (parents.isEmpty()) {
                res.add(element);
            } else {
                res.addAll(getPossibleRoots(parents, elementsInSameLayer));
            }
        }
        return res;
    }
}
