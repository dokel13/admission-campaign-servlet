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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentHomeCommandTest {

    @Mock
    private StudentService studentService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private StudentHomeCommand studentHomeCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    @Test
    public void executeShouldReturnHomePage() {
        when(session.getAttribute("email")).thenReturn("email");
        assertThat(studentHomeCommand.execute(request.getRequest(session)),
                is("/WEB-INF/jsp/student/home.jsp"));

        verify(studentService).getApplication(any(String.class));
        verify(session).getAttribute(any(String.class));
    }
}
