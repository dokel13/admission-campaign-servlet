package com.campaign.admission.dao;

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
import java.util.Optional;

public class SpecialtyDaoImpl extends AbstractDao<Specialty> implements SpecialtyDao {

    @Override
    protected Mapper<Specialty> getMapper() {
        return new SpecialtyMapper();
    }

    @Override
    public List<String> findAllSpecialtiesNames() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.all.specialties.names"))) {

            return constructMultivaluedStrResult(statement.executeQuery(), "specialty");
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding all specialties names operation exception!");
        }
    }

    @Override
    public Optional<Specialty> findSpecialty(String specialty) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.specialty"))) {

            statement.setString(1, specialty);

            return constructResult(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding specialty operation exception!");
        }
    }

    @Override
    public List<Specialty> findAllSpecialties() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.all.specialties"))) {

            return constructMultivaluedResult(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding all specialties operation exception!");
        }
    }

    @Override
    public List<Boolean> findSpecialtiesOpens() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.specialties.opens"))) {

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
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("update.specialties.open"))) {

            statement.setBoolean(1, open);
            statement.execute();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Setting admission operation exception!");
        }
    }
}
