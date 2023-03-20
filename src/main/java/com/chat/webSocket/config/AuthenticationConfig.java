package com.chat.webSocket.config;

import com.chat.webSocket.config.sns.oauth.PrincipalOauth2UserService;
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
    private PrincipalOauth2UserService principalOauth2UserService;

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
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/loginProc")
//                .defaultSuccessUrl("/") // 성공 시 redirection되는 페이지
//                .and() // Service 를 직접 걸어줘야 함 → 낚아서 어디로 갈 지 정해주기
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                ;
        return http.build();
    }

}
