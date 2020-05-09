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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdmissionCommandTest {

    @Mock
    private AdminService adminService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AdmissionCommand admissionCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    private final Boolean open = true;

    @Test
    public void executeWithOpenShouldRedirectAdmin() {
        request.setParameter("open", open.toString());
        String query = "query";
        request.setQueryString(query);
        when(adminService.checkAdmission()).thenReturn(open);
        assertThat(admissionCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/admin?" +
                        query.replace("open=" + open + "&", "")));

        verify(adminService).setAdmission(open);
        verify(adminService).checkAdmission();
    }

    @Test
    public void executeWithNullOpenShouldRedirectAdmin() {
        String query = "query";
        request.setQueryString(query);
        when(adminService.checkAdmission()).thenReturn(open);
        assertThat(admissionCommand.execute(request.getRequest(session)),
                is("redirect:/admission/api/admin?" +
                        query.replace("open=" + open + "&", "")));

        verify(adminService, never()).setAdmission(open);
        verify(adminService).checkAdmission();
    }
}
