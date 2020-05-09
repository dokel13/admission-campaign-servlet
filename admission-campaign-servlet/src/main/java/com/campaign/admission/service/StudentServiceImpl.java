package com.campaign.admission.service;

import com.campaign.admission.dao.ApplicationDao;
import com.campaign.admission.dao.ExamDao;
import com.campaign.admission.dao.SpecialtyDao;
import com.campaign.admission.dao.datasource.ConnectionPool;
import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;
import org.slf4j.Logger;

import java.util.List;

import static com.campaign.admission.domain.User.builder;
import static com.campaign.admission.util.AdmissionValidator.validateAdmissionOpen;
import static com.campaign.admission.util.AdmissionValidator.validateMarks;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = getLogger(ConnectionPool.class);

    private final ExamDao examDao;
    private final SpecialtyDao specialtyDao;
    private final ApplicationDao applicationDao;

    public StudentServiceImpl(ExamDao examDao, SpecialtyDao specialtyDao, ApplicationDao applicationDao) {
        this.examDao = examDao;
        this.specialtyDao = specialtyDao;
        this.applicationDao = applicationDao;
    }

    @Override
    public List<String> getUserFreeSubjects(String email) {
        if (validateAdmissionOpen(specialtyDao.findSpecialtiesOpens())) {
            List<String> subjects = examDao.findUserFreeSubjects(email);
            if (subjects.size() == 0) {
                throw new ServiceRuntimeException("Finding subjects exception! No user free subjects are found!");
            } else {

                return subjects;
            }
        } else {
            throw new ServiceRuntimeException("Admission is closed!");
        }
    }

    @Override
    public void saveExamSubjects(String[] subjects, String email) {
        if (subjects != null) {
            User user = builder()
                    .withEmail(email)
                    .build();
            examDao.saveExams(stream(subjects).map(subject ->
                    Exam.builder()
                            .withUser(user)
                            .withSubject(subject)
                            .build())
                    .collect(toList()));
        }
    }

    @Override
    public List<String> getAllSpecialties() {
        return specialtyDao.findAllSpecialtiesNames();
    }

    @Override
    public Specialty getSpecialty(String specialty) {
        return specialtyDao.findSpecialty(specialty).orElseGet(() -> {
            LOGGER.error("Finding specialty database exception!");
            throw new ServiceRuntimeException("Finding specialty database exception!");
        });
    }

    @Override
    public List<Exam> getResults(String email) {
        return examDao.findExamsByEmail(email);
    }

    @Override
    public Application getApplication(String email) {
        if (validateAdmissionOpen(specialtyDao.findSpecialtiesOpens())) {
            return null;
        }

        return applicationDao.findApplicationByEmail(email).orElse(null);
    }

    @Override
    public Integer countApplicationsBySpecialty(String specialty) {
        return applicationDao.findApplicationsCountBySpecialty(specialty);
    }

    @Override
    public List<Application> getApplicationsPaginated(String specialty, Integer page, Integer pageSize) {
        return applicationDao.findApplicationsPaginatedBySpecialty(specialty, page, pageSize);
    }

    @Override
    public String specialtyApply(String email, String specialtyName) {
        Specialty specialty = specialtyDao.findSpecialty(specialtyName).orElseThrow(() ->
                new ServiceRuntimeException("Finding specialty database exception!"));
        int markSum = applicationValidator(email, specialty);
        User user = builder()
                .withEmail(email)
                .build();
        applicationDao.saveApplication(Application.builder()
                .withUser(user)
                .withSpecialty(specialty)
                .withMarkSum(markSum)
                .build());

        return specialtyName;
    }

    private Integer applicationValidator(String email, Specialty specialty) {
        if (!specialty.getOpen()) {
            throw new ServiceRuntimeException("Admission is closed!");
        }
        applicationDao.findApplicationByEmail(email).ifPresent(app -> {
            throw new ServiceRuntimeException("User already has application!");
        });

        return validateMarks(examDao.findExamsByEmail(email), specialty.getRequirements());
    }
}
