package com.campaign.admission.service;

import com.campaign.admission.dao.ApplicationDao;
import com.campaign.admission.dao.ExamDao;
import com.campaign.admission.dao.SpecialtyDao;
import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Specialty;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;

import java.util.List;

import static com.campaign.admission.domain.User.builder;
import static com.campaign.admission.util.AdmissionValidator.validateAdmissionOpen;
import static com.campaign.admission.util.AdmissionValidator.validateMarks;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class StudentServiceImpl implements StudentService {

    private final ExamDao examDao;
    private final SpecialtyDao specialtyDao;
    private final ApplicationDao applicationDao;

    public StudentServiceImpl(ExamDao examDao, SpecialtyDao specialtyDao, ApplicationDao applicationDao) {
        this.examDao = examDao;
        this.specialtyDao = specialtyDao;
        this.applicationDao = applicationDao;
    }

    @Override
    public List<String> findUserFreeSubjects(String email) {
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
    public Boolean checkUserEnrollment(String email) {
        return applicationDao.findEnrollmentByEmail(email).orElse(false);
    }

    @Override
    public List<String> findAllSpecialties() {
        return specialtyDao.findAllSpecialties();
    }

    @Override
    public Specialty findSpecialty(String specialty) {
        return specialtyDao.findSpecialty(specialty);
    }

    @Override
    public List<Exam> findResults(String email) {
        return examDao.findExamsByEmail(email);
    }

    @Override
    public void specialtyApply(String email, String specialtyName) {
        Specialty specialty = specialtyDao.findSpecialty(specialtyName);
        applicationValidator(email, specialty);
        User user = builder()
                .withEmail(email)
                .build();
        applicationDao.saveApplication(new Application(user, specialty));
    }

    private void applicationValidator(String email, Specialty specialty) {
        if (!specialty.getOpen()) {
            throw new ServiceRuntimeException("Admission is closed!");
        }
        applicationDao.findApplicationByEmail(email).ifPresent(id -> {
            throw new ServiceRuntimeException("User already has application!");
        });
        validateMarks(examDao.findExamsByEmail(email), specialty.getRequirements());
    }
}
