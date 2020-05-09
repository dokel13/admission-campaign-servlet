package com.campaign.admission.controller.command;

import com.campaign.admission.service.AdminService;
import com.campaign.admission.testUtils.TestHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminHomeCommandTest {

    @Mock
    private AdminService adminService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AdminHomeCommand adminHomeCommand;

    @Spy
    private final TestHttpServletRequest request = new TestHttpServletRequest();

    private final List<String> subjects = asList("subject1", "subject2", "subject3");

    @Test
    public void executeShouldReturnAdminPage() {
        when(adminService.getAllSubjects()).thenReturn(subjects);
        assertThat(adminHomeCommand.execute(request.getRequest(session)),
                is("/WEB-INF/jsp/admin/home.jsp"));

        verify(adminService).getAllSubjects();
        verify(request).setAttribute("subjects", subjects);
    }
}
