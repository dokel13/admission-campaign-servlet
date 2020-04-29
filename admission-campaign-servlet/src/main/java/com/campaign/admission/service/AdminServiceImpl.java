package com.campaign.admission.service;

import com.campaign.admission.dao.ApplicationDao;
import com.campaign.admission.dao.ExamDao;
import com.campaign.admission.dao.SpecialtyDao;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.User;

import java.util.List;
import java.util.stream.IntStream;

import static com.campaign.admission.util.AdmissionValidator.validateAdmissionOpen;
import static java.lang.Integer.parseInt;
import static java.lang.Math.min;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

public class AdminServiceImpl implements AdminService {

    private final ExamDao examDao;
    private final SpecialtyDao specialtyDao;
    private final ApplicationDao applicationDao;

    public AdminServiceImpl(ExamDao examDao, SpecialtyDao specialtyDao, ApplicationDao applicationDao) {
        this.examDao = examDao;
        this.specialtyDao = specialtyDao;
        this.applicationDao = applicationDao;
    }

    @Override
    public List<String> getAllSubjects() {
        if (validateAdmissionOpen(specialtyDao.findSpecialtiesOpens())) {
            return examDao.findAllSubjects();
        }

        return null;
    }

    @Override
    public void setAdmission(Boolean open) {
        if (open == true) {
            applicationDao.setAllEnrollments(false);
        } else if (open == false) {
            applicationDao.setEnrollmentsBySpecialties(true, specialtyDao.findAllSpecialties());
        }
        specialtyDao.setAdmission(open);
    }

    @Override
    public List<Exam> getExamsPaginated(String subject, Integer page, Integer pageSize) {
        if (validateAdmissionOpen(specialtyDao.findSpecialtiesOpens())) {
            return examDao.findExamsPaginated(subject, page, pageSize);
        }

        return null;
    }

    @Override
    public Integer countExamsBySubject(String subject) {
        return examDao.findExamsCountBySubject(subject);
    }

    @Override
    public void saveMarks(String[] emails, String[] marks) {
        examDao.setMarks(IntStream.range(0, min(emails.length, marks.length))
                .mapToObj(i -> Exam.builder()
                        .withUser(User.builder().withEmail(emails[i]).build())
                        .withMark(parseInt(of((marks[i])).filter(j -> !j.equals("")).orElse("0")))
                        .build()).collect(toList()));
    }

    @Override
    public Boolean checkAdmission() {
        return validateAdmissionOpen(specialtyDao.findSpecialtiesOpens());
    }
}
