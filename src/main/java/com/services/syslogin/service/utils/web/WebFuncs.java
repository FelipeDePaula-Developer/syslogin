package com.services.syslogin.service.utils.web;

import com.services.syslogin.service.utils.EncryptDecrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        String infoURLCookie = encodeParamsToURL(infosCookies);

        Cookie cookie = new Cookie("remember-me", infoURLCookie);
        cookie.setMaxAge(15 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    public String encodeParamsToURL(Map<String, String> infos) {
        return infos.keySet().stream()
                .map(keyMap -> {
                    try {
                        return keyMap + "=" + encodeURL(infos.get(keyMap));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return keyMap;
                })
                .collect(Collectors.joining("&", "", ""));
    }

    public String decodeURLParams(String params) {
        return Arrays.stream(params.split("&")).map(key -> {
                    try {
                        return key.split("=")[0] + " = " + decodeURL(key.split("=")[1]);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return key;
                }).
        collect(Collectors.joining("&"));
    }

    private String encodeURL(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private String decodeURL(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }
}
