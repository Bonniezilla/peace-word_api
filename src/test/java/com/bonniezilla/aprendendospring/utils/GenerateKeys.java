package com.bonniezilla.aprendendospring.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class GenerateKeys {
    public static void main(String[] args ) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        kpg.initialize(256);
        KeyPair keyPair = kpg.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("PrivateKey: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        System.out.println("PublicKey: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
    }
}
