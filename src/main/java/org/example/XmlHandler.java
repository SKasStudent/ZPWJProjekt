package org.example;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import javax.xml.stream.*;

public class XmlHandler {
    public void createXml(List<Wood> woodList) {
        try{
            OutputStream out = new FileOutputStream("WoodCatalogue.xml");
            XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(out);

            streamWriter.writeStartDocument("UTF-8");
            streamWriter.writeStartElement("WoodCatalogue");

            woodList.forEach(wood -> {
                try {
                    streamWriter.writeStartElement("Wood");
                    streamWriter.writeStartElement("Species");
                    streamWriter.writeCharacters(wood.getSpecies());
                    streamWriter.writeEndElement();
                    streamWriter.writeStartElement("Type");
                    streamWriter.writeCharacters(wood.getType());
                    streamWriter.writeEndElement();
                    streamWriter.writeStartElement("Origin");
                    streamWriter.writeCharacters(wood.getOrigin());
                    streamWriter.writeEndElement();
                    streamWriter.writeStartElement("Color");
                    streamWriter.writeCharacters(wood.getColor());
                    streamWriter.writeEndElement();
                    streamWriter.writeStartElement("AvailableStock");
                    streamWriter.writeCharacters(wood.getAvailableStock().toString());
                    streamWriter.writeEndElement();
                    streamWriter.writeEndElement();
                } catch (XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            });

            streamWriter.writeEndElement();
            streamWriter.writeEndDocument();
            streamWriter.flush();
            streamWriter.close();
            out.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void readXml(Path XmlPath){

        try {
            InputStream in = new FileInputStream(XmlPath.toString());
            XMLInputFactory inputFactory = XMLInputFactory.newFactory();
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);

            while(streamReader.hasNext()){
                int event = streamReader.next();
                switch(event){
                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println("<==" + streamReader.getLocalName() + "==>");
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        System.out.println("-" + streamReader.getText().trim());
                        break;
                }
            }

            streamReader.close();
            in.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
