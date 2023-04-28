package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf().disable()

            .authorizeHttpRequests()
            .requestMatchers("/api/hello").permitAll()
            .requestMatchers(PathRequest.toH2Console()).permitAll()
            .anyRequest().authenticated()

            .and()
            .headers()
            .frameOptions()
            .sameOrigin();

    return httpSecurity.build();
  }

}
