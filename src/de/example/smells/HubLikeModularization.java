package de.example.smells;

import de.example.main.ModelAdapter;
import de.example.model.ElementType;

import java.util.List;

import static de.example.smells.Constants.LARGE_FAN_IN;
import static de.example.smells.Constants.LARGE_FAN_OUT;

public class HubLikeModularization extends Detector {

    public HubLikeModularization(ModelAdapter model) {
        super(model);
    }

    public List<EASmell> detect() {
        List<ElementType> elements = model.getElements();
        for (ElementType element : elements) {
            int fanIn = model.getElementsWithReferenceTo(element).size();
            int fanOut = model.getReferencedElementsOf(element).size();
            if (fanIn > LARGE_FAN_IN && fanOut > LARGE_FAN_OUT) {
                EASmell hlm = new EASmell("Hub-like Modularization", element);
                hlm.setContext(" with a total of " + (fanIn + fanOut) + " relations");
                addToSmells(hlm);
            }
        }
        return smells;
    }
}
