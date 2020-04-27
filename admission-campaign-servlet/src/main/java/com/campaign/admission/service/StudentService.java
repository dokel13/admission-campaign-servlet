package com.campaign.admission.service;

import com.campaign.admission.domain.Exam;
import com.campaign.admission.domain.Specialty;

import java.util.List;

public interface StudentService {

    List<String> findUserFreeSubjects(String email);

    void saveExamSubjects(String[] subjects, String email);

    Boolean checkUserEnrollment(String email);

    List<String> findAllSpecialties();

    Specialty findSpecialty(String specialty);

    List<Exam> findResults(String email);

    void specialtyApply(String email, String specialty);
}
