package com.campaign.admission.service;

import com.campaign.admission.dao.ExamDao;
import com.campaign.admission.dao.SpecialtyDao;
import com.campaign.admission.domain.Exam;

import java.util.List;

import static com.campaign.admission.util.AdmissionValidator.validateAdmissionOpen;

public class AdminServiceImpl implements AdminService {

    private final ExamDao examDao;
    private final SpecialtyDao specialtyDao;

    public AdminServiceImpl(ExamDao examDao, SpecialtyDao specialtyDao) {
        this.examDao = examDao;
        this.specialtyDao = specialtyDao;
    }

    @Override
    public List<String> findAllSubjects() {
        if (validateAdmissionOpen(specialtyDao.findSpecialtiesOpens())) {
            return examDao.findAllSubjects();
        }

        return null;
    }

    @Override
    public void setAdmission(Boolean open) {
        specialtyDao.setAdmission(open);
    }

    @Override
    public List<Exam> findExamsPaginated(String subject, Integer page, Integer pageSize) {
        return examDao.findExamsPaginated(subject, page, pageSize);
    }
}
