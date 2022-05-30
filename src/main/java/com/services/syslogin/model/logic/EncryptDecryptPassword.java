package com.services.syslogin.model.logic;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

@Component
public class EncryptDecryptPassword{
    private  String  jsSalt =   "Bar12345Bar12345" ;
    private Cipher cipher;
    private Key key;

    public EncryptDecryptPassword() throws Exception{
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
//        keyPairGen.initialize(1024);
        this.key = new SecretKeySpec(jsSalt.getBytes(), "AES");
        this.cipher = Cipher.getInstance("AES");
    }

    public String encryptPassword(String password) throws Exception {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.key);
        byte[] input = password.getBytes();
        this.cipher.update(input);
        byte[] cipherText = this.cipher.doFinal();
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decryptPassword(String encPassword) throws Exception{
        byte[] password = Base64.getDecoder().decode(encPassword.getBytes());
        this.cipher.init(Cipher.DECRYPT_MODE, this.key);
        byte[] decipheredText = this.cipher.doFinal(password);
        return new String(decipheredText);
    }
}

