package com.campaign.admission.dao;

import com.campaign.admission.dao.mapper.Mapper;
import com.campaign.admission.dao.mapper.UserMapper;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.DatabaseRuntimeException;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    @Override
    protected Mapper<User> getMapper() {
        return new UserMapper();
    }

    @Override
    public User save(User user) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("insert.user"),
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getRole().name());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurname());
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }

            return user;
        } catch (SQLException exception) {
            String exceptionMessage = "Saving user operation exception!";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.user.by.email"))) {

            statement.setString(1, email);

            return constructResult(statement.executeQuery());
        } catch (SQLException exception) {
            String exceptionMessage = "Finding user by email operation exception!";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }
}
