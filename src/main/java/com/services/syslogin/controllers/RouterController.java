package com.services.syslogin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    @RequestMapping("/")
    public String index() {
        return "dashboard";
    }

    @RequestMapping("pages/sign-in")
    public String signIn() {
        return "pages/sign-in";
    }

    @RequestMapping("pages/sign-up")
    public String signUp() {
        return "pages/sign-up";
    }

}
