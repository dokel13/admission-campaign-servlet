package com.campaign.admission.dao.mapper;

import com.campaign.admission.domain.Role;
import com.campaign.admission.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.campaign.admission.domain.User.builder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @Mock
    private ResultSet resultSet;

    private final UserMapper userMapper = new UserMapper();

    private final User user = builder()
            .withRole(Role.STUDENT)
            .withEmail("email")
            .withPassword("password")
            .withName("name")
            .withSurname("surname")
            .build();

    @Test
    public void mapShouldReturnUser() {
        try {
            when(resultSet.getString("role")).thenReturn(Role.STUDENT.name());
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getString("name")).thenReturn("name");
            when(resultSet.getString("surname")).thenReturn("surname");

            assertThat(userMapper.map(resultSet), is(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
