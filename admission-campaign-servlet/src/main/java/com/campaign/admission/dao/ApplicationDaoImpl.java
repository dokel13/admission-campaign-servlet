package com.campaign.admission.dao;

import com.campaign.admission.dao.mapper.ApplicationMapper;
import com.campaign.admission.dao.mapper.Mapper;
import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ApplicationDaoImpl extends AbstractDao<Application> implements ApplicationDao {

    @Override
    protected Mapper<Application> getMapper() {
        return new ApplicationMapper();
    }

    @Override
    public void saveApplication(Application application) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("insert.application"))) {

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
    public Optional<Application> findApplicationByEmail(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.application.by.email"))) {

            statement.setString(1, email);

            return constructResult(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding application by email operation exception!");
        }
    }

    @Override
    public List<Application> findApplicationsPaginatedBySpecialty(String specialty, Integer page, Integer pageSize) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.applications.paginated.by.specialty"))) {

            statement.setString(1, specialty);
            statement.setInt(2, (page * pageSize));
            statement.setInt(3, pageSize);

            return constructMultivaluedResult(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding applications paginated by specialty operation exception!");
        }
    }

    @Override
    public Integer findApplicationsCountBySpecialty(String specialty) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.applications.count.by.specialty"))) {

            statement.setString(1, specialty);

            return getIntResult(statement.executeQuery(), "count");
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding applications count by specialty operation exception!");
        }
    }

    @Override
    public void setAllEnrollments(Boolean enrollment) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("update.all.enrollments"))) {

            statement.setBoolean(1, enrollment);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Setting all enrollments operation exception!");
        }
    }

    @Override
    public void setEnrollmentsBySpecialties(Boolean enrollment, List<Specialty> specialties) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("update.sorted.enrollments.by.specialties"))) {

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
