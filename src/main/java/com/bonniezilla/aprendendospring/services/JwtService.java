package com.bonniezilla.aprendendospring.services;

import com.bonniezilla.aprendendospring.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    public JwtService() {}

    public JwtService(String privateKeyBase64, String publicKeyBase64, long expiration) {
        this.privateKeyBase64 = privateKeyBase64;
        this.publicKeyBase64 = publicKeyBase64;
        this.expiration = expiration;
    }

    @Value("${JWT_PRIVATE_KEY}")
    private String privateKeyBase64;

    @Value("${JWT_PUBLIC_KEY}")
    private String publicKeyBase64;

    @Value("${JWT_EXPIRATION}")
    private long expiration; // in milliseconds

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private void loadKeys() throws Exception {
        if (privateKey == null || publicKey == null) {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        byte[] privateBytes = Base64.getDecoder().decode(privateKeyBase64);
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateBytes);
        privateKey = keyFactory.generatePrivate(privateSpec);

        byte[] publicBytes = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicBytes);
        publicKey = keyFactory.generatePublic(publicSpec);
        }
    }

    // Generate token JWT
    public String generateToken(User user) throws Exception {
        loadKeys();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(privateKey, SignatureAlgorithm.ES256)
                .compact();
    }

    public boolean validateToken(String token) throws Exception {
        loadKeys();

        try {
            Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) throws Exception {
        loadKeys();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getRoleFromToken(String token) throws Exception {
        loadKeys();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

}
