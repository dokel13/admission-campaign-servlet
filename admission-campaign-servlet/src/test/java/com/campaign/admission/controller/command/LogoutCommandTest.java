package com.campaign.admission.controller.command;

import com.campaign.admission.testUtils.TestHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogoutCommandTest {

    @Mock
    private HttpSession session;

    private final LogoutCommand logoutCommand = new LogoutCommand();

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    @Test
    public void executeShouldRedirectHome() {
        assertThat(logoutCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/home"));

        verify(session).invalidate();
    }
}
