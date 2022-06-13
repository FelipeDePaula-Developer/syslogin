package com.services.syslogin.service.utils.web;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.net.http.HttpResponse;
import java.util.Enumeration;

@Component
public class SessionFuncs {

    public void setPersitentCookie(String cookieName, String cookieValue, HttpServletResponse response){
        response.setContentType("text/html");

        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(604800);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public void setNonPersitentCookie(String cookieName, String cookieValue, HttpServletResponse response){
        response.setContentType("text/html");

        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

}
