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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class XmlTask implements Serializable{
    private Document document;
    private File file;

    private final static String PATH = "C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path" +
            "\\src\\RPIS61\\Gubanov\\wdad\\learn\\xml\\test1.xml";

    public XmlTask(String fileName) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.file = new File("C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path" +
                "\\src\\RPIS61\\Gubanov\\wdad\\learn\\xml\\" + fileName +".xml");
        this.document = builder.newDocument();
    }

    public XmlTask() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        this(new File(PATH));
    }

    public XmlTask(File file) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.file = file;
        this.document = builder.parse(file);
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

    public org.w3c.dom.Document getDocument() {
        return document;
    }

    private Element findDay(Calendar day) {
        NodeList days = document.getElementsByTagName("date");
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

    public void removeDay(Calendar calendar) throws TransformerException, FileNotFoundException {
        Element day = findDay(calendar);
        if(day != null)
            document.getDocumentElement().removeChild(day);
        transformer();
    }

    public void changeOfficiantName(String oldFirstName, String oldSecondName, String newFirstName, String newSecondName) throws TransformerException, FileNotFoundException {
        NodeList officiants = document.getElementsByTagName("officiant");
        Element officiant;
        for(int i = 0; i < officiants.getLength(); i++){
            officiant = (Element) officiants.item(i);
            if(officiant.getAttribute("firstname").equals(oldFirstName)
                    && officiant.getAttribute("lastname").equals(oldSecondName)) {
                officiant.setAttribute("firstname", newFirstName);
                officiant.setAttribute("lastname", newSecondName);
            }
        }
        transformer();
    }

    public List<Order> getOrders(Calendar calendar){
        List<Order> orders = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        Element day = findDay(calendar);
        NodeList orderList = day.getElementsByTagName("order");
        NodeList itemList;
        Order order;
        Item item;
        Officiant officiant;
        Element orderElement, officiantElement, itemElement;

        for (int i = 0; i < orderList.getLength(); i++) {
           orderElement = (Element) orderList.item(i);
           officiantElement = (Element) orderElement.getElementsByTagName("officiant").item(0);
           officiant = new Officiant(officiantElement.getAttribute("firstname"), officiantElement.getAttribute("lastname"));

           itemList = orderElement.getElementsByTagName("item");
            for (int j = 0; j < itemList.getLength(); j++) {
                itemElement = (Element) itemList.item(i);
                item = new Item(itemElement.getAttribute("name"), Integer.parseInt(itemElement.getAttribute("cost")));
                items.add(item);
            }
            order = new Order(officiant, items);
            orders.add(order);
        }

        return orders;
    }

    public Calendar lastOfficientWorkDate(Officiant officiant){
        NodeList officiants, days = document.getElementsByTagName("date");
        Element officiantElement, dayElement;
        Calendar calendar;
        int year, month, day;
        for (int j = 0; j < days.getLength(); j++) {
            dayElement = (Element) days.item(j);
            officiants = dayElement.getElementsByTagName("officiant");
            for (int i = 0; i < officiants.getLength(); i++) {
                officiantElement = (Element) officiants.item(i);
                if (officiantElement.getAttribute("firstname").equals(officiant.getFirstName())
                        && officiantElement.getAttribute("lastname").equals(officiant.getSecondName())) {
                    year = Integer.parseInt(dayElement.getAttribute("year"));
                    month = Integer.parseInt(dayElement.getAttribute("month"));
                    day = Integer.parseInt(dayElement.getAttribute("day"));
                    calendar = Calendar.getInstance();
                    calendar.set(year, month - 1, day);
                    return calendar;
                }
            }
        }

        return null;
    }

    private void transformer() throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
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

    public void buildingXmlDocument() {
        document.appendChild(document.createElement("restaurant"));
    }

    public void addTotalCost(){
        addTagElement("totalcost", "order");
        setTagText("totalcost", String.valueOf(getTotalCostPerLastOrder()));
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
