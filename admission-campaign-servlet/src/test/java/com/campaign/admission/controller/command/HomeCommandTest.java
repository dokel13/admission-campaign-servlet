package com.campaign.admission.controller.command;

import com.campaign.admission.domain.Role;
import com.campaign.admission.testUtils.TestHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomeCommandTest {

    @Mock
    private HttpSession session;

    private final HomeCommand homeCommand = new HomeCommand();

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    @Test
    public void executeShouldRedirectStudent() {
        String query = "query";
        request.setQueryString(query);
        when(session.getAttribute("role")).thenReturn(Role.STUDENT);
        assertThat(homeCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/student?" + query));

        verify(session).getAttribute(any(String.class));
    }

    @Test
    public void executeShouldRedirectAdmin() {
        String query = "query";
        request.setQueryString(query);
        when(session.getAttribute("role")).thenReturn(Role.ADMIN);
        assertThat(homeCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/admin?" + query));

        verify(session).getAttribute(any(String.class));
    }

    @Test
    public void executeShouldReturnHomePage() {
        when(session.getAttribute("role")).thenReturn(null);
        assertThat(homeCommand.execute(request.getRequest(session)),
                is("/WEB-INF/jsp/home.jsp"));

        verify(session).getAttribute(any(String.class));
    }
}
