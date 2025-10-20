package com.hss.hss_backend.controller;

import com.hss.hss_backend.entity.UserAccount;
import com.hss.hss_backend.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // SecurityConfig ile aynı secret key - her restart'ta aynı key kullanılsın
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("mySecretKeyForTestingPurposesOnly123456789012345678901234567890".getBytes());

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        
        if (username == null || username.trim().isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Username is required");
            return ResponseEntity.badRequest().body(error);
        }

        // Kullanıcıyı bul
        Optional<UserAccount> userAccount = userService.findByUsername(username);
        
        if (userAccount.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid username or password");
            return ResponseEntity.badRequest().body(error);
        }

        UserAccount user = userAccount.get();
        
        // Şifre kontrolü
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid username or password");
            return ResponseEntity.badRequest().body(error);
        }

        // Kullanıcı aktif mi?
        if (!user.getIsActive()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Account is deactivated");
            return ResponseEntity.badRequest().body(error);
        }

        // Kullanıcının rollerini al
        List<String> roles = userService.getUserRoles(username);
        
        // JWT token oluştur
        String token = createToken(username, roles);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("tokenType", "Bearer");
        response.put("expiresIn", 3600); // 1 saat
        response.put("username", username);
        response.put("roles", roles);
        response.put("fullName", user.getStaff().getFullName());
        response.put("email", user.getEmail());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-test-user")
    public ResponseEntity<Map<String, Object>> createTestUser(@RequestBody Map<String, String> userRequest) {
        String username = userRequest.getOrDefault("username", "test-user");
        String email = userRequest.getOrDefault("email", "test@example.com");
        String fullName = userRequest.getOrDefault("fullName", "Test User");
        String role = userRequest.getOrDefault("role", "ADMIN");

        try {
            // Kullanıcı zaten var mı kontrol et
            Optional<UserAccount> existingUser = userService.findByUsername(username);
            if (existingUser.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "User already exists");
                response.put("username", username);
                response.put("roles", userService.getUserRoles(username));
                return ResponseEntity.ok(response);
            }

            // Yeni kullanıcı oluştur
            userService.createTestUser(username, email, fullName, role);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Test user created successfully");
            response.put("username", username);
            response.put("email", email);
            response.put("fullName", fullName);
            response.put("role", role);
            response.put("password", "password123"); // Varsayılan şifre
            response.put("note", "Use this password to login");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to create user: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/test-token")
    public ResponseEntity<Map<String, Object>> getTestToken() {
        // Önce test kullanıcısı oluştur
        try {
            Optional<UserAccount> testUser = userService.findByUsername("test-user");
            if (testUser.isEmpty()) {
                userService.createTestUser("test-user", "test@example.com", "Test User", "ADMIN");
            }
        } catch (Exception e) {
            // Kullanıcı zaten var olabilir, devam et
        }

        String token = createToken("test-user", List.of("ADMIN", "VETERINARIAN", "STAFF"));
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("tokenType", "Bearer");
        response.put("expiresIn", 3600);
        response.put("username", "test-user");
        response.put("roles", List.of("ADMIN", "VETERINARIAN", "STAFF"));
        response.put("message", "Bu token test amaçlıdır. Swagger UI'da Authorization butonuna tıklayıp 'Bearer ' + token değerini girin.");
        response.put("loginInfo", "Username: test-user, Password: password123");
        
        return ResponseEntity.ok(response);
    }

    private String createToken(String username, List<String> roles) {
        Instant now = Instant.now();
        Instant expiry = now.plus(1, ChronoUnit.HOURS);
        
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .claim("username", username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(SECRET_KEY)
                .compact();
    }
}