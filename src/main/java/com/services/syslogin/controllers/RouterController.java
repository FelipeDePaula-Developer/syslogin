package com.services.syslogin.controllers;

import com.services.syslogin.service.UserService;
import com.services.syslogin.service.utils.Utils;
import com.services.syslogin.service.utils.web.WebFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RouterController {

    @Autowired
    private WebFuncs webFuncs;
    @Autowired
    private UserService userService;
    @Autowired
    private Utils utils;

    @RequestMapping(value = {"/", "/dashboard"})
    public String index(HttpServletRequest request, HttpSession session) throws Exception {

        Cookie rememberMeCookie = webFuncs.getCookie(request, "remember-me");

        if (rememberMeCookie != null) {
            String cookieValue = webFuncs.decodeURLParams(rememberMeCookie.getValue());
            boolean check = userService.validateRememberMeCookie(cookieValue, request, session);
            if (check) {
                return "pages/dashboard";
            }
        }
        return "pages/sign-in";
    }

    @RequestMapping("/sign-in")
    public String signIn() {
        return "pages/sign-in";
    }

    @RequestMapping("/sign-up")
    public String signUp() {
        return "pages/sign-up";
    }


}
