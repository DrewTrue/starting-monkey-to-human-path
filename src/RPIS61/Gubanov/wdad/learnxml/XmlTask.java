package RPIS61.Gubanov.wdad.learnxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Calendar;

public class XmlTask {
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private File file;

    public XmlTask(String fileName) throws ParserConfigurationException {
        this.factory = DocumentBuilderFactory.newInstance();
        this.builder = factory.newDocumentBuilder();
        this.file = new File("C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path" +
                "\\src\\RPIS61\\Gubanov\\wdad\\learnxml\\" + fileName +".xml");
        this.document = builder.newDocument();
    }

    public void buildingXmlDocument() {
        document.appendChild(document.createElement("restaurant"));
    }

    public void buildingXmlDocument(String rootElement) {
        document.appendChild(document.createElement(rootElement));
    }

    public void addTagElement(String elementName, String parentName) {
        Element child = document.createElement(elementName);
        document.getElementsByTagName(parentName)
                .item(document.getElementsByTagName(parentName).getLength() - 1).appendChild(child);
        child.appendChild(document.createTextNode(" \n"));
    }

    public void setTagAtribute(String tagName, String attrName, String attrValue) {
        Element element = (Element) document.getElementsByTagName(tagName)
                .item (document.getElementsByTagName(tagName).getLength() - 1);
        element.setAttribute(attrName, attrValue);
    }

    public void setTagText(String tagName, String text){
        Element element = (Element) document.getElementsByTagName(tagName)
                .item (document.getElementsByTagName(tagName).getLength() - 1);
        element.setTextContent('\n' + text + '\n');
    }

    public void transformer() throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
    }

    public void addAnOrder() {
        addTagElement("order", "date");
    }

    public void addAnItem(String name, String cost , String quantity) {
        addTagElement("item", "order");
        setTagAtribute("item", "name", name);
        setTagAtribute("item", "cost", cost);
        setTagText("item", quantity);
    }

    public void addAnOfficiant(String firstname, String lastname) {
        addTagElement("officiant", "order");
        setTagAtribute("officiant", "firstname", firstname);
        setTagAtribute("officiant", "lastname", lastname);
    }

    public void addDate() {
        addTagElement("date", "restaurant");
        setTagAtribute("date", "year", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        setTagAtribute("date", "month", String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
        setTagAtribute("date", "day", String.valueOf(Calendar.getInstance().get(Calendar.DATE)));
    }

    public int countingTotalCostPerOrder() {
        int totalCostPerOrder = 0;
        Element order = (Element) document.getElementsByTagName("order")
                .item (document.getElementsByTagName("order").getLength() - 1);
        NodeList items = order.getElementsByTagName("item");
        int quantity = 0;
        int cost = 0;
        for (int i = 0; i < items.getLength(); i++){
            quantity = Integer.parseInt(items.item(i).getTextContent().trim());
            cost = Integer.parseInt(items.item(i).getAttributes().item(0).getNodeValue());
            totalCostPerOrder += cost * quantity;
        }
        return totalCostPerOrder;
    }

    public void addTotalCost(){
        addTagElement("totalcost", "order");
        setTagText("totalcost", String.valueOf(countingTotalCostPerOrder()));
    }

    public org.w3c.dom.Document getDocument() {
        return document;
    }

    public int earningsTotal(String officiantSecondName, Calendar calendar) {

        return 0;
    }

    public void removeDay(Calendar calendar) {

    }

    public void changeOfficiantName(String oldFirstName, String oldSecondName, String newFirstName, String newSecondName) {

    }
}
