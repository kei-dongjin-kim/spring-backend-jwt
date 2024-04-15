package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.jwt.JwtAccessDeniedHandler;
import com.example.demo.jwt.JwtAuthenticationEntryPoint;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.jwt.JwtFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
  private final JwtTokenProvider jwtTokenProvider;
  private final CorsFilter corsFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  public SecurityConfig(
          JwtTokenProvider jwtTokenProvider,
          CorsFilter corsFilter,
          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
          JwtAccessDeniedHandler jwtAccessDeniedHandler
  ) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.corsFilter = corsFilter;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            // csrf have to be disabled, because we will use token.
            .csrf(AbstractHttpConfigurer::disable)

            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

            .exceptionHandling((exception) -> exception
              .authenticationEntryPoint(jwtAuthenticationEntryPoint)
              .accessDeniedHandler(jwtAccessDeniedHandler)
            )

            // enable h2-console
            .headers((headers) -> headers
              .frameOptions((frameOptions) -> frameOptions.sameOrigin())
            )

            // session have to be STATELESS, because will not use session
            .sessionManagement((sessionManagement) -> sessionManagement
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
              .requestMatchers("/api/login", "/api/signup").permitAll()
              .requestMatchers(PathRequest.toH2Console()).permitAll()
              .anyRequest().authenticated()
            )

            .addFilterBefore(
              new JwtFilter(jwtTokenProvider),
              UsernamePasswordAuthenticationFilter.class
            );

    return http.build();
  }
}