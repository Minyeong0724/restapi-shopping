package dev.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // POST 요청 허용을 위해 필수
                .headers(headers -> headers.frameOptions(options -> options.disable())) // H2 콘솔 등을 쓴다면 필요
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 경로 오픈
                );
        return http.build();
    }
}