package de.example.main;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

import de.example.model.*;
import java.io.File;

public class ModelUnmarshaller {
    /**
     * Unmarshalling of provided EA Model
     * @param args needs the EA Model as XML-File as input
     * @throws JAXBException
     */
    public static void main(String[] args) throws JAXBException {
        if( args.length != 1 ) {
            System.out.println( "\nPlease add the EA Model as XML-File, e.g.: \"CentralModel.xml\"" );
            return;
        }

        JAXBContext jc = JAXBContext.newInstance("de.example.model");
        Unmarshaller unmarshaller = jc.createUnmarshaller();

        //ModelType model = (ModelType) unmarshaller.unmarshal(new File("CentralModel.xml"));
        //JAXBElement<ModelType> model = unmarshaller.unmarshal(new StreamSource(new File("CentralModel.xml")), ModelType.class);
        ModelType model = unmarshaller.unmarshal(new StreamSource(new File(args[0])), ModelType.class).getValue();

        System.out.println(model.getElements().getElement().get(0).getIdentifier());
    }
}
