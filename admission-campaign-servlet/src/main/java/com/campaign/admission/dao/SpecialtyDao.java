package com.campaign.admission.dao;

import com.campaign.admission.domain.Specialty;

import java.util.List;

public interface SpecialtyDao {

    List<String> findAllSpecialties();

    Specialty findSpecialty(String subject);

    List<Boolean> findSpecialtiesOpens();

    void setAdmission(Boolean open);
}
