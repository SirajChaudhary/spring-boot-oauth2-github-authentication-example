package com.example.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Utility to generate JWTs after successful OAuth2 GitHub login.
 * Fixes the Base64 decoding issue by using SecretKeySpec with HS256.
 */
@Component
public class JwtUtil {

    // In production, use a secure, random key and keep it secret
    private static final String SECRET_KEY = "my-secret-key-which-is-very-secure";

    // Token validity in milliseconds
    private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    /**
     * Generates a JWT with the given claims and subject (e.g., GitHub login)
     * @param claims custom payload to include in the token
     * @param subject typically the username or user ID
     * @return signed JWT as a string
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        // Convert the string secret key to bytes and create an HMAC-SHA256 key
        Key hmacKey = new SecretKeySpec(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );

        return Jwts.builder()
                .setClaims(claims) // add custom claims
                .setSubject(subject) // user identifier
                .setIssuedAt(new Date()) // token issue time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // token expiry
                .signWith(hmacKey) // sign the token with HMAC key
                .compact();
    }

    /**
     * Returns the secret key used to sign JWTs.
     * Mainly for use in configuring JwtDecoder in SecurityConfig.
     */
    public String getSecretKey() {
        return SECRET_KEY;
    }
}
