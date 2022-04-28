package com.services.syslogin.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.services.syslogin.model.entities.User;
import com.services.syslogin.model.logic.EncryptDecryptPassword;
import com.services.syslogin.model.repositories.UserRepository;
import com.services.syslogin.model.validations.UserDataValidation;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptDecryptPassword encryptDecryptPassword;
    @Autowired
    private UserDataValidation userDataValidation;


    @PostMapping("pages/cad/user")
    public @ResponseBody
    JSONObject newUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        JSONObject json = new JSONObject();
        json.put("username", "");
        json.put("email", "");

        boolean emailValidate = userDataValidation.emailValidate(email);
        String userNameExists = userRepository.verifyUsernameExists(userName);
        String emailExists = userRepository.verifyEmailExists(email);
        if (emailValidate && userNameExists == null && emailExists == null) {
            String encryptedPassword = encryptDecryptPassword.encryptPassword(password);
            User user = new User(userName, email, encryptedPassword);
            userRepository.save(user);
            json.put("success", "Usuário Cadastrado");
            return json;
        }else{
            if(userNameExists != null)
                json.put("username", "Esse username já está cadastrado no nosso sistema");

            if(emailExists != null)
                json.put("email", "Esse email já está cadastrado no nosso sistema");

            json.put("error", "Erro ao cadastrar usuário");
            return json;
        }

    }

    @PostMapping("pages/log/user")
    public @ResponseBody
    String loginUser (@RequestParam String email, @RequestParam String password){

        String id = userRepository.searchUser(email, password);
        return id;
    }
}
