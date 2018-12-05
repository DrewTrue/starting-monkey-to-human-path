package RPIS61.Gubanov.wdad.data.storage;

import RPIS61.Gubanov.wdad.data.managers.PreferencesManager;
import RPIS61.Gubanov.wdad.utils.PreferencesManagerConstants;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.xml.sax.SAXException;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DataSourceFactory {
    public static DataSource createDataSource() throws IOException, SAXException, ParserConfigurationException {
        PreferencesManager manager = PreferencesManager.getInstance();
        MysqlDataSource dataSource = new MysqlDataSource();
        String url = "jdbc:" + manager.getProperty(PreferencesManagerConstants.DRIVER_TYPE) +
                "://" + manager.getProperty(PreferencesManagerConstants.HOST_NAME) +
                ':' + manager.getProperty(PreferencesManagerConstants.PORT) +
                '/' + manager.getProperty(PreferencesManagerConstants.DB_NAME);
        dataSource.setUrl(url);
//        dataSource.setURL(url);
        dataSource.setUser(manager.getProperty(PreferencesManagerConstants.USER));
        dataSource.setPassword(manager.getProperty(PreferencesManagerConstants.PASSWORD));
        return dataSource;
    }

    public static DataSource createDataSource(String className, String driverType, String host, int port,
                                              String dbName, String user, String password) throws IOException, SAXException, ParserConfigurationException {
        MysqlDataSource dataSource = new MysqlDataSource();
        String url = "jdbc:" + driverType + "://" + host + ':' + port + '/' + dbName;
        dataSource.setUrl(url);
//        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
