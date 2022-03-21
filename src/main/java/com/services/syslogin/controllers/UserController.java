package com.services.syslogin.controllers;

import com.services.syslogin.model.entities.User;
import com.services.syslogin.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("services/cadastro")
public class UserController {

    /*@todo
        - Criptografar a senha
        - Validar CPF
        - Validar Senha
     */

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public @ResponseBody
    User newUser(@RequestParam String nome, @RequestParam String cpf, @RequestParam String email, @RequestParam String password){
        User user = new User(nome, cpf, email , password);
        userRepository.save(user);
        return user;
    }
}
