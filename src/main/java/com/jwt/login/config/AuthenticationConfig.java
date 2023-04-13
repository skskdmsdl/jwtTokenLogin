package com.jwt.login.config;

import com.jwt.login.config.filter.JwtTokenFilter;
import com.jwt.login.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final MemberService memberService;
    @Value("${jwt.secret-key}")
    private String key;

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
                .addFilterBefore(new JwtTokenFilter(key, memberService), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .successHandler(successHandler)
                ;
        return http.build();
    }

}
