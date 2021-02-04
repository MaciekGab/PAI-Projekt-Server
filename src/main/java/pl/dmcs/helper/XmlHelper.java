package pl.dmcs.helper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class XmlHelper {

    public static Object fromXml(Class<?> class_, String fileName) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(class_);
        return context.createUnmarshaller()
                .unmarshal(new FileReader(fileName));
    }

    public static void toXml(Object obj, String fileName) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(obj, new File(fileName));
    }

}
