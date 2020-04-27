package com.campaign.admission.dao;

import com.campaign.admission.domain.Application;

import java.util.Optional;

public interface ApplicationDao {

    void saveApplication(Application application);

    Optional<Boolean> findEnrollmentByEmail(String email);

    Optional<Integer> findApplicationByEmail(String email);
}
