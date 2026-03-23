package com.horrorcore.car_show.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class FilterChainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                request ->
                        request.requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/car",
                                "/api/v1/car/",
                                "/api/v1/owner",
                                "/api/v1/owner/",
                                "/api/v1/auth/**",
                                "/",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                                .requestMatchers(
                                        "/api/v1/car/create",
                                        "/api/v1/car/create/",
                                        "/api/v1/car/{id}/delete",
                                        "/api/v1/owner/create",
                                        "/api/v1/owner/create/",
                                        "/api/v1/owner/delete/{id}"
                                ).authenticated()
        )
                .sessionManagement(ses
                        -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
