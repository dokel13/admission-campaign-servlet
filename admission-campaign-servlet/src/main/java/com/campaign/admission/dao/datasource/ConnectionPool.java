package com.campaign.admission.dao.datasource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;
import static org.slf4j.LoggerFactory.getLogger;

public class ConnectionPool {

    private static Logger LOGGER = getLogger(ConnectionPool.class);

    private final BasicDataSource dataSource;

    public ConnectionPool(String fileName) {
        ResourceBundle databaseProperties = getBundle(fileName);
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
