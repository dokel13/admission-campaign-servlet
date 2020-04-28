package com.campaign.admission.dao;

import com.campaign.admission.dao.datasource.ConnectionPool;
import com.campaign.admission.dao.mapper.Mapper;
import com.campaign.admission.dao.mapper.SpecialtyMapper;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

public class SpecialtyDaoImpl implements SpecialtyDao {

    private final ConnectionPool connectionPool;

    private final ResourceBundle resourceBundle;

    public SpecialtyDaoImpl() {
        connectionPool = new ConnectionPool("database");
        resourceBundle = getBundle("queries");
    }

    private Mapper<Specialty> getMapper() {
        return new SpecialtyMapper();
    }

    @Override
    public List<String> findAllSpecialtiesNames() {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.all.specialties.names"))) {

            ResultSet resultSet = statement.executeQuery();
            List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("specialty"));
            }

            return result;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding all specialties names operation exception!");
        }
    }

    @Override
    public Specialty findSpecialty(String specialty) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.specialty"))) {

            statement.setString(1, specialty);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                return getMapper().map(resultSet);
            }

            return null;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding specialty operation exception!");
        }
    }

    @Override
    public List<Specialty> findAllSpecialties() {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.all.specialties"))) {

            ResultSet resultSet = statement.executeQuery();
            List<Specialty> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getMapper().map(resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding all specialties operation exception!");
        }
    }

    @Override
    public List<Boolean> findSpecialtiesOpens() {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.specialties.opens"))) {

            ResultSet resultSet = statement.executeQuery();
            List<Boolean> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getBoolean("open"));
            }

            return result;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding specialties' opens operation exception!");
        }
    }

    @Override
    public void setAdmission(Boolean open) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("update.specialties.open"))) {

            statement.setBoolean(1, open);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Setting admission operation exception!");
        }
    }
}
