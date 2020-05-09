package com.campaign.admission.dao;

import com.campaign.admission.domain.Exam;

import java.util.List;

public interface ExamDao {

    List<String> findUserFreeSubjects(String email);

    List<String> findAllSubjects();

    void saveExams(List<Exam> exams);

    List<Exam> findExamsByEmail(String email);

    List<Exam> findExamsPaginated(String subject, Integer page, Integer pageSize);

    Integer findExamsCountBySubject(String subject);

    void setMarks(String subject, List<Exam> exams);
}
