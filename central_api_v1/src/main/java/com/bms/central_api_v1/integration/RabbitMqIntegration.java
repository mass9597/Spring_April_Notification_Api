package com.bms.central_api_v1.integration;

import com.bms.central_api_v1.requestdto.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqIntegration {

    @Autowired
    RabbitTemplate rabbitTemplate;

    String exchangeName = "bms-notification-exchange";
    String routingKey = "bms-notification-route-123";

    public void insertMessageToQueue(NotificationMessage message){
        rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
    }
}
