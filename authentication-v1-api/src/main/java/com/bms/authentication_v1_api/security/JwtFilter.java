package com.bms.authentication_v1_api.security;


import com.bms.authentication_v1_api.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    AuthService authService;

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bToken = request.getHeader("Authorization");

        if(bToken != null && bToken.startsWith("Bearer ")){

            String token = bToken.substring(7);

            //we got the token, we need to validate this token is a genuine token or not
            Boolean isValid = authService.verifyToken(token);

            if(isValid == false){
                //if user is not valid, we don't need to provide authentication
                filterChain.doFilter(request,response);
                return;
            }

            String credentials = authService.decrypt(token);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(credentials,null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        }
        filterChain.doFilter(request,response);
    }


}
