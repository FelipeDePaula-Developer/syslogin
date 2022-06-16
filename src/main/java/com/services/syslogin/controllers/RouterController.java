package com.services.syslogin.controllers;

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

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpSession session) {

        Cookie cookieUserEmail =  webFuncs.getCookie(request, "userEmail");
        Cookie cookieUserName = webFuncs.getCookie(request, "userName");

        if (cookieUserName != null && cookieUserEmail != null){
            session.setAttribute(cookieUserEmail.getName(), cookieUserEmail.getValue());
            session.setAttribute(cookieUserName.getName(), cookieUserName.getValue());
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
