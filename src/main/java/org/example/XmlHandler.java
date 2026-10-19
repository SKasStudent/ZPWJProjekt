package org.example;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.*;

public class XmlHandler {
    public void createXml(List<Wood> woodList, Path XmlPath) {
        try{
            OutputStream out = new FileOutputStream(XmlPath.toFile());
            XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(out);

            streamWriter.writeStartDocument("UTF-8","1.0");
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

    public List<Wood> readXml(Path XmlPath){
        List<Wood> woodList = new ArrayList<>();
        try {
            InputStream in = new FileInputStream(XmlPath.toFile());
            XMLInputFactory inputFactory = XMLInputFactory.newFactory();
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);

            Map<String,String> fields = null;

            while(streamReader.hasNext()){
                int event = streamReader.next();
                switch(event){
                    case XMLStreamConstants.START_ELEMENT:
                        String name = streamReader.getLocalName();
                        if(name.equals("Wood")){
                            fields = new HashMap<>();
                        } else if(fields != null) {
                            fields.put(name, streamReader.getElementText().trim());
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        name = streamReader.getLocalName();
                        if(name.equals("Wood")){
                            try {
                                woodList.add(createWoodObject(fields));
                            }catch(LackingWoodDataException e){
                                System.out.println("Error while creating wood object: " + e.getMessage());
                            }
                            fields = null;
                        }
                }
            }

            streamReader.close();
            in.close();

        } catch(Exception e){
            e.printStackTrace();
        }
        return woodList;
    }

    public Wood createWoodObject(Map<String,String> fields) throws LackingWoodDataException{
        String AvailableStockText = fields.get("AvailableStock");
        if(AvailableStockText==null){
            throw new LackingWoodDataException("AvailableStock field invalid - null");
        }

        double AvailableStock;
        try{
            AvailableStock = Double.parseDouble(AvailableStockText);
        }catch(NumberFormatException e){
            throw new LackingWoodDataException("AvailableStock field invalid - not a number");
        }

        return new Wood(fields.get("Species"), fields.get("Type"), fields.get("Origin"), fields.get("Color"), AvailableStock);
    }
}
