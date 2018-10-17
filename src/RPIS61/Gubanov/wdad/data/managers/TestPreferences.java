package RPIS61.Gubanov.wdad.data.managers;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class TestPreferences {
    public static void main(String[] args) throws TransformerException, IOException, SAXException, ParserConfigurationException {
        PreferencesManagers managers = PreferencesManagers.getInstance();
        managers.setElement("server", "asd");
        managers.addBlindObject("asd", "asd");
        managers.addBlindObject("asasdd", "asda");
        managers.addRegistry("no","asd","sa");
        managers.transformer();
    }
}
