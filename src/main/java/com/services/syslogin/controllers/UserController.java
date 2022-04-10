package com.services.syslogin.controllers;

import com.services.syslogin.model.entities.User;
import com.services.syslogin.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {



    @Autowired
    private UserRepository userRepository;




    @PostMapping("user/cadastro")
    public @ResponseBody
    User newUser(@Valid User user){
        userRepository.save(user);
        return user;
    }
}
