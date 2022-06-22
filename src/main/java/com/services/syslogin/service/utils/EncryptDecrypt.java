package com.services.syslogin.service.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

@Component
public class EncryptDecrypt {
    private String key = "A%D*G-KaPdSgVkXp";
    private Cipher cipher;
    private Key AESkey;

    public EncryptDecrypt() throws Exception {
        this.AESkey = new SecretKeySpec(key.getBytes(), "AES");
        this.cipher = Cipher.getInstance("AES");
    }

    public static String genKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secRandom = new SecureRandom();
        keyGen.init(secRandom);
        byte[] key = keyGen.generateKey().getEncoded();
        return Base64.getEncoder().encodeToString(key);
    }

    public String encryptPassword(String data) throws Exception {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.AESkey);
        byte[] input = data.getBytes();
        this.cipher.update(input);
        byte[] cipherText = this.cipher.doFinal();
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decryptPassword(String encData) throws Exception {
        byte[] password = Base64.getDecoder().decode(encData.getBytes());
        this.cipher.init(Cipher.DECRYPT_MODE, this.AESkey);
        byte[] decipheredText = this.cipher.doFinal(password);
        return new String(decipheredText);
    }
}

