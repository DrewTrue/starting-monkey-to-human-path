package RPIS61.Gubanov.wdad.learn.xml;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class TestXmlTask {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {
//        File file = new File("C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path" +
//                "\\src\\RPIS61\\Gubanov\\wdad\\learn\\xml\\test1.xml");
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
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2018, Calendar.NOVEMBER, 22);
//        System.out.println(xmlTask.earningsTotal("John","Stones", calendar));
//        xmlTask.changeOfficiantName("John", "Stones", "Kate", "Stones");
//        xmlTask.removeDay(calendar);
    }
}
