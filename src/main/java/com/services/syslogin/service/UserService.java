package com.services.syslogin.service;

import com.services.syslogin.repository.UserRepository;
import com.services.syslogin.service.utils.EncryptDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserService {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    public boolean emailValidate(String email) {
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void validateRememberMeCookie(String cookie) throws Exception {
        String decCookie = encryptDecrypt.decryptData(cookie);

        System.out.println(decCookie);
    };


}
