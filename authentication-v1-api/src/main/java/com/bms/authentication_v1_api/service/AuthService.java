package com.bms.authentication_v1_api.service;


import com.bms.authentication_v1_api.integration.DbApi;
import com.bms.authentication_v1_api.models.AppUser;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    DbApi dbApi;

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

    public String decrypt(String token){
        String credentials = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();

        return credentials;
    }

    public boolean verifyToken(String token){

        String credentials = this.decrypt(token);

        String email = credentials.split(":")[0];
        String password = credentials.split(":")[1];

        AppUser user = dbApi.callGetUserByEmail(email);

        if(user == null){
            return false;
        }
        if(user.getPassword().equalsIgnoreCase(password)){
            return true;
        }
        return false;

    }
}
