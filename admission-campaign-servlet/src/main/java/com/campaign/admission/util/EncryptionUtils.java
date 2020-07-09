package com.campaign.admission.util;

import com.campaign.admission.exception.EncryptorRuntimeException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class EncryptionUtils {

    public static String encrypt(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(value.getBytes());

            return new String(array);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptorRuntimeException(e, "Encryptor Failure!");
        }
    }
}
