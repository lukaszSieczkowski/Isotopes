package pl.isotope.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;


public class DbUtil {

    private static DbUtil dbUtil;
    private ComboPooledDataSource connectionPool;

    /**
     * Constructor creates object allow get connection with database
     *
     * @throws PropertyVetoException
     */
    private DbUtil() throws PropertyVetoException {
        connectionPool = new ComboPooledDataSource();
        connectionPool.setDriverClass("com.mysql.jdbc.Driver");
        connectionPool.setJdbcUrl("jdbc:mysql://localhost/isotope_bunker?useUnicode=true" +
                "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" +
                "&autoReconnect=true&useSSL=false");
        connectionPool.setUser("root");
        connectionPool.setPassword("admin");
        connectionPool.setInitialPoolSize(6);
        connectionPool.setMinPoolSize(6);
        connectionPool.setMaxPoolSize(10);
        connectionPool.setAcquireIncrement(3);
        connectionPool.setMaxIdleTime(3000);
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    /**
     * Method closes database connection
     */
    public void close() {
        connectionPool.close();
    }

    /**
     * Creates new DbUtilObject
     *
     * @return dbUtil
     */
    public static DbUtil getInstance() {
        if (dbUtil == null) {
            try {
                dbUtil = new DbUtil();
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
        return dbUtil;
    }

}
