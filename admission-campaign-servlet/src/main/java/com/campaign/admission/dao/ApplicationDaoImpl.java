package com.campaign.admission.dao;

import com.campaign.admission.dao.datasource.ConnectionPool;
import com.campaign.admission.dao.mapper.ApplicationMapper;
import com.campaign.admission.dao.mapper.Mapper;
import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
            statement.setInt(4, application.getMarkSum());
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

    @Override
    public void setAllEnrollments(Boolean enrollment) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("update.all.enrollments"))) {

            statement.setBoolean(1, enrollment);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Setting all enrollments operation exception!");
        }
    }

    @Override
    public void setEnrollmentsBySpecialties(Boolean enrollment, List<Specialty> specialties) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("update.sorted.enrollments.by.specialties"))) {

            for (Specialty specialty : specialties) {
                statement.setBoolean(1, enrollment);
                statement.setString(2, specialty.getName());
                statement.setInt(3, specialty.getMaxStudentAmount());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Setting enrollments by specialties operation exception!");
        }
    }
}
