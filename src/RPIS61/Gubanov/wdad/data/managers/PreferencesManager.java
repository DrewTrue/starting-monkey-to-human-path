package RPIS61.Gubanov.wdad.data.managers;

import RPIS61.Gubanov.wdad.utils.PreferencesManagerConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.util.Properties;

public class PreferencesManager {
    private final static String path = "src\\RPIS61\\Gubanov\\wdad\\resources\\configuration\\appconfig.xml";

    private Document document;
    private static PreferencesManager instance;
    private Properties properties;
    private String[] keys;

    private PreferencesManager() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(new File(path));
        this.properties = new Properties();
        this.keys = new String[]{
                PreferencesManagerConstants.CREATE_REGISTRY, PreferencesManagerConstants.REGISTRY_ADDRESS,
                PreferencesManagerConstants.REGISTRY_PORT, PreferencesManagerConstants.POLICY_PATH,
                PreferencesManagerConstants.USE_CODEBASE_ONLY, PreferencesManagerConstants.CLASS_PROVIDER,
                PreferencesManagerConstants.CLASS_NAME, PreferencesManagerConstants.DRIVER_TYPE,
                PreferencesManagerConstants.HOST_NAME, PreferencesManagerConstants.PORT,
                PreferencesManagerConstants.DB_NAME, PreferencesManagerConstants.USER,
                PreferencesManagerConstants.PASSWORD };
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

    public static PreferencesManager getInstance() throws ParserConfigurationException, SAXException, IOException {
        if(instance == null){
            instance = new PreferencesManager();
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

    public void addBindObject(String name, String className) throws TransformerException, FileNotFoundException {
        Element blindObject = document.createElement("bindobject");
        blindObject.setAttribute("name", name);
        blindObject.setAttribute("class", className);
        document.getElementsByTagName("server").item(0).appendChild(blindObject);
        transformer();
    }

    public void removeBindObject(String name) throws TransformerException, FileNotFoundException {
        NodeList bindobjects = document.getDocumentElement().getElementsByTagName("bindobject");
        Element blindobject;
        for (int i = 0; i < bindobjects.getLength(); i++) {
            blindobject = (Element) bindobjects.item(i);
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

    private void transformer() throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(new File(path))));
    }
}
