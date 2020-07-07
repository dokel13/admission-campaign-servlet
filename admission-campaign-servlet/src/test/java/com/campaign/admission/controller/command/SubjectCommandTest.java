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
public class SubjectCommandTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private SubjectCommand subjectCommand;

    private final TestHttpServletRequest request = new TestHttpServletRequest();

    @Test
    public void executeShouldRedirectSubject() {
        String query = "subject&page=2&locale=ua";
        String resultQuery = "subject&page=1&locale=ua";
        request.setQueryString(query);
        request.setParameter("page", "2");
        assertThat(subjectCommand.execute(request.getRequest(any(HttpSession.class))),
                is("redirect:/admission/api/admin/subject?" + resultQuery));

        verify(adminService).countBySubjectAndApplicationIsNull("subject");
    }

    @Test
    public void executeShouldReturnSubjectPage() {
        String query = "subject";
        request.setQueryString(query);
        request.setParameter("page", "1");
        assertThat(subjectCommand.execute(request.getRequest(any(HttpSession.class))),
                is("/WEB-INF/jsp/admin/subject.jsp"));

        verify(adminService).countBySubjectAndApplicationIsNull(query);
        verify(adminService).getExamsPaginated(query, 0, 3);
    }
}
