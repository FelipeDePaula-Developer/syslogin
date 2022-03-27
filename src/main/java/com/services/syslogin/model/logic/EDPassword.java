package com.services.syslogin.model.logic;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EDPassword {
    String encryptPassword(String password) throws java.security.GeneralSecurityException;
}
