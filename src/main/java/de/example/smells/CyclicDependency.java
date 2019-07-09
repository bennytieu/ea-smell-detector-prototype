package de.example.smells;

import de.example.model.ElementType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CyclicDependency extends Detector {

    public CyclicDependency() {
        super("Cyclic Dependency");
    }

    public List<EASmell> detect() {
        List<ElementType> elements = model.getElements();
        for (ElementType element : elements) {
            detectCyclicDependency(element);
        }
        return result;
    }

    private void detectCyclicDependency(ElementType element) {
        int currentSize = 0;
        Set<ElementType> referencedElements = new HashSet<>(model.getReferencedElementsOf(element));
        while (referencedElements.size() > currentSize) {
            if (referencedElements.contains(element)) {
                addToSmells(new EASmell(getSmellName(), element));
                break;
            }
            currentSize = referencedElements.size();
            Set<ElementType> additionalElements = new HashSet<>();
            for (ElementType e : referencedElements) {
                additionalElements.addAll(model.getReferencedElementsOf(e));
            }
            referencedElements.addAll(additionalElements);
        }
    }
}
