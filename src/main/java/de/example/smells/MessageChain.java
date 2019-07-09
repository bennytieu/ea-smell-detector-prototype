package de.example.smells;

import de.example.model.ElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.example.smells.Constants.MAX_SERVICE_CHAIN_LENGTH;

public class MessageChain extends Detector {

    public MessageChain() {
        super("Message Chain");
    }

    public List<EASmell> detect() {
        List<ElementType> elements = model.getElements().stream().filter(e -> e.getClass().getSimpleName().contains("Service")).collect(Collectors.toList());
        //remove cycles
        for (ElementType element : elements) {
            List<ElementType> current = new ArrayList<>();
            current.add(element);
            List<ElementType> chain = getServiceChain(element, current);
            if (chain.size() > MAX_SERVICE_CHAIN_LENGTH) {
                StringBuilder elementsInChain = new StringBuilder();
                for (ElementType e : chain) {
                    elementsInChain.append(e.getNameGroup().get(0).getValue());
                    elementsInChain.append(", ");
                }
                addToSmells(new EASmell(getSmellName(), element, " with a chain length of " + chain.size() + " and the elements: " + elementsInChain));
            }
        }
        return result;
    }

    private List<ElementType> getServiceChain(ElementType start, List<ElementType> current) {
        List<ElementType> referencedServiceElements = model.getReferencedElementsOf(start).stream().filter(e -> e.getClass().getSimpleName().contains("Service")).collect(Collectors.toList());
        List<ElementType> res = new ArrayList<>();
        if (referencedServiceElements.isEmpty()) {
            res.add(start);
            return res;
        } else {
            for (ElementType element : referencedServiceElements) {
                if (current.contains(element)) {
                    // loop in chain
                    break;
                }
                current.add(element);
                List<ElementType> additionalElements = getServiceChain(element, current);
                current.remove(element);
                if (additionalElements.size() > res.size()) {
                    res = additionalElements;
                }
            }
            res.add(0, start);
            return res;
        }
    }
}
