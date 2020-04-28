package com.campaign.admission.service;

import com.campaign.admission.domain.Exam;

import java.util.List;

public interface AdminService {

    List<String> findAllSubjects();

    void setAdmission(Boolean open);

    List<Exam> findExamsPaginated(String subject, Integer page, Integer pageSize);

    Integer countExamsBySubject(String subject);

    void saveMarks(String[] emails, String[] marks);

    Boolean checkAdmission();
}
