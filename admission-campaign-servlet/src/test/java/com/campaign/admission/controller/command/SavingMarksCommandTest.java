package com.campaign.admission.controller.command;

import com.campaign.admission.service.AdminService;
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
public class SavingMarksCommandTest {

    @Mock
    private AdminService adminService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private SavingMarksCommand savingMarksCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    @Test
    public void executeShouldRedirectSubject() {
        String query = "query";
        request.setQueryString(query);
        assertThat(savingMarksCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/admin/subject?" + query));

        verify(adminService).saveMarks(any(String.class), any(String[].class), any(String[].class));
    }
}
