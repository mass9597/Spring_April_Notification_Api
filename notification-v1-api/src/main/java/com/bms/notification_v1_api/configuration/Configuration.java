package com.bms.notification_v1_api.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public JavaMailSender getJavaMailSender(){
        return new JavaMailSenderImpl();
    }

    @Bean
    public TemplateEngine getTemplateEngine(){
        return new TemplateEngine();
    }
}
