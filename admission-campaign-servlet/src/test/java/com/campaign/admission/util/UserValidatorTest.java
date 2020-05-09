package com.campaign.admission.util;

import com.campaign.admission.domain.User;
import com.campaign.admission.exception.UserValidatorRuntimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.campaign.admission.domain.User.builder;
import static com.campaign.admission.util.UserValidator.validateEmail;
import static com.campaign.admission.util.UserValidator.validatePassword;
import static org.junit.rules.ExpectedException.none;

public class UserValidatorTest {

    @Rule
    public ExpectedException expectedException = none();

    private final User user = builder()
            .withPassword("_M�;Z�e�\u001D�'\u07B8�ϙ")
            .build();

    private final User userCheck = builder()
            .withEmail("email@com.ua")
            .withPassword("password")
            .build();

    @Test
    public void validatePasswordShouldNotThrowException() {
        validatePassword(userCheck, user);
    }

    @Test
    public void validatePasswordShouldThrowException() {
        expectedException.expect(UserValidatorRuntimeException.class);
        expectedException.expectMessage("Wrong password!");

        validatePassword(userCheck, userCheck);
    }

    @Test
    public void validateEmailShouldNotThrowException() {
        validateEmail("email@com.ua");
    }

    @Test
    public void validateEmailShouldThrowException() {
        expectedException.expect(UserValidatorRuntimeException.class);
        expectedException.expectMessage("Wrong email!");

        validateEmail("email@com");
    }
}
