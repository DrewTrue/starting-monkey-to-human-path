package RPIS61.Gubanov.wdad.data.managers;

import RPIS61.Gubanov.wdad.learn.xml.Officiant;
import RPIS61.Gubanov.wdad.learn.xml.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public interface DataManager extends Remote {
    int earningsTotal(Officiant officiant, Calendar calendar) throws RemoteException;
    void removeDay(Calendar calendar) throws RemoteException;
    void changeOfficiantName(Officiant oldOfficient, Officiant newOfficient) throws RemoteException;
    List<Order> getOrders(Calendar calendar) throws RemoteException;
    Calendar lastOfficiantWorkDate(Officiant officiant) throws RemoteException;
}
