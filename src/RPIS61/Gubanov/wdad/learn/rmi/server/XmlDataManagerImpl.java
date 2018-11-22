package RPIS61.Gubanov.wdad.learn.rmi.server;

import RPIS61.Gubanov.wdad.learn.rmi.Officiant;
import RPIS61.Gubanov.wdad.learn.rmi.Order;
import RPIS61.Gubanov.wdad.learn.rmi.XmlDataManager;

import java.util.Date;
import java.util.List;

public class XmlDataManagerImpl implements XmlDataManager {
    @Override
    public int earningsTotal(Officiant officiant, Date date) {
        return 0;
    }

    @Override
    public void removeDay(Date date) {

    }

    @Override
    public void changeOfficiantName(Officiant oldOfficient, Officiant newOfficient) {

    }

    @Override
    public List<Order> getOrders(Date date) {
        return null;
    }

    @Override
    public Date lastOfficiantWorkDate(Officiant officiant) {
        return null;
    }
}
