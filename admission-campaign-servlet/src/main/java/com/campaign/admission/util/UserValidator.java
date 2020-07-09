package com.campaign.admission.util;

import com.campaign.admission.domain.User;
import com.campaign.admission.exception.UserValidatorRuntimeException;

import static com.campaign.admission.util.EncryptionUtils.encrypt;

public final class UserValidator {

    private static final String EMAIL_REGEX = "^\\w+@\\D+\\.\\D+$";

    public static void validatePassword(User enteringUser, User existingUser) {
        if (!(encrypt(enteringUser.getPassword()).equals(existingUser.getPassword()))) {
            throw new UserValidatorRuntimeException("Wrong password!");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new UserValidatorRuntimeException("Wrong email!", email);
        }
    }
}
