package com.campaign.admission.controller.command;

import com.campaign.admission.domain.User;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import com.campaign.admission.service.UserService;
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
public class RegistrationCommandTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RegistrationCommand registrationCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    private final Exception exception = new UserValidatorRuntimeException();

    @Test
    public void executeShouldRedirectHome() {
        String query = "query";
        request.setQueryString(query);
        when(userService.register(any(User.class))).thenThrow(exception);
        assertThat(registrationCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/home?" + query));

        verify(userService).register(any(User.class));
        verify(session).setAttribute("exception", exception);
    }
}
