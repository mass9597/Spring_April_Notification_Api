package com.bms.central_api_v1.integration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;


public abstract class RestApi {

    @Autowired
    RestTemplate restTemplate;

    public String addQueryParam(String url,Map<String,?> queryParams){
        if(queryParams.isEmpty()){
            return url;
        }
        //url or URI contains = protocol/domain name/endpoint/?key=value&key=value

        url += "?"; //concatenate ? to add query params
        int count = 1; // we use count variable, to check the no of key-value set, when the count equals the last value of key value set it should not add &
        for(String key : queryParams.keySet()){
            url += (key+ "=" +queryParams.get(key));

            if(count < queryParams.size()) {
                url += "&";

            }
            count++;
        }

        return url;
    }

    public Object makePostCall(String apiBaseUrl,
                             String apiEndPoint,
                             Object requestBody,
                             Map<String,?> queryParams){

        //here we are using object because it is parent class of all classes, because dpi controller returns different classes,therefore we are using generic class

        String url = apiBaseUrl+apiEndPoint;
        url = this.addQueryParam(url,queryParams);
        URI finalUrl = URI.create(url);

        RequestEntity requestEntity = RequestEntity.post(finalUrl).body(requestBody);

        //TO HIT the endpoint we need resttemplate library

        //RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(finalUrl, HttpMethod.POST,requestEntity,Object.class);

        return response.getBody();

    }
    public Object makeGetCall(String apiBaseUrl,
                            String apiEndPoint,
                            Map<String,?> queryParams){

        String url = apiBaseUrl+apiEndPoint;
        url = this.addQueryParam(url,queryParams);
        URI finalUrl = URI.create(url);

        RequestEntity requestEntity = RequestEntity.get(finalUrl).build();

        //TO HIT the endpoint we need resttemplate library

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(finalUrl, HttpMethod.GET,requestEntity,Object.class);

        return response.getBody();

    }

    // method overloading in get call, as we need to pass headers/token
    public Object makeGetCall(String apiBaseUrl,
                              String apiEndPoint,
                              Map<String,?> queryParams,
                              String token){

        String url = apiBaseUrl+apiEndPoint;
        url = this.addQueryParam(url,queryParams);
        URI finalUrl = URI.create(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);

        RequestEntity requestEntity = RequestEntity.get(finalUrl).headers(headers).build();

        //TO HIT the endpoint we need resttemplate library

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(finalUrl, HttpMethod.GET,requestEntity,Object.class);

        return response.getBody();

    }
    public Object makePutCall(String apiBaseUrl,
                            String apiEndPoint,
                            Object requestBody,
                            Map<String,?> queryParams){

        String url = apiBaseUrl+apiEndPoint;
        url = this.addQueryParam(url,queryParams);
        URI finalUrl = URI.create(url);

        RequestEntity requestEntity = RequestEntity.put(finalUrl).body(requestBody);

        //TO HIT the endpoint we need resttemplate library

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(finalUrl, HttpMethod.PUT,requestEntity,Object.class);

        return response.getBody();
    }
    public Object makeDeleteCall(String apiBaseUrl,
                               String apiEndPoint,
                               Map<String,?> queryParams){

        String url = apiBaseUrl+apiEndPoint;
        url = this.addQueryParam(url,queryParams);
        URI finalUrl = URI.create(url);

        RequestEntity requestEntity = RequestEntity.delete(finalUrl).build();

        //TO HIT the endpoint we need resttemplate library

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(finalUrl, HttpMethod.DELETE,requestEntity,Object.class);

        return response.getBody();
    }
}
