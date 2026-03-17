package com.horrorcore.car_show.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class FilterChainConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                request ->
                        request.requestMatchers(
                                HttpMethod.GET,
                                "/car",
                                "/car/",
                                "/owner",
                                "/owner/",
                                "/auth/**",
                                "/",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                                .requestMatchers(
                                        "/car/create",
                                        "/car/create/",
                                        "/car/{id}/delete",
                                        "/owner/create",
                                        "/owner/create/",
                                        "/owner/delete/{id}"
                                ).authenticated()
        ).formLogin(
                form -> form.defaultSuccessUrl("/car")
                        .permitAll()
        );
        return httpSecurity.build();
    }
}
