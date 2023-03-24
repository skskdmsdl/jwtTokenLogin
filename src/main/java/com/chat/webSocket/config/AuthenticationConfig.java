package com.chat.webSocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Autowired
    private OAuth2SuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.cors();

         http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/join", "/login").permitAll()
                 .anyRequest().permitAll()
//                .antMatchers().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login()
                .successHandler(successHandler)
//                .loginPage("/login/sns")
//                .defaultSuccessUrl("/login")
//                .userInfoEndpoint()
//                .userService(oAuth2UserService)
                ;
        return http.build();
    }

}
