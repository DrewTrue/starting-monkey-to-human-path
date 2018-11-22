package RPIS61.Gubanov.wdad.learn.rmi.client;

import RPIS61.Gubanov.wdad.data.managers.PreferencesManager;
import RPIS61.Gubanov.wdad.learn.rmi.XmlDataManager;
import RPIS61.Gubanov.wdad.utils.PreferencesManagerConstants;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        PreferencesManager manager = PreferencesManager.getInstance();
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.POLICY_PATH));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.USE_CODEBASE_ONLY));
        Registry registry;
        try{
            registry = LocateRegistry.getRegistry(manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS),
                    Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            XmlDataManager stub = (XmlDataManager) registry.lookup("XmlDataManager");
            System.out.println("stub executing...");
//            System.out.println(stub.earningsTotal());
        }
        catch (Exception e){
            System.out.println("crushed ");
            e.printStackTrace();
        }
    }
}
