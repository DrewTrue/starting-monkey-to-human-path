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
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JDBCDataManager implements DataManager {

    private static final String SELECT_OFFICIANT_ID_QUERY = "select id from officiants where first_name = ? and second_name = ?";
    private static final String DELETE_DATE_QUERY = "delete from orders where date(date) = ?";
    private static final String UPDATE_OFFICIANT_QUERY = "update officiants set first_name = ?, second_name = ? where first_name = ? and second_name = ?";;
    private static final String SELECT_LATE_DATA_QUERY = "select max(date) as date from orders join officiants on(orders.officiant_id = officiants.id) where first_name = ? and second_name = ?";
    private static final String SELECT_SUM_QUERY = "SELECT SUM( items.cost * items_orders.quantity ) AS totalcost FROM orders JOIN officiants ON ( orders.officiant_id = officiants.id ) JOIN items_orders ON ( orders.id = items_orders.orders_id ) JOIN items ON ( items.id = items_orders.items_dictionary_id ) WHERE DATE( orders.date ) =  ? AND (officiants.first_name = ? AND officiants.second_name = ?)";
    private static final String SELECT_JOIN_ALL_QUERY = "SELECT orders.id, officiants.first_name, officiants.second_name, items_orders.quantity, items.name, items.cost FROM orders JOIN officiants ON ( officiants.id = orders.officiant_id ) JOIN items_orders ON ( orders.id = items_orders.orders_id ) JOIN items ON ( items.id = items_orders.items_dictionary_id ) WHERE DATE( orders.date ) = ?";

    DataSource dataSource;

    public JDBCDataManager() throws ParserConfigurationException, SAXException, IOException {
        //todo one connection per method
        this.dataSource = DataSourceFactory.createDataSource();
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
//        statement.setDate();
        //todo other method same as here
        PreparedStatement statement;
        ResultSet result = null;
        try {
            Connection connection = dataSource.getConnection();
            statement = connection.prepareStatement(SELECT_OFFICIANT_ID_QUERY);
            statement.setString(1, officiant.getFirstName());
            statement.setString(2, officiant.getSecondName());
            result = statement.executeQuery();
            if(result.next()){
                id = result.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert result != null;
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
        //todo use agregate functions
        //todo JOIN items ON (items.id = orders_items.item_id) JOIN officiants ON (officiants.id = oreders_items.officians.id)
        Connection connection;
        PreparedStatement statement;
        ResultSet result = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SELECT_SUM_QUERY);
            statement.setDate(1, new Date(calendar.getTimeInMillis()));
            statement.setString(2, officiant.getFirstName());
            statement.setString(3, officiant.getSecondName());
            result = statement.executeQuery();
            if(result.next())
                totalcost = result.getInt("totalcost");
        } catch (Exception e){
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
        //todo
        Connection connection;
        PreparedStatement statement;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(DELETE_DATE_QUERY);
            statement.setDate(1, new Date(calendar.getTimeInMillis()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficient, Officiant newOfficient) throws RemoteException {
        Connection connection;
        PreparedStatement statement;
        try{
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(UPDATE_OFFICIANT_QUERY);
            statement.setString(1, newOfficient.getFirstName());
            statement.setString(2, newOfficient.getSecondName());
            statement.setString(3, oldOfficient.getFirstName());
            statement.setString(4, oldOfficient.getSecondName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrders(Calendar calendar) throws RemoteException {
        List<Order> orders = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        Order order;
        Item item;
        Officiant officiant;
        int orderID;
        ResultSet result = null;
        Connection connection;
        PreparedStatement statement;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SELECT_JOIN_ALL_QUERY);
            statement.setDate(1, new Date(calendar.getTimeInMillis()));
            result = statement.executeQuery();
            result.next();
            orderID = result.getInt("id");
            officiant = new Officiant(result.getString("first_name"), result.getString("second_name"));
//            System.out.println("id = " + orderID);
            result = statement.executeQuery();
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
        //todo use MAX(date)
        Connection connection;
        PreparedStatement statement;
        ResultSet result = null;
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SELECT_LATE_DATA_QUERY);
            statement.setString(1, officiant.getFirstName());
            statement.setString(2, officiant.getSecondName());
            result = statement.executeQuery();
            if(result.next()) {
                date = result.getDate("date");
                calendar.setTime(date);
            }
        } catch (SQLException e) {
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
