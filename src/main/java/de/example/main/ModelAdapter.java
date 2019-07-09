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

    public ModelAdapter(String xmlFile, String xsdSchema) {
        try {
            model = JAXBMarshalUnmarshal.unmarshal(xmlFile, ModelType.class, xsdSchema);
            System.out.println("Successfully loaded " + xmlFile);
        } catch (JAXBException | SAXException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    public ModelType getModel() {
        return model;
    }

    public List<ElementType> getElements() {
        return model.getElements().getElement();
    }

    public ElementType getElementByIdentifier(String id) {
        List<ElementType> elements = model.getElements().getElement().stream().filter(e -> e.getIdentifier().equals(id)).collect(Collectors.toList());
        if (elements.isEmpty()) {
            return null;
        } else {
            return elements.get(0);
        }
    }

    public List<ElementType> getElementsWithReferenceTo(ElementType target) {
        List<ElementType> res = new ArrayList<>();
        for (RelationshipType rel : getRelationships()) {
            ElementType tar = (ElementType) rel.getTarget();
            if (tar.equals(target)) {
                ElementType sour = (ElementType) rel.getSource();
                res.add(sour);
            }
        }
        return res;
    }

    public List<ElementType> getReferencedElementsOf(ElementType source) {
        List<ElementType> res = new ArrayList<>();
        for (RelationshipType rel : getRelationships()) {
            ElementType sour = (ElementType) rel.getSource();
            if (sour.equals(source)) {
                ElementType tar = (ElementType) rel.getTarget();
                res.add(tar);
            }
        }
        return res;
    }

    public List<ElementType> getReferencedElementsOf(ElementType source, String[] types) {
        List<ElementType> res = new ArrayList<>();
        for (RelationshipType rel : getRelationships()) {
            ElementType sour = (ElementType) rel.getSource();
            if (sour.equals(source)) {
                for (String type : types) {
                    if (rel.getClass().getName().equals("de.example.model." + type)) {
                        ElementType tar = (ElementType) rel.getTarget();
                        res.add(tar);
                    }
                }
            }
        }
        return res;
    }

    public List<ElementType> getElementsWithReferenceTo(ElementType target, String[] types) {
        List<ElementType> res = new ArrayList<>();
        for (RelationshipType rel : getRelationships()) {
            ElementType tar = (ElementType) rel.getTarget();
            if (tar.equals(target)) {
                for (String type : types) {
                    if (rel.getClass().getName().equals("de.example.model." + type)) {
                        ElementType sour = (ElementType) rel.getSource();
                        res.add(sour);
                    }
                }
            }
        }
        return res;
    }

    public List<PropertyDefinitionType> getPropertyDefinitions() {
        return model.getPropertyDefinitions().getPropertyDefinition();
    }

    public MetadataType getMetadata() {
        return model.getMetadata();
    }

    public List<PropertyType> getProperties() {
        return model.getProperties().getProperty();
    }

    public List<RelationshipType> getRelationships() {
        return model.getRelationships().getRelationship();
    }

    public String getVersion() {
        return model.getVersion();
    }

    public List<Object> getAny() {
        return model.getAny();
    }

    public List<PreservedLangStringType> getDocumentation() {
        return model.getDocumentation();
    }

    public String getIdentifier() {
        return model.getIdentifier();
    }

    public Map<QName, String> getOtherAttributes() {
        return model.getOtherAttributes();
    }

    public String getNameGroup() {
        StringBuilder res = new StringBuilder();
        for (LangStringType langString : model.getNameGroup()) {
            res.append(langString.getLang()).append(": ").append(langString.getValue()).append("\n");
        }
        return res.toString();
    }

    public List<ElementType> getElementsInLayer(String layer) {
        List<OrganizationType> l = model.getOrganizations().get(0).getItem().stream().filter(e -> e.getLabelGroup().get(0).getValue().toLowerCase().contains(layer.toLowerCase())).collect(Collectors.toList());
        if (l.isEmpty()) {
            return new ArrayList<>();
        } else if (l.get(0).getItem().isEmpty()) {
            return new ArrayList<>();
        } else {
            l = l.get(0).getItem();
            List<ElementType> res = new ArrayList<>();
            for (OrganizationType element : l) {
                ElementType tmp = (ElementType) element.getIdentifierRef();
                res.add(tmp);
            }
            return res;
        }
    }

    public List<Diagram> getViews() {
        return model.getViews().getDiagrams().getView();
    }

}
