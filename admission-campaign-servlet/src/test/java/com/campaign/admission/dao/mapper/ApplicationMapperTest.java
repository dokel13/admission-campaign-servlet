package com.campaign.admission.dao.mapper;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationMapperTest {

    @Mock
    private ResultSet resultSet;

    private final ApplicationMapper applicationMapper = new ApplicationMapper();

    private final User user = User.builder()
            .withName("name")
            .withSurname("surname")
            .withEmail("email")
            .build();

    private final Specialty specialty = Specialty.builder()
            .withName("specialty")
            .build();

    private final Application application = Application.builder()
            .withUser(user)
            .withSpecialty(specialty)
            .withEnrollment(true)
            .withMarkSum(500)
            .build();

    @Test
    public void mapShouldReturnApplication() {
        try {
            when(resultSet.getString("name")).thenReturn("name");
            when(resultSet.getString("surname")).thenReturn("surname");
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getString("specialty")).thenReturn("specialty");
            when(resultSet.getBoolean("enrollment")).thenReturn(true);
            when(resultSet.getInt("mark_sum")).thenReturn(500);

            assertThat(applicationMapper.map(resultSet), is(application));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
