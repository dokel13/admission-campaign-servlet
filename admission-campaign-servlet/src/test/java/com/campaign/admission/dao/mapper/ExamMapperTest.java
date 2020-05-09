package com.campaign.admission.dao.mapper;

import com.campaign.admission.domain.Exam;
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
public class ExamMapperTest {

    @Mock
    private ResultSet resultSet;

    private final ExamMapper examMapper = new ExamMapper();

    private final User user = User.builder()
            .withName("name")
            .withSurname("surname")
            .withEmail("email")
            .build();

    private final Exam exam = Exam.builder()
            .withMark(160)
            .withSubject("subject")
            .withUser(user)
            .build();

    @Test
    public void mapShouldReturnExam() {
        try {
            when(resultSet.getString("name")).thenReturn("name");
            when(resultSet.getString("surname")).thenReturn("surname");
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getInt("mark")).thenReturn(160);
            when(resultSet.getString("subject")).thenReturn("subject");

            assertThat(examMapper.map(resultSet), is(exam));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
