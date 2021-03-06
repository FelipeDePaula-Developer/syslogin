package com.services.syslogin.controllers;

import com.services.syslogin.model.User;
import com.services.syslogin.model.UserLogin;
import com.services.syslogin.repository.UserLoginRepository;
import com.services.syslogin.service.utils.EncryptDecrypt;
import com.services.syslogin.repository.UserRepository;
import com.services.syslogin.service.UserService;
import com.services.syslogin.service.utils.Utils;
import com.services.syslogin.service.utils.web.WebFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;
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
    @Autowired
    private Utils utils;

    @PostMapping("/cad/user")
    public ModelAndView newUser(@RequestParam String userName, @RequestParam String email, @RequestParam String password,
                                HttpServletRequest request) throws Exception {
        boolean emailValidate = userDataValidation.emailValidate(email);
        String userNameExists = userRepository.verifyUsernameExists(userName);
        String emailExists = userRepository.verifyEmailExists(email);

        if (emailValidate && userNameExists == null && emailExists == null) {
            String userIP = WebFuncs.getClientIpAddress(request);
            String encryptedPassword = encryptDecrypt.encryptPassword(password);
            User user = new User(userName, email, encryptedPassword);
            userRepository.save(user);

            UserLogin ul = new UserLogin(user, "F", webFuncs.getClientIpAddress(request), "T", encryptDecrypt.genKey());
            userLoginRepository.save(ul);

            return new ModelAndView("redirect:/dashboard");
        } else {
            ModelAndView mv = new ModelAndView("pages/sign-up");
            if (userNameExists != null) {
                mv.addObject("username", userName);
                mv.addObject("errorUsername", "Esse username j?? est?? cadastrado no nosso sistema");
            }
            if (emailExists != null) {
                mv.addObject("email", email);
                mv.addObject("errorEmail", "Esse email j?? est?? cadastrado no nosso sistema");
            }
            if (!emailValidate) {
                mv.addObject("email", email);
                mv.addObject("errorEmail", "Formato do email invalido");
            }
            return mv;
        }
    }

    @PostMapping("/login/user")
    public ModelAndView authUser(@RequestParam String email, @RequestParam String password, @RequestParam String rememberMe,
                                 HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("pages/sign-in");
        boolean emailValidate = userDataValidation.emailValidate(email);
        if (emailValidate) {
            User user = userRepository.findUserByEmail(email);
            if (user != null) {
                String userPassword = encryptDecrypt.decryptPassword(user.getPassword());
                if (Objects.equals(userPassword, password)) {

                    String userIP = WebFuncs.getClientIpAddress(request);
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
                mv.addObject("loginError", "Email n??o encontrado, realize seu cadastro");
            }
        }
        return mv;
    }

    @GetMapping("/logout/user")
    public ModelAndView logoutUser(HttpSession session, HttpServletRequest request) {
        Cookie rememberMeCookie = webFuncs.getCookie(request, "remember-me");

        if (rememberMeCookie != null) {
            String cookieValue = webFuncs.decodeURLParams(rememberMeCookie.getValue());
            Map<String, String> cookieValueMap = utils.convertStringToMap(cookieValue, "&", " = ");
            UserLogin userLogin = userLoginRepository.getUserLoginByUser_KeyAndId_user(cookieValueMap.get("key"), Integer.parseInt(cookieValueMap.get("userId")));

            if (userLogin != null) {
                userLogin.setRemember_me("F");
                userLogin.setLogged("F");
                userLoginRepository.save(userLogin);
            }

            rememberMeCookie.setMaxAge(-1);
        }
        session.invalidate();

        return new ModelAndView("redirect:/sign-in");
    }

}
