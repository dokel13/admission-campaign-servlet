package com.campaign.admission.dao;

import com.campaign.admission.domain.Specialty;

import java.util.List;

public interface SpecialtyDao {

    List<String> findAllSpecialtiesNames();

    Specialty findSpecialty(String subject);

    List<Specialty> findAllSpecialties();

    List<Boolean> findSpecialtiesOpens();

    void setAdmission(Boolean open);
}
