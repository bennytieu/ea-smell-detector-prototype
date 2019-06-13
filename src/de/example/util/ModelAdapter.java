package de.example.util;

import de.example.model.ElementType;
import de.example.model.ModelType;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class
 * maybe this functionality should be moved to ModelType Class instead, but this would alter a class generated from the xsd-schema
 */
public class ModelAdapter {

    public static void main(String[] args) throws JAXBException, SAXException {
        ModelType model = JAXBMarshalUnmarshal.unmarshal("CentralModel.xml", ModelType.class, null);
        System.out.println(model.getElements().getElement().get(0).getIdentifier());
        ElementType element = getElementByIdentifier(model, "id-5e50603d-bd28-49ef-91e7-5db25b447f6a");
        System.out.println(element.getIdentifier());
        //marshal(model, "Test.xml", "archimate3_Diagram.xsd");
    }

    /**
     * Get the corresponding element to an identifier
     *
     * @param model A model conformal to the underlying structure
     * @param id    Identifier to search for
     * @return The element with the identifier, otherwise null
     */
    public static ElementType getElementByIdentifier(ModelType model, String id) {
        List<ElementType> elements = model.getElements().getElement().stream().filter(e -> e.getIdentifier().equals(id)).collect(Collectors.toList());
        if (elements.isEmpty()) {
            return null;
        } else {
            return elements.get(0);
        }
    }

}
