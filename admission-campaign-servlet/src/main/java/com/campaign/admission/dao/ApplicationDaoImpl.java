package com.campaign.admission.dao;

import com.campaign.admission.dao.datasource.ConnectionPool;
import com.campaign.admission.dao.mapper.ApplicationMapper;
import com.campaign.admission.dao.mapper.Mapper;
import com.campaign.admission.domain.Application;
import com.campaign.admission.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.ResourceBundle.getBundle;

public class ApplicationDaoImpl implements ApplicationDao {

    private final ConnectionPool connectionPool;

    private final ResourceBundle resourceBundle;

    public ApplicationDaoImpl() {
        connectionPool = new ConnectionPool("database");
        resourceBundle = getBundle("queries");
    }

    private Mapper<Application> getMapper() {
        return new ApplicationMapper();
    }

    @Override
    public void saveApplication(Application application) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("insert.application"))) {

            statement.setString(1, application.getUser().getEmail());
            statement.setString(2, application.getSpecialty().getName());
            statement.setBoolean(3, false);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Saving application operation exception!");
        }
    }

    @Override
    public Optional<Boolean> findEnrollmentByEmail(String email) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.enrollment.by.email"))) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return of(resultSet.getBoolean("enrollment"));
            }

            return empty();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding enrollment by email operation exception!");
        }
    }

    @Override
    public Optional<Integer> findApplicationByEmail(String email) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.application.by.email"))) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return of(resultSet.getInt("application_id"));
            }

            return empty();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding application by email operation exception!");
        }
    }
}
