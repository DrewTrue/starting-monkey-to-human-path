package RPIS61.Gubanov.wdad.learn.rmi.server;

import RPIS61.Gubanov.wdad.data.managers.PreferencesManager;
import RPIS61.Gubanov.wdad.learn.rmi.XmlDataManager;
import RPIS61.Gubanov.wdad.utils.PreferencesManagerConstants;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        PreferencesManager manager = PreferencesManager.getInstance();
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        System.setProperty("java.rmi.server.hostname", manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS));
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.POLICY_PATH));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.USE_CODEBASE_ONLY));
        Registry registry;
        File file;
        try {
            System.out.println("start registry");
            registry = LocateRegistry.getRegistry(Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            file = new File("C:\\Users\\пользователь\\IdeaProjects\\starting-monkey-to-human-path" +
                    "\\src\\RPIS61\\Gubanov\\wdad\\learn\\xml\\test1.xml");
            XmlDataManagerImpl xmlDataManager = new XmlDataManagerImpl(file);

            System.out.println("export object");
            XmlDataManager stub = (XmlDataManager) UnicastRemoteObject.exportObject(xmlDataManager,0);

            System.out.println("bind object");
            registry.bind("XmlDataManager", stub);
            manager.addBindObject("XmlDataManager", "XmlDataManager");

            System.out.println("server is ready");
        }
        catch (Exception e){
            System.out.println("crashed ");
            e.printStackTrace();
        }
    }
}