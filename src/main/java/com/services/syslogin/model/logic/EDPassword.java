package com.services.syslogin.model.logic;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EDPassword {
    String EncryptPassword(String password) throws java.security.GeneralSecurityException;
}
