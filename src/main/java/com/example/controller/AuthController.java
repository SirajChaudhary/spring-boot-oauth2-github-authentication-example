package com.example.controller;

import com.example.util.JwtUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Called after GitHub login, generates a JWT for API access
     */
    @GetMapping("/success")
    public Map<String, Object> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", principal.getAttribute("name"));
        claims.put("login", principal.getAttribute("login"));

        String token = jwtUtil.generateToken(claims, principal.getAttribute("login"));

        return Map.of(
                "message", "Login successful",
                "jwt", token,
                "user", principal.getAttributes()
        );
    }
}
