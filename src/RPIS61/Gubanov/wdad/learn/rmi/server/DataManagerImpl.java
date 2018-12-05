package RPIS61.Gubanov.wdad.learn.rmi.server;

import RPIS61.Gubanov.wdad.data.managers.DataManager;
import RPIS61.Gubanov.wdad.learn.xml.Officiant;
import RPIS61.Gubanov.wdad.learn.xml.Order;
import RPIS61.Gubanov.wdad.learn.xml.XmlTask;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class DataManagerImpl implements DataManager, Serializable {
    private XmlTask restaurant;

    public DataManagerImpl() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        this.restaurant = new XmlTask();
    }

    @Override
    public int earningsTotal(Officiant officiant, Calendar calendar) {
        return restaurant.earningsTotal(officiant.getFirstName(), officiant.getSecondName(), calendar);
    }

    @Override
    public void removeDay(Calendar calendar) {
        try {
            restaurant.removeDay(calendar);
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficient, Officiant newOfficient) {
        try {
            restaurant.changeOfficiantName(oldOfficient.getFirstName(), oldOfficient.getSecondName(),
                    newOfficient.getFirstName(), newOfficient.getSecondName());
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrders(Calendar calendar) {
        return restaurant.getOrders(calendar);
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) {
        return restaurant.lastOfficientWorkDate(officiant);
    }
}
