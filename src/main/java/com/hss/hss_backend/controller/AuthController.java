package com.hss.hss_backend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    // SecurityConfig ile aynı secret key
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        
        // Basit test için herhangi bir kullanıcı adı/şifre kabul ediyoruz
        if (username == null || username.trim().isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Username is required");
            return ResponseEntity.badRequest().body(error);
        }

        // Test token oluştur
        String token = createTestToken(username);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("tokenType", "Bearer");
        response.put("expiresIn", 3600); // 1 saat
        response.put("username", username);
        response.put("roles", new String[]{"ADMIN", "VETERINARIAN", "STAFF"}); // Test için tüm rolleri ver
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test-token")
    public ResponseEntity<Map<String, Object>> getTestToken() {
        String token = createTestToken("test-user");
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("tokenType", "Bearer");
        response.put("expiresIn", 3600);
        response.put("username", "test-user");
        response.put("roles", new String[]{"ADMIN", "VETERINARIAN", "STAFF"});
        response.put("message", "Bu token test amaçlıdır. Swagger UI'da Authorization butonuna tıklayıp 'Bearer ' + token değerini girin.");
        
        return ResponseEntity.ok(response);
    }

    private String createTestToken(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plus(1, ChronoUnit.HOURS);
        
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", new String[]{"ADMIN", "VETERINARIAN", "STAFF"})
                .claim("username", username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(SECRET_KEY)
                .compact();
    }
}
