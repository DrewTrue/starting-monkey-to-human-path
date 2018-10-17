package RPIS61.Gubanov.wdad.data.managers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PreferencesManagers {
    private final static String path = "C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path\\" +
            "src\\RPIS61\\Gubanov\\wdad\\resources\\configuration\\appconfig.xml";

    private Document document;
    private static PreferencesManagers instance;

    private PreferencesManagers() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(new File(path));
    }

    public static PreferencesManagers getInstance() throws ParserConfigurationException, SAXException, IOException {
        if(instance == null){
            instance = new PreferencesManagers();
        }
        return instance;
    }

    public String get(String tagName){
        NodeList elements = document.getElementsByTagName(tagName);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < elements.getLength(); i++) {
            stringBuilder.append("tag: ");
           stringBuilder.append(elements.item(i).getNodeName()).append("\n").append(" attr(s): ");
            for (int j = 0; j < elements.item(i).getAttributes().getLength(); j++) {
                stringBuilder.append(elements.item(i).getAttributes().item(j).getNodeName()).append(" = ")
                        .append(elements.item(i).getAttributes().item(j).getNodeValue()).append("; ");
            }
            stringBuilder.append("\n").append("content: ").append(elements.item(i).getTextContent().trim()).append("\n")
                    .append("has child(s): ");
            for (int j = 1; j < elements.item(i).getChildNodes().getLength(); j+=2) {
                stringBuilder.append(elements.item(i).getChildNodes().item(j).getNodeName()).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void setElement(String tagName, String content){
        Node node = document.getElementsByTagName(tagName).item(document.getElementsByTagName(tagName).getLength() - 1);
        if((tagName.equals("createregistry") || tagName.equals("usecodebaseonly"))
                && (content.equals("yes") || content.equals("no"))){
            node.setTextContent(content);
        }
        if(tagName.equals("registryaddress") || tagName.equals("registryport")
                || tagName.equals("policypath") || tagName.equals("classprovider")){
            node.setTextContent(content);
        }
    }

    public void addBlindObject(String attrValue1, String attrValue2){
        Element blindObject = document.createElement("blindobject");
        blindObject.setAttribute("name", attrValue1);
        blindObject.setAttribute("class", attrValue2);
        document.getElementsByTagName("server").item(document.getElementsByTagName("server").getLength() - 1).appendChild(blindObject);
    }

    public void addRegistry(String createRegistryContent, String registryAddressContent, String registryPortContent){
        Element registry = document.createElement("registry");
        Element createregistry = document.createElement("createregistry");
        Element registryaddress = document.createElement("registryaddress");
        Element registryport = document.createElement("registryport");
        registry.appendChild(createregistry);
        registry.appendChild(registryaddress);
        registry.appendChild(registryport);
        setElement("createregistry", createRegistryContent);
        setElement("registryaddress", registryAddressContent);
        setElement("registryport", registryPortContent);
    }

    public Document getDocument() {
        return document;
    }

    public void transformer() throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(new File(path))));
    }
}
