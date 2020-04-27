package com.campaign.admission.service;

import com.campaign.admission.dao.UserDao;
import com.campaign.admission.domain.User;
import com.campaign.admission.exception.ServiceRuntimeException;

import static com.campaign.admission.util.EncryptionUtils.encrypt;
import static com.campaign.admission.util.UserValidator.validateEmail;
import static com.campaign.admission.util.UserValidator.validatePassword;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(User enteringUser) {
        User existingUser = userDao.findByEmail(enteringUser.getEmail()).orElseThrow(() ->
                new ServiceRuntimeException("Login exception! User doesn`t exist!"));
        validatePassword(enteringUser, existingUser);

        return existingUser;
    }

    public User register(User user) {
        validateEmail(user.getEmail());
        userDao.findByEmail(user.getEmail()).ifPresent(user1 -> {
            throw new ServiceRuntimeException("Registration exception! User already exists!");
        });
        user.setPassword(encrypt(user.getPassword()));

        return userDao.save(user);
    }
}
