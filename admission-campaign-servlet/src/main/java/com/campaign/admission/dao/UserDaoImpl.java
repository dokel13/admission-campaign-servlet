package com.campaign.admission.dao;

import com.campaign.admission.dao.datasource.ConnectionPool;
import com.campaign.admission.dao.mapper.Mapper;
import com.campaign.admission.dao.mapper.UserMapper;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.DatabaseRuntimeException;

import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.ResourceBundle.getBundle;

public class UserDaoImpl implements UserDao {

    private final ConnectionPool connectionPool;

    private final ResourceBundle resourceBundle;

    public UserDaoImpl() {
        connectionPool = new ConnectionPool("database");
        resourceBundle = getBundle("queries");
    }

    //    TODO abstract DAO or delete getMapper method
    private Mapper<User> getMapper() {
        return new UserMapper();
    }

    @Override
    public User save(User user) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("insert.user"),
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
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Saving user operation exception!");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.user.by.email"))) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return of(getMapper().map(resultSet));
            }

            return empty();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding user by email operation exception!");
        }
    }
}
