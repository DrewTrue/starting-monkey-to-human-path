package RPIS61.Gubanov.wdad.learnxml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

public class TestXmlTask {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        XmlTask xmlTask = new XmlTask("test1");
        xmlTask.buildingXmlDocument();
        xmlTask.addDate();
        xmlTask.addAnOrder();
        xmlTask.addAnOfficiant("John", "Stones");
        xmlTask.addAnItem("Chicken wings", "5", "3");
        xmlTask.addAnItem("Cola", "2", "2");
        xmlTask.addTotalCost();
        xmlTask.setTagAtribute("restaurant", "name", "McBurgerC");
        xmlTask.transformer();

        System.out.println(xmlTask.getDocument().getElementsByTagName("officiant")
                .item(0).getAttributes().item(0).getNodeValue());
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.MONTH) + 1);
    }
}
