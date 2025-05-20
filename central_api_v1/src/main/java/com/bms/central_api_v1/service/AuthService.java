package com.bms.central_api_v1.service;

import com.bms.central_api_v1.exception.UnAuthorizedException;
import com.bms.central_api_v1.integration.AuthApi;
import com.bms.central_api_v1.responseBody.SuccessResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthApi authApi;

    public void verifyToken(String Authorization){

        try{
            SuccessResponseBody successResponseBody = authApi.callVerifyToken(Authorization);
        } catch (Exception e) {
            throw new UnAuthorizedException("Invalid request");
        }

    }

}
