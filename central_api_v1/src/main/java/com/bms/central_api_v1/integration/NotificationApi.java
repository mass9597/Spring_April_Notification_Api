package com.bms.central_api_v1.integration;

import com.bms.central_api_v1.requestdto.CreateTheaterNotificationRB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Component
public class NotificationApi extends RestApi{

    @Value("${notification.api.base}")

    String baseUrl;

    public void notifyAdminsForTheaterCreation(CreateTheaterNotificationRB createTheaterNotificationRB){

        String endPoint = "/theater/notify";

        this.makePutCall(baseUrl,endPoint,createTheaterNotificationRB,new HashMap<>());
    }


}
