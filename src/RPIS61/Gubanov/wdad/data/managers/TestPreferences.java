package RPIS61.Gubanov.wdad.data.managers;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class TestPreferences {
    public static void main(String[] args) throws TransformerException, IOException, SAXException, ParserConfigurationException {
        PreferencesManagers managers = PreferencesManagers.getInstance();
//        managers.setClassProvider("classprovider");
//        System.out.println(managers.getClassProvider());
        managers.addBlindObject("asd", "asd");
        managers.addBlindObject("asasdd", "asda");
        managers.removeBlindObject("asd");
    }
}
