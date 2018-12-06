package RPIS61.Gubanov.wdad.data.managers;

import RPIS61.Gubanov.wdad.data.storage.DataSourceFactory;
import RPIS61.Gubanov.wdad.learn.xml.Item;
import RPIS61.Gubanov.wdad.learn.xml.Officiant;
import RPIS61.Gubanov.wdad.learn.xml.Order;
import org.xml.sax.SAXException;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JDBCDataManager implements DataManager {
    private Statement statement;

    private final static String DROP_VIEW_QUERY = "drop view restaurant_view";
    private final static String CREATE_VIEW_QUERY = "create or replace view restaurant_view as select \n" +
            "o.id, o.date, o.officiant_id, \n" +
            "of.id as of_id, of.first_name, of.second_name,\n" +
            "io.id as io_id, io.orders_id, io.items_dictionary_id, io.quantity,\n" +
            "i.id as i_id, i.name, i.cost \n" +
            "from orders o, officiants of, items i, items_orders io \n" +
            "where o.officiant_id = of.id \n" +
            "and o.id = io.orders_id\n" +
            "and io.items_dictionary_id = i.id";

    public JDBCDataManager() throws ParserConfigurationException, SAXException, IOException, SQLException {
        DataSource dataSource = DataSourceFactory.createDataSource();
        Connection connection = dataSource.getConnection();
        this.statement = connection.createStatement();
//        statement.execute(DROP_VIEW_QUERY);
        statement.execute(CREATE_VIEW_QUERY);
    }

    private String getMySQLDate(Calendar calendar){
        String date;
        date = String.valueOf(calendar.get(Calendar.YEAR));
        date += '-';
        date += String.valueOf(calendar.get(Calendar.MONTH) + 1);
        date +='-';
        if(calendar.get(Calendar.DATE) < 10) {
            date += '0';
            date += String.valueOf(calendar.get(Calendar.DATE));
        }
        else {
            date += String.valueOf(calendar.get(Calendar.DATE));
        }
        return date;
    }

    private String getOfficiantID(Officiant officiant){
        String id = null;
        String sqlQuery = "select id from officiants where first_name = " + '"' + officiant.getFirstName() + '"'
                + "and second_name = " + '"' + officiant.getSecondName() + '"';
        ResultSet result = null;
        try {
            result = statement.executeQuery(sqlQuery);
        if(result != null){
            while (result.next())
                id = result.getString("id");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public int earningsTotal(Officiant officiant, Calendar calendar) throws RemoteException {
        int totalcost = 0;
        String officaintId = getOfficiantID(officiant);
        if(officaintId == null) {
            System.out.println("there is no such officiant");
            return totalcost;
        }
        String date = getMySQLDate(calendar);
        ResultSet result = null;
        String sqlQuery = "select quantity, cost from restaurant_view where date(date) = "
                            + '"' + date + '"' + "and officiant_id = " + officaintId;
        try {
            result = statement.executeQuery(sqlQuery);
            while (result.next()){
                totalcost += result.getInt("quantity") * result.getInt("cost");
            }
        } catch (Exception e){
            System.out.println("crashed");
            e.printStackTrace();
        } finally {
            try {
                assert result != null;
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalcost;
    }

    @Override
    public void removeDay(Calendar calendar) throws RemoteException {
        String date = getMySQLDate(calendar);
        String sqlQuery = "delete from orders where date(date) = " + '"' + date + '"';
        try {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println("crashed");
            e.printStackTrace();
        }
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficient, Officiant newOfficient) throws RemoteException {
        String officiantID = getOfficiantID(oldOfficient);
        if(officiantID == null){
            System.out.println("there is no such officiant");
            return;
        }
        String sqlQuery = "update officiants set" +
                " first_name = " + '"' + newOfficient.getFirstName() + '"' + ',' +
                " second_name = " + '"' + newOfficient.getSecondName() + '"' + " where" +
                " first_name = " + '"' + oldOfficient.getFirstName() + '"' + " and" +
                " second_name = " + '"' + oldOfficient.getSecondName() + '"';
        try {
            statement.execute(sqlQuery);
            System.out.println("officiant name has changed from " +
                    oldOfficient.getFirstName() + ' ' + oldOfficient.getSecondName() + " to " +
                    newOfficient.getFirstName() + ' ' + newOfficient.getSecondName());
        } catch (SQLException e) {
            System.out.println("crashed");
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrders(Calendar calendar) throws RemoteException {
        String date = getMySQLDate(calendar);
        List<Order> orders = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        Order order;
        Item item;
        Officiant officiant = null;
        String sqlQuery = "select * from restaurant_view where date(date) = " + '"' + date + '"';
//        System.out.println(sqlQuery);
        ResultSet result = null;
        int orderID = 0;
        try {
            result = statement.executeQuery(sqlQuery);
            result.next();
            orderID = result.getInt("id");
            officiant = new Officiant(result.getString("first_name"), result.getString("second_name"));
//            System.out.println("id = " + orderID);
            result = statement.executeQuery(sqlQuery);
            while (result.next()){
                if(orderID == result.getInt("id")) {
                    item = new Item(result.getString("name"), result.getInt("cost"));
                    items.add(item);
//                    System.out.println("item equals = " + item.getName());
//                    System.out.println("id equals");
                } else {
                    order = new Order(officiant, items);
                    orders.add(order);
//                    System.out.println("items size = " + items.size());
//                    System.out.println(items.get(0).getName());
                    items = new ArrayList<>();
                    item = new Item(result.getString("name"), result.getInt("cost"));
                    items.add(item);
//                    System.out.println("item not equals = " + item.getName());
//                    System.out.println("id not equals");
                }
                officiant = new Officiant(result.getString("first_name"), result.getString("second_name"));
                orderID = result.getInt("id");
//                System.out.println("id = " + orderID);
            }
            order = new Order(officiant, items);
            orders.add(order);
        } catch (SQLException e) {
            System.out.println("crashed");
            e.printStackTrace();
        } finally {
            try {
                assert result != null;
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("orders size in jdbcdatamanager = " + orders.size());
//        System.out.println("items size in jdbcdatamanager = " + orders.get(1).getItems().size());
        return orders;
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws RemoteException {
        String officaintId = getOfficiantID(officiant);
        if(officaintId == null) {
            System.out.println("there is no such officiant");
            return null;
        }
        String sqlQuery = "select dayofmonth(DATE) as day, month(date) as month, year(date) as year from restaurant_view\n" +
                "where officiant_id = " + officaintId;
        ResultSet result = null;
        Calendar calendar = Calendar.getInstance();
        try {
            result = statement.executeQuery(sqlQuery);
            result.last();
            calendar.set(result.getInt("year"), result.getInt("month") - 1, result.getInt("day"));
        } catch (SQLException e) {
            System.out.println("crashed");
            e.printStackTrace();
        }
        finally {
            try {
                assert result != null;
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return calendar;
    }
}
