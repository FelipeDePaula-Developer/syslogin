package com.services.syslogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
//@ComponentScan(basePackages={"com.services.syslogin.controllers"})
public class SysloginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysloginApplication.class, args);
    }


}
