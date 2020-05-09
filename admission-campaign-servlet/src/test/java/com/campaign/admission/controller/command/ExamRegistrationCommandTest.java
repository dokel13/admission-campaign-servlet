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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExamRegistrationCommandTest {

    @Mock
    private StudentService studentService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ExamRegistrationCommand examRegistrationCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    @Test
    public void executeShouldRedirectHome() {
        String query = "query";
        request.setQueryString(query);
        String email = "email";
        when(session.getAttribute("email")).thenReturn(email);
        assertThat(examRegistrationCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/home?" + query));

        verify(studentService).saveExamSubjects(request.getParameterValues("subject"), email);
        verify(session).getAttribute("email");
    }
}
