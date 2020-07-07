package com.campaign.admission.dao.mapper;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.campaign.admission.domain.Exam.builder;

public class ExamMapper implements Mapper<Exam> {

    @Override
    public Exam map(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .withName(resultSet.getString("name"))
                .withSurname(resultSet.getString("surname"))
                .withEmail(resultSet.getString("email"))
                .build();

        return builder().withMark(resultSet.getInt("mark"))
                .withSubject(resultSet.getString("subject"))
                .withUser(user)
                .build();
    }
}
