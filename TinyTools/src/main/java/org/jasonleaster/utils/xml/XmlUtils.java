package org.jasonleaster.utils.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.jasonleaster.utils.io.Resources;

/**
 * Author: jasonleaster
 * Date  : 2017/5/14
 * Email : jasonleaster@gmail.com
 */
public class XmlUtils {

    private static final Logger log = Logger.getLogger(XmlUtils.class);

    private static final XmlUtils instance = new XmlUtils();

    public XmlUtils() {
    }

    public static <T> T parseXmlFromFile(String xmlFilePath, Class<T> clazz) {

        InputStream xmlInputStream;
        try {
            xmlInputStream = Resources.getResourceAsStream(xmlFilePath);
            if (xmlFilePath == null || xmlFilePath.length() == 0) {
                log.info("The xml file is empty! XML file path: " + xmlFilePath);
                return null;
            }
        } catch (IOException e) {
            log.error("Can load xml file from the path: " + xmlFilePath);
            return null;
        }

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller u = context.createUnmarshaller();
            return (T) u.unmarshal(xmlInputStream);
        } catch (JAXBException e) {
            log.error("Failed to unmarshal the xml file. File Path: " + xmlFilePath);
            return null;
        }
    }

    public static void objectsConvertToXmlFile(Object obj, String xmlFilePath) {
        File outputFile;
        try {
            outputFile = Resources.createFileIfNotExist(xmlFilePath);
        }catch (IOException e) {
            log.error("! ==> Failed to create the file. File path: " + xmlFilePath);
            return;
        }

        try {
            JAXBContext jaxbContext   = JAXBContext.newInstance(obj.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(obj, outputFile);
        }catch (JAXBException e){
            log.error("Failed to convert the object into the file. File path: " + xmlFilePath);
        }
    }
}
