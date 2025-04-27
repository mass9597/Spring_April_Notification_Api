package com.bms.central_api_v1.configuration;


import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    String exchangeName = "bms-notification-exchange";
    String queueName = "bms-notification-queue";
    String routingKey = "bms-notification-route-123";

    //we are going to use rabbitMq as we want asynchronous connection between centralapi and notification api
    //1. we need to add rabbit mq dependencies to the spring boot from maven repository --- spring boot starter amqp
    //2. we need to set connection parameters of rabbit mq --- CachingConnectionFactory
    //3. to do the operations in rabbit mq we require rabbit template object.


    @Bean
    public CachingConnectionFactory getCachingConnection(){
        CachingConnectionFactory connection = new CachingConnectionFactory("localhost");
        connection.setUsername("guest");
        connection.setPassword("guest");
        return connection;

    }

    @Bean
    public RabbitTemplate getRabbitTemplate(CachingConnectionFactory connection){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connection);

        //spring boot knows how to convert the json payload/data to object but rabbitmq doesn't, thus, we use jackson2jsonmessageconvertor
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    //create exchange and queue in rabbit mq

    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue getMessagingQueue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding bindingQueueWithExchange(DirectExchange exchange,
                                            Queue queue){

        return BindingBuilder.bind(queue).to(exchange).with(routingKey);

    }





    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
