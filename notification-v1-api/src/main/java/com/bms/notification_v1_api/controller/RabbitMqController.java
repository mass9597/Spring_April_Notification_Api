package com.bms.notification_v1_api.controller;


import com.bms.notification_v1_api.requestbody.NotificationMessage;
import com.bms.notification_v1_api.requestbody.TheaterRequestBody;
import com.bms.notification_v1_api.service.TheaterMailService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {

    @Autowired
    TheaterMailService theaterMailService;

    @Autowired
    ModelMapper modelMapper;

    @RabbitListener(queues = "bms-notification-queue")
    public void pullMessageFromRabbitMQ(NotificationMessage message) throws Exception{

        String messageType = message.getMessage();

        if(messageType.equalsIgnoreCase("Create_theater")){
            Object payLoad = message.getPayLoad();

            TheaterRequestBody theaterRequestBody = modelMapper.map(payLoad,TheaterRequestBody.class);

            theaterMailService.notifyAdminForTheaterCreation(theaterRequestBody);
        }

    }
}
