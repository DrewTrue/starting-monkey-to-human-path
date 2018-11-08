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

    public String getCreateRegistry(){
        return document.getElementsByTagName("createregistry").item(0).getTextContent();
    }

    public void setCreateRigistry(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("createregistry").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    public String getRegistryAddress(){
        return document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }

    public void setRegistryAddress(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("registryaddress").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    public String getRegistryPort(){
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }

    public void setRegistryPort(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("registryport").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    public String getPolicyPath(){
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }

    public void setPolicyPath(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("policypath").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    public String getUseCodeBaseOnly(){
        return document.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }

    public void setUseCodeBaseOnly(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("usecodebaseonly").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    public String getClassProvider(){
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }

    public void setClassProvider(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("classprovider").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    private void set(String tagName, String content) throws TransformerException, FileNotFoundException {
        Element element = (Element) document.getElementsByTagName(tagName).item(0);
        element.setTextContent(content);
        transformer();
    }

    public void addBlindObject(String attrValue1, String attrValue2) throws TransformerException, FileNotFoundException {
        Element blindObject = document.createElement("blindobject");
        blindObject.setAttribute("name", attrValue1);
        blindObject.setAttribute("class", attrValue2);
        document.getElementsByTagName("server").item(document.getElementsByTagName("server").getLength() - 1).appendChild(blindObject);
        transformer();
    }

    public void addRegistry(String createRegistryContent, String registryAddressContent, String registryPortContent) throws TransformerException, FileNotFoundException {
        Element registry = document.createElement("registry");
        Element createregistry = document.createElement("createregistry");
        Element registryaddress = document.createElement("registryaddress");
        Element registryport = document.createElement("registryport");
        registry.appendChild(createregistry);
        registry.appendChild(registryaddress);
        registry.appendChild(registryport);
        set("createregistry", createRegistryContent);
        set("registryaddress", registryAddressContent);
        set("registryport", registryPortContent);
        transformer();
    }

    public Document getDocument() {
        return document;
    }

    private void transformer() throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(new File(path))));
    }
}
