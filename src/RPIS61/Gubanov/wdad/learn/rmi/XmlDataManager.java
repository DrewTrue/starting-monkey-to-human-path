package RPIS61.Gubanov.wdad.learn.rmi;

import java.rmi.Remote;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface XmlDataManager extends Remote {
    public int earningsTotal(Officiant officiant, Calendar calendar);
    public void removeDay(Calendar calendar);
    public void changeOfficiantName(Officiant oldOfficient, Officiant newOfficient);
    public List<Order> getOrders(Calendar calendar);
    public Calendar lastOfficiantWorkDate(Officiant officiant);
}
