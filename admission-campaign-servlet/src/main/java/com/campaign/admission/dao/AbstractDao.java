package com.campaign.admission.dao;

import com.campaign.admission.dao.datasource.ConnectionPool;
import com.campaign.admission.dao.mapper.Mapper;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.ResourceBundle.getBundle;
import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractDao<T> {

    protected static final Logger LOGGER = getLogger(ConnectionPool.class);

    private final ConnectionPool connectionPool;

    private final ResourceBundle resourceBundle;

    protected AbstractDao() {
        this.connectionPool = new ConnectionPool("database");
        this.resourceBundle = getBundle("queries");
    }

    protected Connection getConnection() {
        return connectionPool.getConnection();
    }

    protected String getSql(String query) {
        return resourceBundle.getString(query);
    }

    protected abstract Mapper<T> getMapper();

    protected Optional<T> constructResult(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return of(getMapper().map(resultSet));
        }

        return empty();
    }

    protected List<T> constructMultivaluedResult(ResultSet resultSet) throws SQLException {
        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(getMapper().map(resultSet));
        }

        return result;
    }

    protected Integer getIntResult(ResultSet resultSet, String column) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getInt(column);
        }

        return 0;
    }

    protected List<String> constructMultivaluedStrResult(ResultSet resultSet, String column) throws SQLException {
        List<String> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(resultSet.getString(column));
        }

        return result;
    }
}
