package com.campaign.admission.controller.command;

import com.campaign.admission.testUtils.TestHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class ErrorCommandTest {

    @Mock
    private HttpSession session;

    private final ErrorCommand errorCommand = new ErrorCommand();

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    @Test
    public void executeShouldReturnErrorPage() {
        assertThat(errorCommand.execute(request.getRequest(session)),
                is("/WEB-INF/jsp/error.jsp"));
    }
}
