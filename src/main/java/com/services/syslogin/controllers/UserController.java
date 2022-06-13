package com.services.syslogin.controllers;

import com.services.syslogin.model.User;
import com.services.syslogin.service.utils.EncryptDecryptPassword;
import com.services.syslogin.repository.UserRepository;
import com.services.syslogin.service.UserService;
import com.services.syslogin.service.utils.web.SessionFuncs;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptDecryptPassword encryptDecryptPassword;
    @Autowired
    private UserService userDataValidation;
    @Autowired
    private SessionFuncs sessionFuncs;


    @PostMapping("/cad/user")
    public ModelAndView newUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password) throws Exception {
        boolean emailValidate = userDataValidation.emailValidate(email);
        String userNameExists = userRepository.verifyUsernameExists(userName);
        String emailExists = userRepository.verifyEmailExists(email);

        if (emailValidate && userNameExists == null && emailExists == null) {
            String encryptedPassword = encryptDecryptPassword.encryptPassword(password);
            User user = new User(userName, email, encryptedPassword);
            userRepository.save(user);
            return new ModelAndView("redirect:/dashboard");
        } else {
            ModelAndView mv = new ModelAndView("pages/sign-up");
            if (userNameExists != null) {
                mv.addObject("username", userName);
                mv.addObject("errorUsername", "Esse username já está cadastrado no nosso sistema");
            }
            if (emailExists != null) {
                mv.addObject("email", email);
                mv.addObject("errorEmail", "Esse email já está cadastrado no nosso sistema");
            }
            return mv;
        }
    }

    @PostMapping("/log/user")
    public ModelAndView authUser(@RequestParam String email, @RequestParam String password, @RequestParam String rememberMe,
                                 HttpSession session, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("pages/sign-in");
        String[] dbUserReturn = userRepository.searchUserPerEmail(email).split(",");
        String userName = dbUserReturn[0];
        String userEmail = dbUserReturn[1];
        String userPassword = dbUserReturn[2];

        if (userPassword != null) {
            String dbpassword = encryptDecryptPassword.decryptPassword(userPassword);

            if (Objects.equals(dbpassword, password)) {

                if (Objects.equals(rememberMe, "false")){
                    sessionFuncs.setNonPersitentCookie("userEmail", userEmail, response);
                    sessionFuncs.setNonPersitentCookie("userName", userName, response);
                }else if (Objects.equals(rememberMe, "true")){
                    sessionFuncs.setPersitentCookie("userEmail", userEmail, response);
                    sessionFuncs.setPersitentCookie("userName", userName, response);
                }

                session.setAttribute("usuarioLogado", userEmail);
                return new ModelAndView("redirect:/dashboard");
            } else {
                mv.addObject("email", email);
                mv.addObject("loginError", "Email ou Senha Incorretos");
            }

        } else {
            mv.addObject("loginError", "Email não encontrado realize seu cadastro");
        }
        return mv;
    }
}
