package RPIS61.Gubanov.wdad.learn.rmi;

import java.util.Date;
import java.util.List;

public interface XmlDataManager {
    public int earningsTotal(Officiant officiant, Date date);
    public void removeDay(Date date);
    public void changeOfficiantName(Officiant oldOfficient, Officiant newOfficient);
    public List<Order> getOrders(Date date);
    public Date lastOfficiantWorkDate(Officiant officiant);
}
