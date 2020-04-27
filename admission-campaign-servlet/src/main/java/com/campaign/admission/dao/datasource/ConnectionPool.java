package com.campaign.admission.dao.datasource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {

    private static Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);

    private final BasicDataSource dataSource;

    public ConnectionPool(String fileName) {
        ResourceBundle databaseProperties = ResourceBundle.getBundle(fileName);
        dataSource = new BasicDataSource();
        dataSource.setUsername(databaseProperties.getString("db.connection.user"));
        dataSource.setPassword(databaseProperties.getString("db.connection.password"));
        dataSource.setDriverClassName(databaseProperties.getString("db.connection.driver"));
        dataSource.setUrl(databaseProperties.getString("db.connection.url"));
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Getting database connection failure!");
            throw new RuntimeException(e);
        }
    }
}
