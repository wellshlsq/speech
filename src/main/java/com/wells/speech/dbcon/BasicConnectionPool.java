package com.wells.speech.dbcon;

import com.wells.speech.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BasicConnectionPool implements ConnectionPool {
    private static final String YUGADB_URL = "yugadb.url";
    private static final String YUGADB_USER = "yugadb.user";
    private static final String YUGADB_PASSWORD = "yugadb.password";
    private String url;
    private String user;
    private String password;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 5;

    public List<Connection> create(
            String url, String user,
            String password) throws Exception {
        System.out.println("Initializing connection pool..");
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return pool;
    }

    // standard constructors
    public BasicConnectionPool(){
        super();
        try {
            ConfigProperties configProp = new ConfigProperties();
            this.connectionPool = create(configProp.getConfigValue(YUGADB_URL), configProp.getConfigValue(YUGADB_USER), configProp.getConfigValue(YUGADB_PASSWORD));
        } catch (Exception ex) {
            System.out.println("Error: Unable to create Connection Pool.. Reason:" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public BasicConnectionPool(String url, String user, String password) throws Exception {
        this.url = url;
        this.password = password;
        this.user = user;
        this.connectionPool = create(url, user, password);
    }

    @Override
    public Connection getConnection() {

        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(
            String url, String user, String password)
            throws Exception {
        return DriverManager.getConnection(url, user, password);
        //return DriverManager.getConnection("jdbc:yugabytedb://20.115.82.248:5433/yugabyte",
        //        "yugabyte", "Hackathon22!");

    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    // standard getters/setter
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}