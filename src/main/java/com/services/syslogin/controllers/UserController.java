package com.services.syslogin.controllers;

import com.services.syslogin.model.User;
import com.services.syslogin.model.UserLogin;
import com.services.syslogin.repository.UserLoginRepository;
import com.services.syslogin.service.utils.EncryptDecrypt;
import com.services.syslogin.repository.UserRepository;
import com.services.syslogin.service.UserService;
import com.services.syslogin.service.utils.web.WebFuncs;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Key;
import java.util.Base64;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLoginRepository userLoginRepository;
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    @Autowired
    private UserService userDataValidation;
    @Autowired
    private WebFuncs webFuncs;

    @PostMapping("/cad/user")
    public ModelAndView newUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password,
                                HttpServletRequest request) throws Exception {
        boolean emailValidate = userDataValidation.emailValidate(email);
        String userNameExists = userRepository.verifyUsernameExists(userName);
        String emailExists = userRepository.verifyEmailExists(email);

        if (emailValidate && userNameExists == null && emailExists == null) {
            String userIP = webFuncs.getClientIpAddress(request);
            String encryptedPassword = encryptDecrypt.encryptPassword(password);
            User user = new User(userName, email, encryptedPassword);
            userRepository.save(user);

            UserLogin ul = new UserLogin(user, "F", webFuncs.getClientIpAddress(request), "T", EncryptDecrypt.genKey());
            userLoginRepository.save(ul);

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

    @PostMapping("/login/user")
    public ModelAndView authUser(@RequestParam String email, @RequestParam String password, @RequestParam String rememberMe,
                                 HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mv = new ModelAndView("pages/sign-in");
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            String dbpassword = encryptDecrypt.decryptPassword(user.getPassword());
            if (Objects.equals(dbpassword, password)) {

                String userIP = webFuncs.getClientIpAddress(request);
                String key = EncryptDecrypt.genKey();
                if (Objects.equals(rememberMe, "true")) {
                    webFuncs.setRememberMeCookie(user.getUserName(), user.getId(), userIP, key, response);
                    UserLogin ul = new UserLogin(user, "T", userIP, "T", key);
                    userLoginRepository.save(ul);
                } else {
                    UserLogin ul = new UserLogin(user, "F", userIP, "T", key);
                    userLoginRepository.save(ul);
                }

                session.setAttribute("username", user.getUserName());
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

    @GetMapping("/logout/user")
    public ModelAndView logoutUser (){
        ModelAndView mv = new ModelAndView("pages/sign-in");

        return mv;
    }

}
