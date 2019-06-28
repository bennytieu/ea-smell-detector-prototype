package de.example.main;

import de.example.model.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelAdapter {

    private static ModelType model;

    ModelAdapter(String xmlFile, String xsdSchema) {
        try {
            model = JAXBMarshalUnmarshal.unmarshal(xmlFile, ModelType.class, xsdSchema);
            System.out.println("Successfully loaded " + xmlFile);
        } catch (JAXBException | SAXException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    // for testing
    public static void main(String[] args) throws JAXBException, SAXException {
        model = JAXBMarshalUnmarshal.unmarshal("CentralModel.xml", ModelType.class, null);
        System.out.println(getIdentifiersInLayer("application"));
    }

    public static ModelType getModel() {
        return model;
    }

    public static List<ElementType> getElements() {
        return model.getElements().getElement();
    }

    public static ElementType getElementByIdentifier(String id) {
        List<ElementType> elements = model.getElements().getElement().stream().filter(e -> e.getIdentifier().equals(id)).collect(Collectors.toList());
        if (elements.isEmpty()) {
            return null;
        } else {
            return elements.get(0);
        }
    }

    public static List<PropertyDefinitionType> getPropertyDefinitions() {
        return model.getPropertyDefinitions().getPropertyDefinition();
    }

    public static MetadataType getMetadata() {
        return model.getMetadata();
    }

    public static List<PropertyType> getProperties() {
        return model.getProperties().getProperty();
    }

    public static List<RelationshipType> getRelationships() {
        return model.getRelationships().getRelationship();
    }

    public static String getVersion() {
        return model.getVersion();
    }

    public static List<Object> getAny() {
        return model.getAny();
    }

    public static List<PreservedLangStringType> getDocumentation() {
        return model.getDocumentation();
    }

    public static String getIdentifier() {
        return model.getIdentifier();
    }

    public static Map<QName, String> getOtherAttributes() {
        return model.getOtherAttributes();
    }

    public static String getNameGroup() {
        StringBuilder res = new StringBuilder();
        for (LangStringType langString : model.getNameGroup()) {
            res.append(langString.getLang()).append(": ").append(langString.getValue()).append("\n");
        }
        return res.toString();
    }

    public static List<String> getIdentifiersInLayer(String layer) {
        List<OrganizationType> l = model.getOrganizations().get(0).getItem().stream().filter(e -> e.getLabelGroup().get(0).getValue().toLowerCase().contains(layer.toLowerCase())).collect(Collectors.toList());
        if (l.isEmpty()) {
            return null;
        } else {
            l = l.get(0).getItem();
            List<String> res = new ArrayList<>();
            for (OrganizationType element : l) {
                RealElementType tmp = (RealElementType) element.getIdentifierRef();
                res.add(tmp.getIdentifier());
            }
            return res;
        }
    }

}
