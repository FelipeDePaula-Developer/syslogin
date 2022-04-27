package com.services.syslogin.controllers;

import com.services.syslogin.model.entities.User;
import com.services.syslogin.model.logic.EncryptDecryptPassword;
import com.services.syslogin.model.repositories.UserRepository;
import com.services.syslogin.model.validations.UserDataValidation;
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
    boolean newUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        boolean emailValidate = userDataValidation.emailValidate(email);
        String userNameExists = userRepository.verifyUsernameExists(userName);
        String emailExists = userRepository.verifyEmailExists(email);

        if (emailValidate && userNameExists == null && emailExists == null) {
            String encryptedPassword = encryptDecryptPassword.encryptPassword(password);
            User user = new User(userName, email, encryptedPassword);
            userRepository.save(user);
            return true;
        }else{
            return false;
        }

    }

    @PostMapping("pages/log/user")
    public @ResponseBody
    String loginUser (@RequestParam String email, @RequestParam String password){

        String id = userRepository.searchUser(email, password);
        return id;
    }
}
