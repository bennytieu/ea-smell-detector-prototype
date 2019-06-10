package de.example.util;

import de.example.model.ElementType;
import de.example.model.ModelType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class
 * maybe this functionality should be moved to ModelType Class instead, but this would alter a class generated from the xsd-schema
 */
public class Util {

    /**
     * Get the corresponding element to an identifier
     *
     * @param model A model conformal to the underlying structure
     * @param id    Identifier to search for
     * @return The element with the identifier, otherwise null
     */
    public ElementType getElementByIdentifier(ModelType model, String id) {
        List<ElementType> elements = model.getElements().getElement().stream().filter(e -> e.getIdentifier().equals(id)).collect(Collectors.toList());
        if (elements.isEmpty()) {
            return null;
        } else {
            return elements.get(0);
        }
    }

}
