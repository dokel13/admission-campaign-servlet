package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Specialty;
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
public class RatingCommandTest {

    @Mock
    private StudentService studentService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RatingCommand ratingCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    private final Application application = Application.builder()
            .withSpecialty(Specialty.builder().withName("name").build())
            .build();

    @Test
    public void executeShouldRedirectHome() {
        String query = "page=1&locale=ua";
        String resultQuery = "locale=ua";
        request.setQueryString(query);
        when(session.getAttribute("email")).thenReturn("email");
        when(studentService.getApplication(any(String.class))).thenReturn(null);
        assertThat(ratingCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/home?" + resultQuery));

        verify(session).getAttribute(any(String.class));
        verify(studentService).getApplication(any(String.class));
    }

    @Test
    public void executeShouldRedirectRating() {
        String query = "page=2&locale=ua";
        String resultQuery = "page=1&locale=ua";
        request.setQueryString(query);
        request.setParameter("page", "2");
        when(session.getAttribute("email")).thenReturn("email");
        when(studentService.getApplication(any(String.class))).thenReturn(application);
        assertThat(ratingCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/student/rating?" + resultQuery));

        verify(session).getAttribute(any(String.class));
        verify(studentService).getApplication(any(String.class));
        verify(studentService).countApplicationsBySpecialty(any(String.class));
    }

    @Test
    public void executeShouldReturnRatingPage() {
        when(session.getAttribute("email")).thenReturn("email");
        when(studentService.getApplication(any(String.class))).thenReturn(application);
        assertThat(ratingCommand.execute(request.getRequest(session)),
                is("/WEB-INF/jsp/student/rating.jsp"));

        verify(session).getAttribute(any(String.class));
        verify(studentService).getApplication(any(String.class));
        verify(studentService).countApplicationsBySpecialty(any(String.class));
        verify(studentService).getApplicationsPaginated(any(String.class), any(Integer.class), any(Integer.class));
    }
}
