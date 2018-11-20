package RPIS61.Gubanov.wdad.data.managers;

import RPIS61.Gubanov.wdad.utils.PreferencesManagerConstants;
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
import java.util.Enumeration;
import java.util.Properties;

public class PreferencesManagers {
    private final static String path = "C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path\\" +
            "src\\RPIS61\\Gubanov\\wdad\\resources\\configuration\\appconfig.xml";

    private Document document;
    private static PreferencesManagers instance;
    private Properties properties;
    private String[] keys;

    private PreferencesManagers() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(new File(path));
        this.properties = new Properties();
        this.keys = new String[]{PreferencesManagerConstants.createRegistry, PreferencesManagerConstants.registryAddress,
                PreferencesManagerConstants.registryPort, PreferencesManagerConstants.policyPath,
                PreferencesManagerConstants.useCodebaseOnly, PreferencesManagerConstants.classProvider};
        propertySetting();
    }

    private void propertySetting(){
        String value, key;
        String[] keyArray;
        for (int i = 0; i < keys.length; i++) {
            keyArray = keys[i].split("\\.");
            key = keyArray[keyArray.length - 1];
            value = document.getElementsByTagName(key).item(0).getTextContent();
            properties.setProperty(keys[i], value);
        }
    }

    public static PreferencesManagers getInstance() throws ParserConfigurationException, SAXException, IOException {
        if(instance == null){
            instance = new PreferencesManagers();
        }
        return instance;
    }

    @Deprecated
    public String getCreateRegistry(){
        return document.getElementsByTagName("createregistry").item(0).getTextContent();
    }

    @Deprecated
    public void setCreateRigistry(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("createregistry").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    @Deprecated
    public String getRegistryAddress(){
        return document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryAddress(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("registryaddress").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    @Deprecated
    public String getRegistryPort(){
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryPort(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("registryport").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    @Deprecated
    public String getPolicyPath(){
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }

    @Deprecated
    public void setPolicyPath(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("policypath").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    @Deprecated
    public String getUseCodeBaseOnly(){
        return document.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }

    @Deprecated
    public void setUseCodeBaseOnly(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("usecodebaseonly").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    @Deprecated
    public String getClassProvider(){
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }

    @Deprecated
    public void setClassProvider(String content) throws TransformerException, FileNotFoundException {
        Element createregistry = (Element) document.getElementsByTagName("classprovider").item(0);
        createregistry.setTextContent(content);
        transformer();
    }

    public void addBlindObject(String name, String className) throws TransformerException, FileNotFoundException {
        Element blindObject = document.createElement("blindobject");
        blindObject.setAttribute("name", name);
        blindObject.setAttribute("class", className);
        document.getElementsByTagName("server").item(0).appendChild(blindObject);
        transformer();
    }

    public void removeBlindObject(String name) throws TransformerException, FileNotFoundException {
        NodeList blindobjects = document.getDocumentElement().getElementsByTagName("blindobject");
        Element blindobject;
        for (int i = 0; i < blindobjects.getLength(); i++) {
            blindobject = (Element) blindobjects.item(i);
            if(blindobject.getAttribute("name").equals(name)){
                blindobject.getParentNode().removeChild(blindobject);
            }
        }
        transformer();
    }

    public void setProperty(String key, String value){
        document.getElementsByTagName(key).item(0).setTextContent(value);
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public void setProperties(Properties properties){
        String[] keys = properties.stringPropertyNames().toArray(new String[0]);
        for (int i = 0; i < keys.length; i++) {
            setProperty(keys[i], properties.getProperty(keys[i]));
        }
    }

    public Properties getProperties(){
        return properties;
    }

//    private void set(String tagName, String content) throws TransformerException, FileNotFoundException {
//        Element element = (Element) document.getElementsByTagName(tagName).item(0);
//        element.setTextContent(content);
//        transformer();
//    }
//
//    public void addRegistry(String createRegistryContent, String registryAddressContent, String registryPortContent) throws TransformerException, FileNotFoundException {
//        Element registry = document.createElement("registry");
//        Element createregistry = document.createElement("createregistry");
//        Element registryaddress = document.createElement("registryaddress");
//        Element registryport = document.createElement("registryport");
//        registry.appendChild(createregistry);
//        registry.appendChild(registryaddress);
//        registry.appendChild(registryport);
//        set("createregistry", createRegistryContent);
//        set("registryaddress", registryAddressContent);
//        set("registryport", registryPortContent);
//        transformer();
//    }
//
//    public Document getDocument() {
//        return document;
//    }

    private void transformer() throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(new File(path))));
    }
}
