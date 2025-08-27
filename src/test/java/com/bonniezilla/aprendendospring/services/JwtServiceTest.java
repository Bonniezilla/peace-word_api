package com.bonniezilla.aprendendospring.services;

import com.bonniezilla.aprendendospring.entities.Role;
import com.bonniezilla.aprendendospring.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class JwtServiceTest {

    private JwtService jwtService;

    private User testUser;

    private static final String privateKey = "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCAJ2m6xmyx8uy18Y+4yz3TxTm2RQS/YTxHuIphLVkC5xA==";
    private static final String publicKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEZN5AqOViMXQXldUjUm2U+cHfj2egkoPyP/bpJ8Tq1yJmC8Dn4nX4NxhDk1qLyz+W76hmU6rO0VPWnZot79UGQA==";

    @BeforeEach
    void setup() throws Exception {

        jwtService = new JwtService(privateKey, publicKey, 3600000);

        testUser = new User();
        testUser.setUsername("usertest");
        testUser.setRole(Role.ROLE_ADMIN);
    }

    @Test
    void testGenerateAndValidateToken() throws Exception {
        String token = jwtService.generateToken(testUser);

        assertNotNull(token);
        assertTrue(jwtService.validateToken(token));

        String username = jwtService.getUsernameFromToken(token);
        String role = jwtService.getRoleFromToken(token);

        assertNotNull(username);
        assertNotNull(role);

        assertEquals("usertest", username);
        assertEquals("ROLE_ADMIN", role);

        String[] parts = token.split("\\.");
        String tamperedToken = parts[0] + "." + parts[1] + "invalidSignature";
        assertFalse(jwtService.validateToken(tamperedToken));
    }

    @Test
    void testTokenExpiration() throws Exception{
        JwtService shortExpiryService = new JwtService(privateKey, publicKey, 1);
        String token = shortExpiryService.generateToken(testUser);

        // Wait 1 ms
        Thread.sleep(5);

        // Token expired should be invalid
        assertFalse(shortExpiryService.validateToken(token));
    }

}
