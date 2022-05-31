package com.services.syslogin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    @RequestMapping("/")
    public String index() {
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
