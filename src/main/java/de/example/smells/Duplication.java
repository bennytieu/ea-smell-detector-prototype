package de.example.smells;

import de.example.model.ElementType;

import java.util.List;

import static de.example.smells.Constants.DUPLICATED_WORDS_RATIO;

public class Duplication extends Detector {

    public Duplication() {
        super("Duplication");
    }

    public List<EASmell> detect() {
        List<ElementType> elements = model.getElements();
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                // only if same element type
                if (elements.get(i).getClass().getSimpleName().equals(elements.get(j).getClass().getSimpleName())) {
                    String[] ei = elements.get(i).getNameGroup().get(0).getValue().split(" ");
                    String[] ej = elements.get(j).getNameGroup().get(0).getValue().split(" ");
                    int duplicatedWords = 0;
                    for (String w : ej) {
                        if (elements.get(i).getNameGroup().get(0).getValue().contains(w)) {
                            duplicatedWords++;
                        }
                    }
//                    if (duplicatedWords > DUPLICATED_WORDS) {
//                        addToSmells(new EASmell(getSmellName(), elements.get(i), " together with" + " \"" + elements.get(j).getNameGroup().get(0).getValue() + "\" (" + elements.get(j).getIdentifier() + ")"));
//                    }
                    if (((double) 2 * duplicatedWords) / (ei.length + ej.length) > DUPLICATED_WORDS_RATIO) {
                        addToSmells(new EASmell(getSmellName(), elements.get(i), " together with" + " \"" + elements.get(j).getNameGroup().get(0).getValue() + "\" (" + elements.get(j).getIdentifier() + ")"));
                    }
                }
            }
        }
        return result;
    }
}
