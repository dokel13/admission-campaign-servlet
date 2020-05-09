package com.campaign.admission.controller.command;

import com.campaign.admission.service.StudentService;
import com.campaign.admission.testUtils.TestHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SpecialtyCommandTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private SpecialtyCommand specialtyCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    @Test
    public void executeShouldReturnSpecialtyPage() {
        request.setQueryString("");
        assertThat(specialtyCommand.execute(request.getRequest(any(HttpSession.class))),
                is("/WEB-INF/jsp/student/specialty.jsp"));

        verify(studentService).getSpecialty(any(String.class));
    }
}
