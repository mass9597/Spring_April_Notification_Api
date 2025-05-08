package com.bms.authentication_v1_api.security;


import com.bms.authentication_v1_api.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    AuthService authService;

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        String bToken = request.getHeader("Authorization");

        if(bToken != null && bToken.startsWith("Bearer ")){

            String token = bToken.substring(7);
            authService.verifyToken(token);

        }
    }


}
