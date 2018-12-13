package RPIS61.Gubanov.wdad.data.managers;

import RPIS61.Gubanov.wdad.data.storage.DataSourceFactory;
import RPIS61.Gubanov.wdad.learn.xml.Officiant;
import RPIS61.Gubanov.wdad.learn.xml.Order;
import org.xml.sax.SAXException;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            JDBCDataManager dataManager = new JDBCDataManager();
            Officiant officiant = new Officiant("Alice", "Ro");
            Calendar calendar = Calendar.getInstance();
            calendar.set(2018, Calendar.DECEMBER, 5);
            System.out.println(dataManager.earningsTotal(officiant, calendar));
            Officiant oldOff = new Officiant("Petr", "Ivanov");
            Officiant newOff = new Officiant("Bill", "Clinton");
            dataManager.changeOfficiantName(oldOff, newOff);
            calendar = Calendar.getInstance();
            calendar.set(2018, Calendar.DECEMBER, 1);
            dataManager.removeDay(calendar);
            calendar.set(2018, Calendar.DECEMBER, 5);
            List<Order> orders = dataManager.getOrders(calendar);
            System.out.println(orders.size());
            System.out.println(orders.get(0).getItems().get(1).getName());
            System.out.println(dataManager.lastOfficiantWorkDate(officiant).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
