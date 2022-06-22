package com.services.syslogin.service.utils.web;

import com.services.syslogin.service.utils.EncryptDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WebFuncs {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    public Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName))
                    return cookie;
            }
        }
        return null;
    }

    public void setPersitentCookie(String cookieName, String cookieValue, HttpServletResponse response) {
        response.setContentType("text/html");

        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(15 * 24 * 60 * 60);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public void setRememberMeCookie(String username, int userId, String userIp, String key, HttpServletResponse response) throws Exception {
        response.setContentType("text/html");

        Map<String, String> infosCookies = new HashMap<>();
        infosCookies.put("username", username);
        infosCookies.put("userId", Integer.toString(userId));
        infosCookies.put("cookieTime", Integer.toString(15 * 24 * 60 * 60));
        infosCookies.put("userIp", userIp);
        infosCookies.put("key", key);

        String encodedCookie = infosCookies.keySet().stream().map(keyMap -> {
            try {
                return keyMap + "=" + URLEncoder.encode(infosCookies.get(keyMap), StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return e;
            }
        }).collect(Collectors.joining("&"));

//        String cryptoInfos = (username + ";" +
//                Integer.toString(userId) + ";" +
//                15 * 24 * 60 * 60 + ";" +
//                userIp + ";" +
//                key);
//
//        String InfoCookie = URLEncoder.encode(username + "&" +
//                Integer.toString(userId) + "&" +
//                15 * 24 * 60 * 60 + "&" +
//                userIp + "&" +
//                key);

        System.out.println(InfoCookie);

        Cookie cookie = new Cookie("remember-me", InfoCookie);
        cookie.setMaxAge(15 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
