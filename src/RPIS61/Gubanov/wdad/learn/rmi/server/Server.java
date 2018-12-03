package RPIS61.Gubanov.wdad.learn.rmi.server;

import RPIS61.Gubanov.wdad.data.managers.PreferencesManager;
import RPIS61.Gubanov.wdad.learn.rmi.XmlDataManager;
import RPIS61.Gubanov.wdad.utils.PreferencesManagerConstants;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    private static Registry registry;

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        PreferencesManager manager = PreferencesManager.getInstance();
        System.setProperty("java.rmi.server.hostname", manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS));
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.POLICY_PATH));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.USE_CODEBASE_ONLY));
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
//        System.out.println(System.getProperty("java.class.path"));
        try {
            System.out.println("initialization registry");
            if(manager.getProperty(PreferencesManagerConstants.CREATE_REGISTRY).equals("yes")){
                registry = LocateRegistry.createRegistry(Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            } else {
                registry = LocateRegistry.getRegistry(Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            }
            XmlDataManagerImpl xmlDataManager = new XmlDataManagerImpl();
            System.out.println("export object");
            XmlDataManager stub = (XmlDataManager) UnicastRemoteObject.exportObject(xmlDataManager,0);
//            UnicastRemoteObject.exportObject(xmlDataManager,0);
            System.out.println("bind object");
            registry.bind("XmlDataManager", xmlDataManager);
            System.out.println("server is ready");
        }
        catch (Exception e){
            System.out.println("crashed");
            e.printStackTrace();
        }
    }
}