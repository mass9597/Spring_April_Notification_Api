package com.bms.authentication_v1_api.integration;

import com.bms.authentication_v1_api.models.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DbApi extends RestApi{

    @Autowired
    ModelMapper modelMapper;

    @Value("${db.api.base}")
    String baseUrl;

    public AppUser callGetUserByEmail(String emailId){
        String endPoint = "/user/email/"+ emailId.toString();
        Object response = this.makeGetCall(baseUrl,endPoint,new HashMap<>());

        if(response == null){
            return null;
        }

        return modelMapper.map(response, AppUser.class);

    }
}
