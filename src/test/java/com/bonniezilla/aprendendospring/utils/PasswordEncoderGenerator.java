package com.bonniezilla.aprendendospring.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String raw1 = "Viduu777@";
        String raw2 = "Borespedro1@";

        System.out.println(raw1 + " -> " + encoder.encode(raw1));
        System.out.println(raw2 + " -> " + encoder.encode(raw2));
    }
}
