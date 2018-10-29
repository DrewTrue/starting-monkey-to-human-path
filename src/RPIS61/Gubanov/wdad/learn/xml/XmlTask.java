package RPIS61.Gubanov.wdad.learn.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Calendar;

public class XmlTask {
    private Document document;
    private File file;

    public XmlTask(String fileName) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.file = new File("C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path" +
                "\\src\\RPIS61\\Gubanov\\wdad\\learn\\" + fileName +".xml");
        this.document = builder.newDocument();
    }

    public XmlTask(File file) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.file = file;
        this.document = builder.parse(file);
        //todo check totalcost
        totalcostValidation();
    }

    private void totalcostValidation() throws TransformerException, FileNotFoundException {
        NodeList orders = document.getElementsByTagName("order");
        Element order, totalcost;
        for (int i = 0; i < orders.getLength(); i++) {
            order = (Element) orders.item(i);
            if(order.getElementsByTagName("totalcost").getLength() == 0){
                totalcost = document.createElement("totalcost");
                totalcost.setTextContent(String.valueOf(getTotalCost(order)));
                order.appendChild(totalcost);
                transformer();
            } else {
                totalcost = (Element) order.getElementsByTagName("totalcost").item(0);
                if(totalcost.getTextContent() == null){
                    totalcost.setTextContent(String.valueOf(getTotalCost(order)));
                    transformer();
                }
            }
        }
    }

    private int getTotalCost(Element order) {
        int totalcost = 0, quantity, cost;
        NodeList items = order.getElementsByTagName("item");
        Element item;
        for (int i = 0; i < items.getLength(); i++){
            item = (Element) items.item(i);
            quantity = Integer.parseInt(item.getTextContent().trim());
            cost = Integer.parseInt(item.getAttribute("cost"));
            totalcost += cost * quantity;
        }
        return totalcost;
    }

//todo rename
    private int getTotalCostPerLastOrder() {
        int totalcost = 0, quantity, cost;
        Element item, order = (Element) document.getElementsByTagName("order")
                .item (document.getElementsByTagName("order").getLength() - 1);
        NodeList items = order.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++){
            item = (Element) items.item(i);
            quantity = Integer.parseInt(item.getTextContent().trim());
            cost = Integer.parseInt(item.getAttribute("cost"));
            totalcost += cost * quantity;
        }
        return totalcost;
    }

    public void addTotalCost(){
        addTagElement("totalcost", "order");
        setTagText("totalcost", String.valueOf(getTotalCostPerLastOrder()));
    }

    public org.w3c.dom.Document getDocument() {
        return document;
    }

    private Element findDay(Calendar day) {
        NodeList days = document.getElementsByTagName("date");
        //todo Use Element.getAttribute()
        Element calendar;
        for(int i = 0; i < days.getLength(); i++) {
            calendar = (Element) days.item(i);
            if(Integer.parseInt(calendar.getAttribute("day")) == day.get(Calendar.DATE)
                    && Integer.parseInt(calendar.getAttribute("month")) == day.get(Calendar.MONTH) + 1
                    && Integer.parseInt(calendar.getAttribute("year")) == day.get(Calendar.YEAR)) {
                return (Element) days.item(i);
            }
        }
        return null;
    }

    public int earningsTotal(String officiantFirstName, String officiantSecondName, Calendar calendar) {
        int totalCost = 0;
        Element officiant, order, day = findDay(calendar);
        if(day != null) {
            NodeList officiants = day.getElementsByTagName("officiant");
            for (int i = 0; i < officiants.getLength(); i++) {
                officiant = (Element) officiants.item(i);
                order = (Element) officiant.getParentNode();

                if (officiant.getAttribute("firstname").equals(officiantFirstName)
                        && officiant.getAttribute("lastname").equals(officiantSecondName)) {
                    totalCost += Integer.parseInt(order.getElementsByTagName("totalcost").item(0).getTextContent());
                }
            }
        }
        return totalCost;
    }
//todo check null
    public void removeDay(Calendar calendar) throws TransformerException, FileNotFoundException {
        Element day = findDay(calendar);
        if(day != null)
            document.getDocumentElement().removeChild(day);
        transformer();
    }

    public void changeOfficiantName(String oldFirstName, String oldSecondName, String newFirstName, String newSecondName) throws TransformerException, FileNotFoundException {
        NodeList officiants = document.getElementsByTagName("officiant");
        //todo Use Element.getAttribute() & setAttribute()
        Element officiant;
        for(int i = 0; i < officiants.getLength(); i++){
            //todo Element officiant = (Element) officiants.item(i);
            officiant = (Element) officiants.item(i);
            if(officiant.getAttribute("firstname").equals(oldFirstName)
                    && officiant.getAttribute("lastname").equals(oldSecondName)) {
                officiant.setAttribute("firstname", newFirstName);
                officiant.setAttribute("lastname", newSecondName);
            }
        }
        transformer();
    }

    public void buildingXmlDocument() {
        document.appendChild(document.createElement("restaurant"));
    }

    private Element addTagElement(String elementName, String parentName) {
        Element child = document.createElement(elementName);
        document.getElementsByTagName(parentName)
                .item(document.getElementsByTagName(parentName).getLength() - 1).appendChild(child);
        return child;
    }

    private void setTagAtribute(String tagName, String attrName, String attrValue) {
        Element element = (Element) document.getElementsByTagName(tagName)
                .item (document.getElementsByTagName(tagName).getLength() - 1);
        element.setAttribute(attrName, attrValue);
    }

    private void setTagText(String tagName, String text){
        Element element = (Element) document.getElementsByTagName(tagName)
                .item (document.getElementsByTagName(tagName).getLength() - 1);
        element.setTextContent(text);
    }

    private void transformer() throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
    }

    public Element addAnOrder() throws TransformerException, FileNotFoundException {
        Element order = addTagElement("order", "date");
        transformer();
        return order;
    }

    public void addAnItem(String name, String cost , String quantity) throws TransformerException, FileNotFoundException {
        addTagElement("item", "order");
        setTagAtribute("item", "name", name);
        setTagAtribute("item", "cost", cost);
        setTagText("item", quantity);
        transformer();
    }

    public void addAnOfficiant(String firstname, String lastname) throws TransformerException, FileNotFoundException {
        addTagElement("officiant", "order");
        setTagAtribute("officiant", "firstname", firstname);
        setTagAtribute("officiant", "lastname", lastname);
        transformer();
    }

    public void addDate() throws TransformerException, FileNotFoundException {
        addTagElement("date", "restaurant");
        setTagAtribute("date", "year", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        setTagAtribute("date", "month", String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
        setTagAtribute("date", "day", String.valueOf(Calendar.getInstance().get(Calendar.DATE)));
        transformer();
    }
}
