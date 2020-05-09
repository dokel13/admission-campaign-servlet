package com.campaign.admission.dao.mapper;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.campaign.admission.domain.User.builder;

public class UserMapper implements Mapper {

    @Override
    public User map(ResultSet resultSet) throws SQLException {

        return builder()
                .withRole(Role.valueOf(resultSet.getString("role")))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .withName(resultSet.getString("name"))
                .withSurname(resultSet.getString("surname"))
                .build();
    }
}
