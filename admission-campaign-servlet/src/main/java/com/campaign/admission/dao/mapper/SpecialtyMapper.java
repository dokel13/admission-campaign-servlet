package com.campaign.admission.dao.mapper;

import com.campaign.admission.domain.Requirement;
import com.campaign.admission.domain.Specialty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.campaign.admission.domain.Specialty.builder;

public class SpecialtyMapper implements Mapper {

    @Override
    public Specialty map(ResultSet resultSet) throws SQLException {
        List<Requirement> requirements = new ArrayList<>();
        String specialty = resultSet.getString("specialty");
        do {
            Requirement requirement = new Requirement(resultSet.getString("subject"),
                    resultSet.getInt("mark"));
            requirements.add(requirement);
        } while (resultSet.next() && specialty.equals(resultSet.getString("specialty")));
        resultSet.previous();

        return builder()
                .withName(resultSet.getString("specialty"))
                .withMaxStudentAmount(resultSet.getInt("max_student_amount"))
                .withOpen(resultSet.getBoolean("open"))
                .withRequirements(requirements)
                .build();
    }
}
