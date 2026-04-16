package com.busanit401.second_trip_project_back.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Log4j2 // 로그 찍어서 설정 로드되는지 확인!
public class CustomSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("------------------- 시큐리티 설정 로드 중... -------------------");

        // 1. CSRF 비활성화 (POST 요청 시 필수!)
        http.csrf(csrf -> csrf.disable());

        // 2. CORS 설정 (나중에 플러터랑 통신할 때 필요)
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // 3. 모든 API 경로(/api/**) 권한 없이 허용!
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/member/**").permitAll() // 멤버 관련 API 허용
                .anyRequest().permitAll() // 일단 테스트 중엔 다 열어버리자!
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 모든 곳에서 오는 요청 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}