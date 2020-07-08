package com.campaign.admission.util;

import org.junit.Test;

import static com.campaign.admission.util.EncryptionUtils.encrypt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EncryptionUtilsTest {

    @Test
    public void encryptShouldReturnEncryptedPassword() {
        String actual = encrypt("password");
        String expected = encrypt("password");

        assertThat(actual, is(expected));
    }
}
