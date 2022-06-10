package com.services.syslogin.service.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

@Component
public class EncryptDecryptPassword {
    private String key = "A%D*G-KaPdSgVkXp";
    private Cipher cipher;
    private Key AESkey;

    public EncryptDecryptPassword() throws Exception {
        this.AESkey = new SecretKeySpec(key.getBytes(), "AES");
        this.cipher = Cipher.getInstance("AES");
    }

    public String encryptPassword(String password) throws Exception {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.AESkey);
        byte[] input = password.getBytes();
        this.cipher.update(input);
        byte[] cipherText = this.cipher.doFinal();
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decryptPassword(String encPassword) throws Exception {
        byte[] password = Base64.getDecoder().decode(encPassword.getBytes());
        this.cipher.init(Cipher.DECRYPT_MODE, this.AESkey);
        byte[] decipheredText = this.cipher.doFinal(password);
        return new String(decipheredText);
    }
}

