package com.services.syslogin.controllers;

import com.services.syslogin.service.UserService;
import com.services.syslogin.service.utils.web.WebFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class RouterController {

    @Autowired
    private WebFuncs webFuncs;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpSession session) throws Exception {

        Cookie rememberMeCookie =  webFuncs.getCookie(request, "remember-me");

        if (rememberMeCookie != null){
            userService.validateRememberMeCookie(rememberMeCookie.getValue());
            return "pages/dashboard";
        }
        return "pages/sign-in";
    }

    @RequestMapping("/sign-in")
    public String signIn() { return "pages/sign-in"; }

    @RequestMapping("/sign-up")
    public String signUp() {
        return "pages/sign-up";
    }

    @RequestMapping("/dashboard")
    public String dashboard() { return "pages/dashboard"; }


}
