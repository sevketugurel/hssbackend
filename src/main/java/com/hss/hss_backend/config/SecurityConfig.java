package com.hss.hss_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/public/**", "/actuator/health", "/actuator/info").permitAll()
                .requestMatchers("/api/animals/**").hasAnyRole("ADMIN", "VETERINARIAN", "STAFF", "RECEPTIONIST")
                .requestMatchers("/api/appointments/**").hasAnyRole("ADMIN", "VETERINARIAN", "STAFF", "RECEPTIONIST")
                .requestMatchers("/api/medical-history/**").hasAnyRole("ADMIN", "VETERINARIAN")
                .requestMatchers("/api/lab-tests/**").hasAnyRole("ADMIN", "VETERINARIAN", "STAFF")
                .requestMatchers("/api/prescriptions/**").hasAnyRole("ADMIN", "VETERINARIAN")
                .requestMatchers("/api/vaccinations/**").hasAnyRole("ADMIN", "VETERINARIAN", "STAFF")
                .requestMatchers("/api/invoices/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                .requestMatchers("/api/inventory/**").hasAnyRole("ADMIN", "STAFF")
                .requestMatchers("/api/files/**").hasAnyRole("ADMIN", "VETERINARIAN", "STAFF")
                .requestMatchers("/api/dashboard/**").hasAnyRole("ADMIN", "VETERINARIAN", "STAFF", "RECEPTIONIST")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder()))
            );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // This will be configured with the actual issuer URI from application properties
        return NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com")
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
