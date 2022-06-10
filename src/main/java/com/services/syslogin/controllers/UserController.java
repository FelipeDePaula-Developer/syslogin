package com.services.syslogin.controllers;

import com.services.syslogin.model.User;
import com.services.syslogin.service.utils.EncryptDecryptPassword;
import com.services.syslogin.repository.UserRepository;
import com.services.syslogin.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptDecryptPassword encryptDecryptPassword;
    @Autowired
    private UserService userDataValidation;


    @PostMapping("/cad/user")
    public ModelAndView newUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password) throws Exception {

        boolean emailValidate = userDataValidation.emailValidate(email);
        String userNameExists = userRepository.verifyUsernameExists(userName);
        String emailExists = userRepository.verifyEmailExists(email);
        if (emailValidate && userNameExists == null && emailExists == null) {
            String encryptedPassword = encryptDecryptPassword.encryptPassword(password);
            User user = new User(userName, email, encryptedPassword);
            userRepository.save(user);
            return new ModelAndView("/pages/dashboard");
        } else {
            ModelAndView mv = new ModelAndView("pages/sign-up");

            if (userNameExists != null) {
                mv.addObject("username", userName);
                mv.addObject("errorUsername", "Esse username já está cadastrado no nosso sistema");
            }

            if (emailExists != null) {
                mv.addObject("email", email);
                mv.addObject("errorEmail", "Esse username já está cadastrado no nosso sistema");
            }

            return mv;
        }
    }

    @PostMapping("/log/user")
    public @ResponseBody
    Object authUser(@RequestParam String email, @RequestParam String password) throws Exception {
        JSONObject json = new JSONObject();
        String dbUserReturn = userRepository.searchUserPerEmail(email);

        if (dbUserReturn != null) {
            String[] userData = dbUserReturn.split(",");
            String dbpassword = encryptDecryptPassword.decryptPassword(userData[3]);
            if (Objects.equals(dbpassword, password)) {

            } else {
                json.put("success", "false");
            }
        } else {
            json.put("success", "false");
        }

        return json;
    }
}
