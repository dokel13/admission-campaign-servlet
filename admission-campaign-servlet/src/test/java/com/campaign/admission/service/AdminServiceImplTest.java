package com.campaign.admission.service;

import com.campaign.admission.dao.ApplicationDao;
import com.campaign.admission.dao.ExamDao;
import com.campaign.admission.dao.SpecialtyDao;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {

    @Mock
    private ExamDao examDao;

    @Mock
    private SpecialtyDao specialtyDao;

    @Mock
    private ApplicationDao applicationDao;

    @InjectMocks
    private AdminServiceImpl adminService;

    private final List<Boolean> opensTrue = asList(true, true, true);

    private final List<Boolean> opensFalse = asList(false, false, false);

    private final String subject = "subject";

    @Test
    public void getAllSubjectsShouldReturnList() {
        List<String> subjects = new ArrayList<>();
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensTrue);
        when(examDao.findAllSubjects()).thenReturn(subjects);
        assertThat(adminService.getAllSubjects(), is(subjects));

        verify(specialtyDao).findSpecialtiesOpens();
        verify(examDao).findAllSubjects();
    }

    @Test
    public void getAllSubjectsShouldDoNothing() {
        List<String> subjects = null;
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensFalse);
        assertThat(adminService.getAllSubjects(), is(subjects));

        verify(specialtyDao).findSpecialtiesOpens();
    }

    @Test
    public void setAdmissionWithOpen() {
        adminService.setAdmission(true);

        verify(applicationDao).setAllEnrollments(any(Boolean.class));
        verify(specialtyDao).setAdmission(true);
    }

    @Test
    public void setAdmissionWithClose() {
        List<Specialty> specialties = new ArrayList<>();
        when(specialtyDao.findAllSpecialties()).thenReturn(specialties);
        adminService.setAdmission(false);

        verify(specialtyDao).findAllSpecialties();
        verify(applicationDao).setEnrollmentsBySpecialties(true, specialties);
        verify(specialtyDao).setAdmission(false);
    }

    @Test
    public void getExamsPaginatedShouldReturnList() {
        List<Exam> exams = new ArrayList<>();
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensTrue);
        assertThat(adminService.getExamsPaginated(subject, 1, 3), is(exams));

        verify(examDao).findExamsPaginated(subject, 1, 3);
    }

    @Test
    public void getExamsPaginatedShouldDoNothing() {
        List<Exam> exams = null;
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensFalse);
        assertThat(adminService.getExamsPaginated(subject, 1, 3), is(exams));

        verify(examDao, never()).findExamsPaginated(any(String.class), any(Integer.class), any(Integer.class));
    }

    @Test
    public void countExamsBySubjectShouldReturnCount() {
        int count = 3;
        when(examDao.findExamsCountBySubject(subject)).thenReturn(count);
        assertThat(adminService.countBySubjectAndApplicationIsNull(subject), is(count));

        verify(examDao).findExamsCountBySubject(subject);
    }

    @Test
    public void saveMarksShouldSetMarks() {
        String[] emails = {"email1", "email2", "email3"};
        String[] marks = {"170", "180", "190"};
        List<Exam> expectedExams = of(Exam.builder().withMark(170).withUser(User.builder().withEmail("email1").build()).build(),
                Exam.builder().withMark(180).withUser(User.builder().withEmail("email2").build()).build(),
                Exam.builder().withMark(190).withUser(User.builder().withEmail("email3").build()).build())
                .collect(toList());
        adminService.saveMarks(subject, emails, marks);

        verify(examDao).setMarks(subject, expectedExams);
    }

    @Test
    public void checkAdmissionShouldReturnAdmission() {
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensTrue);
        assertThat(adminService.checkAdmission(), is(true));

        verify(specialtyDao).findSpecialtiesOpens();
    }
}
