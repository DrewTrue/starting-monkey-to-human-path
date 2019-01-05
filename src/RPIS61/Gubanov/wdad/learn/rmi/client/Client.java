package RPIS61.Gubanov.wdad.learn.rmi.client;

import RPIS61.Gubanov.wdad.data.managers.PreferencesManager;
import RPIS61.Gubanov.wdad.data.managers.DataManager;
import RPIS61.Gubanov.wdad.learn.xml.Officiant;
import RPIS61.Gubanov.wdad.utils.PreferencesManagerConstants;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;

public class Client {
    private static Registry registry;

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        PreferencesManager manager = PreferencesManager.getInstance();
        System.setProperty("java.rmi.server.hostname", manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS));
        System.setProperty("java.security.policy", manager.getProperty(PreferencesManagerConstants.POLICY_PATH));
        System.setProperty("java.rmi.server.usecodebaseonly", manager.getProperty(PreferencesManagerConstants.USE_CODEBASE_ONLY));
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        try{
            System.out.println("initialization registry");
            registry = LocateRegistry.getRegistry(manager.getProperty(PreferencesManagerConstants.REGISTRY_ADDRESS),
                    Integer.parseInt(manager.getProperty(PreferencesManagerConstants.REGISTRY_PORT)));
            System.out.println("look up bean");
            System.out.println(registry.toString());
            String[] list = registry.list();
            System.out.println(list.length);
            DataManager stub = (DataManager) registry.lookup("DataManager");
            System.out.println("stub executing...");
            System.out.println(stub.getOrders(Calendar.getInstance()).get(0).getOfficiant().getFirstName());
            System.out.println(stub.earningsTotal(new Officiant("John", "Stones"), Calendar.getInstance()));
            stub.removeDay(Calendar.getInstance());
        }
        catch (Exception e){
            System.out.println("crushed ");
            e.printStackTrace();
        }
    }
}
