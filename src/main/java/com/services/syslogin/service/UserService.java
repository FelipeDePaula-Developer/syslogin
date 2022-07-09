package com.services.syslogin.service;

import com.services.syslogin.model.UserLogin;
import com.services.syslogin.repository.UserLoginRepository;
import com.services.syslogin.service.utils.Utils;
import com.services.syslogin.service.utils.web.WebFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserService {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    @Autowired
    private UserLoginRepository userLoginRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private WebFuncs webFuncs;

    public boolean emailValidate(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Boolean validateRememberMeCookie(HttpServletRequest request, HttpSession session) throws Exception {

        Cookie rememberMeCookie = webFuncs.getCookie(request, "remember-me");
        if (rememberMeCookie == null) {
           return false;
        }

        String cookie = webFuncs.decodeURLParams(rememberMeCookie.getValue());
        Map<String, String> cookieDec = utils.convertStringToMap(cookie, "&", " = ");
        UserLogin userLogin = userLoginRepository.getUserLoginByUser_KeyAndId_user(cookieDec.get("key"), Integer.parseInt(cookieDec.get("userId")));

        if (userLogin != null) {
            session.setAttribute("username", cookieDec.get("username"));
            userLogin.setLogged("T");
            userLoginRepository.save(userLogin);
            return true;
        }
        return false;
    }
}
