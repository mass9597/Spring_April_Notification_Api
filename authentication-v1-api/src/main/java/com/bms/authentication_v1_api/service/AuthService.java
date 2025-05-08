package com.bms.authentication_v1_api.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Value("${auth.secret.key}")
    String secretKey;

    Long expirationTime = 100000L;

    public String generateToken(String userId, String password){

        String credentials = userId+":"+password;

        String jwtToken = Jwts.builder().setSubject(credentials)
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        return jwtToken;


    }

    public boolean verifyToken(String token){

    }
}
