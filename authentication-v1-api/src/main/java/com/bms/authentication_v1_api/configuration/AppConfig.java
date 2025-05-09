package com.bms.authentication_v1_api.configuration;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
   public ModelMapper getModelMapper(){
       return new ModelMapper();
   }

   @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
   }
}
