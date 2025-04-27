package com.bms.notification_v1_api.configuration;


import org.modelmapper.ModelMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

import java.util.Properties;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public JavaMailSender getJavaMailSender(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("accioshoppingwebsite@gmail.com");
        mailSender.setPassword("relcfdwhahhcvokv");

        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Bean
    public TemplateEngine getTemplateEngine(){
        return new TemplateEngine();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
