package com.campaign.admission.dao;

import com.campaign.admission.domain.Specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialtyDao {

    List<String> findAllSpecialtiesNames();

    Optional<Specialty> findSpecialty(String specialty);

    List<Specialty> findAllSpecialties();

    List<Boolean> findSpecialtiesOpens();

    void setAdmission(Boolean open);
}
