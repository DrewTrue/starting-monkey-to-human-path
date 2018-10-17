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
//        File file = new File("C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path" +
//                "\\src\\RPIS61\\Gubanov\\wdad\\learnxml\\test2.xml");
//        XmlTask xmlTask = new XmlTask(file);
        XmlTask xmlTask = new XmlTask("test1");
        xmlTask.buildingXmlDocument();
        xmlTask.addDate();
        xmlTask.addAnOrder();
        xmlTask.addAnOfficiant("John", "Stones");
        xmlTask.addAnItem("Chicken wings", "5", "3");
        xmlTask.addAnItem("Cola", "2", "2");
        xmlTask.addTotalCost();
        xmlTask.addAnOrder();
        xmlTask.addAnOfficiant("John", "Stones");
        xmlTask.addAnItem("Chicken wings", "5", "30");
        xmlTask.addAnItem("Cola", "2", "12");
        xmlTask.addTotalCost();
        xmlTask.setTagAtribute("restaurant", "name", "McBurgerC");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.OCTOBER, 16);
        System.out.println(xmlTask.earningsTotal("John","Stones", calendar));
//        xmlTask.changeOfficiantName("John", "Stones", "Kate", "Stones");
//        xmlTask.removeDay(calendar);
        xmlTask.transformer();

//        File file = new File("C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path" +
//                "\\src\\RPIS61\\Gubanov\\wdad\\learnxml\\test1.xml");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2018, Calendar.OCTOBER, 16);
//        XmlTask xmlTask = new XmlTask(file);
////        System.out.println(xmlTask.getDocument().getDocumentElement().getElementsByTagName("date").item(0).getFirstChild());
////        System.out.println(xmlTask.getDocument().getDocumentElement().getFirstChild().getFirstChild().getLastChild().getNodeName());
//        System.out.println(xmlTask.earningsTotal("John","Stones", calendar));
////        xmlTask.changeOfficiantName("John", "Stones", "Kate", "Stones");
////        xmlTask.removeDay(calendar);
////        xmlTask.transformer();
    }
}
