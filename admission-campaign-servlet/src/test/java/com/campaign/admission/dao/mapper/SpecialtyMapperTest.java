package com.campaign.admission.dao.mapper;

import com.campaign.admission.domain.Requirement;
import com.campaign.admission.domain.Specialty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpecialtyMapperTest {

    @Mock
    private ResultSet resultSet;

    private final SpecialtyMapper specialtyMapper = new SpecialtyMapper();

    private final List<Requirement> requirements = of(new Requirement("subject", 150),
            new Requirement("subject", 150))
            .collect(toList());

    private final Specialty specialty = Specialty.builder()
            .withName("specialty")
            .withMaxStudentAmount(20)
            .withOpen(true)
            .withRequirements(requirements)
            .build();

    @Test
    public void mapShouldReturnSpecialty() {
        try {
            when(resultSet.getString("specialty")).thenReturn("specialty")
                    .thenReturn("specialty").thenReturn("specialtyOne").thenReturn("specialty");
            when(resultSet.getInt("max_student_amount")).thenReturn(20);
            when(resultSet.getBoolean("open")).thenReturn(true);
            when(resultSet.getString("subject")).thenReturn("subject");
            when(resultSet.getInt("mark")).thenReturn(150);
            when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

            assertThat(specialtyMapper.map(resultSet), is(specialty));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
