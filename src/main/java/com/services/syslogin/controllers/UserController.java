package com.services.syslogin.controllers;

import com.services.syslogin.model.entities.User;
import com.services.syslogin.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("services/cadastro")
public class UserController {

    /*@todo
        - Criptografar a senha
        - Validar CPF
        - Validar Senha
        - Criar Middleware
     */

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public @ResponseBody
    User newUser(@Valid User user){
        userRepository.save(user);
        return user;
    }
}
