package com.bms.central_api_v1.service;


import com.bms.central_api_v1.exception.UnAuthorizedException;
import com.bms.central_api_v1.integration.AuthApi;
import com.bms.central_api_v1.integration.DbApi;
import com.bms.central_api_v1.integration.NotificationApi;
import com.bms.central_api_v1.integration.RabbitMqIntegration;
import com.bms.central_api_v1.model.AppUser;
import com.bms.central_api_v1.model.Theater;
import com.bms.central_api_v1.requestdto.CreateTheaterNotificationRB;
import com.bms.central_api_v1.requestdto.CreateTheaterRB;
import com.bms.central_api_v1.requestdto.NotificationMessage;
import com.bms.central_api_v1.responseBody.TokenResponseBody;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TheaterService {

    @Autowired
    UserService userService;

    @Autowired
    DbApi dbApi;

    @Autowired
    AuthApi authApi;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    NotificationApi notificationApi;

    @Autowired
    RabbitMqIntegration rabbitMqIntegration;

    public void notifyTheaterOwnerRegAcceptance(Theater theater,AppUser admin){
        NotificationMessage message = new NotificationMessage();

        message.setMessage("Theater_Accepted");

        CreateTheaterNotificationRB rq = new CreateTheaterNotificationRB();
        rq.setTheater(theater);
        rq.setAdmin(admin);

        message.setPayLoad(rq);

        rabbitMqIntegration.insertMessageToQueue(message);

    }

    public void notifyAllTheAdminsForTheaterCreation(Theater theater, List<AppUser> admins){
        for(AppUser admin : admins){
            //we need to call the notification API endpoint
            CreateTheaterNotificationRB requestBody = new CreateTheaterNotificationRB();

            String token = this.callGenerateToken(admin.getEmail(),admin.getPassword());

            requestBody.setTheater(theater);
            requestBody.setAdmin(admin);
            requestBody.setToken(token);

            NotificationMessage message = new NotificationMessage();
            message.setMessage("Create_theater");
            message.setPayLoad(requestBody);

            rabbitMqIntegration.insertMessageToQueue(message);

//            notificationApi.notifyAdminsForTheaterCreation(requestBody);

        }
    }

    public Theater raiseTheaterCreationRequest(CreateTheaterRB createTheaterRB, UUID theaterOwnerId,String Authorization){
         boolean isTheaterOwner = userService.isTheaterOwner(theaterOwnerId);

         if(!isTheaterOwner){
             throw new UnAuthorizedException(String.format(
                     "UserID %s does not have access to add theater to the application",theaterOwnerId.toString()
             ));

         }

         AppUser owner = userService.getUserById(theaterOwnerId);

         Theater theater= dbApi.createTheater(createTheaterRB,owner);

         //1. we need to get all the admins list from DBApi, create a controller in db api to get all the admins list
        //2. we need to set restapi to call get all admins method in dbapi

        List<AppUser> admins = userService.getAllAdmins();

        this.notifyAllTheAdminsForTheaterCreation(theater,admins);



         return theater;


    }

    public void acceptTheaterRequest(UUID theaterId, UUID adminId){

        Theater theater = dbApi.getTheaterById(theaterId);

        theater.setStatus("ACTIVE");

        theater = dbApi.updateTheater(theater);

        AppUser admin = dbApi.getUserById(adminId);

        this.notifyTheaterOwnerRegAcceptance(theater,admin);

    }

    public String callGenerateToken(String emailId, String password){

        Object response = authApi.callGetToken(emailId,password);

        TokenResponseBody responseBody = modelMapper.map(response, TokenResponseBody.class);

        return responseBody.getToken();
    }


}
