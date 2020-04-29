package com.campaign.admission.dao.mapper;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMapper implements Mapper {

    @Override
    public Application map(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .withName(resultSet.getString("name"))
                .withSurname(resultSet.getString("surname"))
                .withEmail(resultSet.getString("email"))
                .build();
        Specialty specialty = Specialty.builder()
                .withName(resultSet.getString("specialty"))
                .build();

        return Application.builder()
                .withUser(user)
                .withSpecialty(specialty)
                .withEnrollment(resultSet.getBoolean("enrollment"))
                .withMarkSum(resultSet.getInt("mark_sum"))
                .build();
    }
}
