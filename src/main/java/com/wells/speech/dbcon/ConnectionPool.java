package com.wells.speech.dbcon;


import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUser();

}
