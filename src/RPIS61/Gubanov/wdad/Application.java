package RPIS61.Gubanov.wdad;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

public class Application {
    public static void main(String[] args) {
        System.out.println("I'm Dmitriy Gubanov, and I'm not monkey");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root", "");
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
