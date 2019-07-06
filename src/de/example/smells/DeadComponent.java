package de.example.smells;

import de.example.model.ElementType;

import java.util.List;

public class DeadComponent extends Detector {

    public DeadComponent() {
        super("Dead Component");
    }

    public List<EASmell> detect() {
        List<ElementType> elements = model.getElements();
        for (ElementType element : elements) {
            if (model.getReferencedElementsOf(element).isEmpty() && model.getElementsWithReferenceTo(element).isEmpty()) {
                addToSmells(new EASmell(getSmellName(), element));
            }
        }
        return result;
    }
}
