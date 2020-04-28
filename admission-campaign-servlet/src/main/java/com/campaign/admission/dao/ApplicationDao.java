package com.campaign.admission.dao;

import com.campaign.admission.domain.Application;
import com.campaign.admission.domain.Specialty;

import java.util.List;
import java.util.Optional;

public interface ApplicationDao {

    void saveApplication(Application application);

    Optional<Boolean> findEnrollmentByEmail(String email);

    Optional<Integer> findApplicationByEmail(String email);

    void setAllEnrollments(Boolean enrollment);

    void setEnrollmentsBySpecialties(Boolean enrollment, List<Specialty> specialties);
}
