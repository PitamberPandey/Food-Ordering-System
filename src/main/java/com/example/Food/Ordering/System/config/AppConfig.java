package com.example.Food.Ordering.System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(management -> 
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
            .authorizeHttpRequests(Authorize -> Authorize
                .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN") // Admin and restaurant owner access
                .requestMatchers("/api/users/profile").authenticated() // Profile requires authentication
                .requestMatchers("/api/**").authenticated() 
                .requestMatchers("/api/**").authenticated()// All /api/** endpoints require authentication
                .anyRequest().permitAll()) // All other requests are permitted without authentication
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class) // JWT filter before basic authentication
            .csrf(csrf -> csrf.disable())
             // Disable CSRF for stateless APIs
            
            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // CORS configuration

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList("http://localhost:8080")); // Allow your frontend URL here
                cfg.setAllowedMethods(Collections.singletonList("*")); // Allow all HTTP methods
                cfg.setAllowCredentials(true); // Allow cookies and credentials
                cfg.setAllowedHeaders(Collections.singletonList("*")); // Allow all headers
                cfg.setExposedHeaders(Arrays.asList("Authorization")); // Expose the Authorization header
                cfg.setMaxAge(3600L); // Cache pre-flight response for 1 hour

                return cfg;
            }
        };
    }
}
