package com.chat.webSocket.config;

import com.chat.webSocket.member.model.Member;
import com.chat.webSocket.member.model.entity.MemberEntity;
import com.chat.webSocket.member.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginService loginService;

    @Override
    public void onAuthenticationSuccess (
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException
    {
        Object principal = authentication.getPrincipal();
        if(principal instanceof OidcUser){
            // google
            MemberEntity oauth = MemberEntity.OAuth2Provider.google.convert((OidcUser) principal);
//            Member member = loginService.loadUser(oauth);
            Member member = loginService.loadUser(oauth);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(oauth, null, oauth.getAuthorities())
            );

        }else if(principal instanceof OAuth2User){
            // naver
            MemberEntity oauth = MemberEntity.OAuth2Provider.naver.convert((OidcUser) principal);
//            Member member = loginService.loadUser(oauth);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(oauth, null, oauth.getAuthorities())
            );
        }
        request.getRequestDispatcher("/").forward(request, response);
    }

}
