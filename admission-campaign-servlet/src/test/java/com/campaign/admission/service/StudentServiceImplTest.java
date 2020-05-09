package com.campaign.admission.service;

import com.campaign.admission.dao.ApplicationDao;
import com.campaign.admission.dao.ExamDao;
import com.campaign.admission.dao.SpecialtyDao;
import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Requirement;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.exception.ServiceRuntimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.campaign.admission.domain.Specialty.builder;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceImplTest {

    @Mock
    private ExamDao examDao;

    @Mock
    private SpecialtyDao specialtyDao;

    @Mock
    private ApplicationDao applicationDao;

    @Rule
    public ExpectedException expectedException = none();

    @InjectMocks
    private StudentServiceImpl studentService;

    private final List<Boolean> opensTrue = asList(true, true, true);

    private final List<Boolean> opensFalse = asList(false, false, false);

    private final String email = "email";

    private final Specialty specialty = builder().withName("specialty").build();

    @Test
    public void getUserFreeSubjectsShouldReturnList() {
        List<String> subjects = asList("subject", "subject");
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensTrue);
        when(examDao.findUserFreeSubjects(any(String.class))).thenReturn(subjects);
        assertThat(studentService.getUserFreeSubjects(email), is(subjects));

        verify(specialtyDao).findSpecialtiesOpens();
        verify(examDao).findUserFreeSubjects(email);
    }

    @Test
    public void getUserFreeSubjectsShouldThrowNullSizeException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Finding subjects exception! No user free subjects are found!");
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensTrue);
        when(examDao.findUserFreeSubjects(any(String.class))).thenReturn(new ArrayList<>());
        try {
            studentService.getUserFreeSubjects(email);
        } finally {
            verify(specialtyDao).findSpecialtiesOpens();
            verify(examDao).findUserFreeSubjects(email);
        }
    }

    @Test
    public void getUserFreeSubjectsShouldThrowAdmissionException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Admission is closed!");
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensFalse);
        try {
            studentService.getUserFreeSubjects(email);
        } finally {
            verify(specialtyDao).findSpecialtiesOpens();
        }
    }

    @Test
    public void saveExamSubjectsShouldSave() {
        studentService.saveExamSubjects(new String[0], email);

        verify(examDao).saveExams(new ArrayList<>());
    }

    @Test
    public void saveExamSubjectsShouldDoNothing() {
        studentService.saveExamSubjects(null, null);

        verify(examDao, never()).saveExams(null);
    }

    @Test
    public void getAllSpecialtiesShouldReturnList() {
        List<String> specialties = new ArrayList<>();
        when(specialtyDao.findAllSpecialtiesNames()).thenReturn(specialties);
        assertThat(studentService.getAllSpecialties(), is(specialties));

        verify(specialtyDao).findAllSpecialtiesNames();
    }

    @Test
    public void getSpecialtyShouldReturnSpecialty() {
        when(specialtyDao.findSpecialty(any(String.class))).thenReturn(of(specialty));
        assertThat(studentService.getSpecialty(specialty.getName()), is(specialty));

        verify(specialtyDao).findSpecialty(specialty.getName());
    }

    @Test
    public void getSpecialtyShouldThrowException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Finding specialty database exception!");
        try {
            studentService.getSpecialty(specialty.getName());
        } finally {
            verify(specialtyDao).findSpecialty(specialty.getName());
        }
    }

    @Test
    public void getResultsShouldReturnList() {
        assertThat(studentService.getResults(email), is(new ArrayList<>()));

        verify(examDao).findExamsByEmail(email);
    }

    @Test
    public void getApplicationShouldDoNoting() {
        Application application = null;
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensTrue);
        assertThat(studentService.getApplication(email), is(application));

        verify(specialtyDao).findSpecialtiesOpens();
    }

    @Test
    public void getApplicationShouldReturnApplication() {
        Application application = Application.builder().build();
        when(specialtyDao.findSpecialtiesOpens()).thenReturn(opensFalse);
        when(applicationDao.findApplicationByEmail(email)).thenReturn(of(application));
        assertThat(studentService.getApplication(email), is(application));

        verify(specialtyDao).findSpecialtiesOpens();
        verify(applicationDao).findApplicationByEmail(email);
    }

    @Test
    public void countApplicationsBySpecialtyShouldReturnCount() {
        int count = 3;
        when(applicationDao.findApplicationsCountBySpecialty(specialty.getName())).thenReturn(count);
        assertThat(studentService.countApplicationsBySpecialty(specialty.getName()), is(count));

        verify(applicationDao).findApplicationsCountBySpecialty(specialty.getName());
    }

    @Test
    public void getApplicationsPaginatedShouldReturnList() {
        int page = 2;
        int pageSize = 3;
        List<Application> applications = new ArrayList<>();
        when(applicationDao.findApplicationsPaginatedBySpecialty(specialty.getName(), page, pageSize)).thenReturn(applications);
        assertThat(studentService.getApplicationsPaginated(specialty.getName(), page, pageSize), is(applications));

        verify(applicationDao).findApplicationsPaginatedBySpecialty(specialty.getName(), page, pageSize);
    }

    @Test
    public void specialtyApplyShouldReturnSpecialty() {
        Specialty specialty = builder().withName("specialty").withOpen(true)
                .withRequirements(singletonList(new Requirement("subject", 160))).build();
        List<Exam> exams = singletonList(Exam.builder().withSubject("subject").withMark(170).build());
        when(specialtyDao.findSpecialty(specialty.getName())).thenReturn(of(specialty));
        when(examDao.findExamsByEmail(email)).thenReturn(exams);
        assertThat(studentService.specialtyApply(email, specialty.getName()), is(specialty.getName()));

        verify(specialtyDao).findSpecialty(specialty.getName());
        verify(applicationDao).findApplicationByEmail(email);
        verify(examDao).findExamsByEmail(email);
        verify(applicationDao).saveApplication(any(Application.class));
    }

    @Test
    public void specialtyApplyShouldThrowDatabaseException() {
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Finding specialty database exception!");
        try {
            studentService.specialtyApply(email, specialty.getName());
        } finally {
            verify(specialtyDao).findSpecialty(specialty.getName());
        }
    }

    @Test
    public void specialtyApplyShouldThrowAdmissionException() {
        Specialty specialty = builder().withName("specialty").withOpen(false).build();
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("Admission is closed!");
        when(specialtyDao.findSpecialty(specialty.getName())).thenReturn(of(specialty));
        try {
            studentService.specialtyApply(email, specialty.getName());
        } finally {
            verify(specialtyDao).findSpecialty(specialty.getName());
        }
    }

    @Test
    public void specialtyApplyShouldThrowExistingApplicationException() {
        Specialty specialty = builder().withName("specialty").withOpen(true).build();
        expectedException.expect(ServiceRuntimeException.class);
        expectedException.expectMessage("User already has application!");
        when(specialtyDao.findSpecialty(specialty.getName())).thenReturn(of(specialty));
        when(applicationDao.findApplicationByEmail(email)).thenReturn(of(Application.builder().build()));
        try {
            studentService.specialtyApply(email, specialty.getName());
        } finally {
            verify(specialtyDao).findSpecialty(specialty.getName());
            verify(applicationDao).findApplicationByEmail(email);
        }
    }
}
