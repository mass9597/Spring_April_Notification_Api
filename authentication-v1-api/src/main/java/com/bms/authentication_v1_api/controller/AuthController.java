package com.bms.authentication_v1_api.controller;


import com.bms.authentication_v1_api.responsebody.SuccessRequestBody;
import com.bms.authentication_v1_api.responsebody.TokenResponseBody;
import com.bms.authentication_v1_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @GetMapping("/token")
    public ResponseEntity getToken(@RequestParam  String userId, @RequestParam String password){

        String jwtToken = authService.generateToken(userId,password);

        TokenResponseBody tokenResponseBody = new TokenResponseBody();

        tokenResponseBody.setToken(jwtToken);

        return new ResponseEntity<>(tokenResponseBody, HttpStatus.OK);

    }

    @GetMapping("/verify-token")
    public ResponseEntity verifyToken(){
        SuccessRequestBody successRequestBody = new SuccessRequestBody();
        successRequestBody.setResponse("Success");
        return new ResponseEntity<>(successRequestBody,HttpStatus.OK);
    }
}
