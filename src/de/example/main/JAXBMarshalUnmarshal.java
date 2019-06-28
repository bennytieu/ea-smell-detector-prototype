package de.example.main;

import de.example.model.ElementType;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class JAXBMarshalUnmarshal {

    // mainly used for testing
    public static void main(String[] args) {
        ModelAdapter model = new ModelAdapter("CentralModel.xml", null);
        ElementType element = model.getElementByIdentifier("id-5e50603d-bd28-49ef-91e7-5db25b447f6a");
        System.out.println(element.getIdentifier());
        System.out.println(model.getViews().get(0).getNameGroup().get(0).getValue());
        //marshal(model.getModel(), "Test.xml", "archimate3_Diagram.xsd");
    }

    /**
     * Unmarshalling of provided XML-File
     *
     * @param xmlFile   The XML-File to be unmarshalled
     * @param c         The corresponding root class
     * @param xsdSchema XSD-Schema for optional validation
     * @param <T>       The type of the root class
     * @return The corresponding object to the unmarshalled XML-File
     * @throws JAXBException for unmarshalling error
     * @throws SAXException  for schema error
     */
    static <T> T unmarshal(String xmlFile, Class<T> c, String xsdSchema) throws JAXBException, SAXException {
        System.out.println("Starting unmarshalling of \"" + xmlFile + "\" ...");
        JAXBContext jc = JAXBContext.newInstance(c.getPackage().getName());
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Schema schema = createSchema(xsdSchema);
        unmarshaller.setSchema(schema);
        return unmarshaller.unmarshal(new StreamSource(new File(xmlFile)), c).getValue();
    }

    /**
     * Marshalling of provided Object to specified XML-File
     *
     * @param jaxbElement The object to be marshalled
     * @param xmlFile     The output XML-File
     * @param xsdSchema   XSD-Schema for optional validation
     * @throws JAXBException for marshalling error
     * @throws SAXException  for schema error
     */
    static void marshal(Object jaxbElement, String xmlFile, String xsdSchema) throws JAXBException, SAXException {
        System.out.println("Starting marshalling of \"" + jaxbElement.getClass().getPackage().getName() + "\" ...");
        JAXBContext jc = JAXBContext.newInstance(jaxbElement.getClass().getPackage().getName());
        Marshaller marshaller = jc.createMarshaller();
        Schema schema = createSchema(xsdSchema);
        marshaller.setSchema(schema);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(jaxbElement, new File(xmlFile));
    }

    /*Schema schema = (xsdSchema == null || xsdSchema.trim().length() == 0)
                ? null : schemaFactory.newSchema(new File(xsdSchema));*/
    private static Schema createSchema(String xsdSchema) throws SAXException {
        if (xsdSchema == null || xsdSchema.trim().length() == 0) {
            return null;
        } else {
            System.out.println("Validating against \"" + xsdSchema + "\" ...");
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            return schemaFactory.newSchema(new File(xsdSchema));
        }
    }
}
