package com.bms.central_api_v1.integration;

import com.bms.central_api_v1.responseBody.SuccessResponseBody;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AuthApi extends RestApi{

    @Autowired
    ModelMapper modelMapper;

    @Value("${auth.api.base}")
    String baseUrl;

    public Object callGetToken(String email, String password){

        String endPoint = "/token";
        HashMap queryParams = new HashMap<>();

        queryParams.put("userId",email);
        queryParams.put("password", password);
        Object response = this.makeGetCall(baseUrl,endPoint,queryParams);
        return response;

    }

    public SuccessResponseBody callVerifyToken(String token){

        String endPoint = "/verify-token";

        Object response =  this.makeGetCall(baseUrl,endPoint,new HashMap<>(),token);

        return modelMapper.map(response, SuccessResponseBody.class);
    }





}
