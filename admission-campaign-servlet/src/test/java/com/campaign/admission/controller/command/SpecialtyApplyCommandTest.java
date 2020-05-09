package com.campaign.admission.controller.command;

import com.campaign.admission.exception.AdmissionValidatorRuntimeException;
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
public class SpecialtyApplyCommandTest {

    @Mock
    private StudentService studentService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private SpecialtyApplyCommand specialtyApplyCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    private final Exception exception = new AdmissionValidatorRuntimeException();

    @Test
    public void executeShouldRedirectSpecialties() {
        String query = "specialty";
        request.setQueryString(query);
        when(session.getAttribute("email")).thenReturn("email");
        when(studentService.specialtyApply(any(String.class), any(String.class))).thenThrow(exception);
        assertThat(specialtyApplyCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/student/specialty?" + query));

        verify(studentService).specialtyApply("email", query);
        verify(session).getAttribute(any(String.class));
        verify(session).setAttribute("exception", exception);
    }

    @Test
    public void executeShouldRedirectHome() {
        String query = "specialty";
        request.setQueryString(query);
        when(session.getAttribute("email")).thenReturn("email");
        assertThat(specialtyApplyCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/home?" + query));

        verify(studentService).specialtyApply("email", query);
        verify(session).getAttribute(any(String.class));
    }
}
