package com.services.syslogin.service.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Utils {
    public Map<String, String> convertStringToMap(String text, String splitCharacter, String regexCharacter){
        Map<String, String> map = new HashMap<>();
        String[] arrayText = text.split(splitCharacter);
        for (String pares : arrayText){
            map.put(pares.split(regexCharacter)[0], pares.split(regexCharacter)[1]);
        }
        return map;
    }
}
