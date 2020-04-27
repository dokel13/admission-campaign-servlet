package com.campaign.admission.dao;

import com.campaign.admission.domain.User;

import java.util.Optional;

public interface UserDao {

    User save(User user);

    Optional<User> findByEmail(String email);
}
