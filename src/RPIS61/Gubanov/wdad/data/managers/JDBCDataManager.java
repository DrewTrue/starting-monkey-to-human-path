package RPIS61.Gubanov.wdad.data.managers;

import RPIS61.Gubanov.wdad.learn.xml.Officiant;
import RPIS61.Gubanov.wdad.learn.xml.Order;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

public class JDBCDataManager implements DataManager {
    @Override
    public int earningsTotal(Officiant officiant, Calendar calendar) throws RemoteException {
        return 0;
    }

    @Override
    public void removeDay(Calendar calendar) throws RemoteException {

    }

    @Override
    public void changeOfficiantName(Officiant oldOfficient, Officiant newOfficient) throws RemoteException {

    }

    @Override
    public List<Order> getOrders(Calendar calendar) throws RemoteException {
        return null;
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws RemoteException {
        return null;
    }
}
