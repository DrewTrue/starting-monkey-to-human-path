package RPIS61.Gubanov.wdad.data.managers;

import RPIS61.Gubanov.wdad.learn.xml.Officiant;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Calendar;

public class Test {
    public static void main(String[] args) throws TransformerException, IOException, SAXException, ParserConfigurationException {
        PreferencesManager managers = PreferencesManager.getInstance();
//        managers.setClassProvider("classprovider");
//        System.out.println(managers.getClassProvider());
//        managers.addBindObject("asd", "asd");
//        managers.addBindObject("asasdd", "asda");
//        managers.removeBindObject("asd");
        JDBCDataManager dataManager = new JDBCDataManager();
        dataManager.earningsTotal(new Officiant("a", "a"), Calendar.getInstance());
    }
}
