package com.bms.notification_v1_api.controller;

import com.bms.notification_v1_api.requestbody.TheaterRequestBody;
import com.bms.notification_v1_api.service.TheaterMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification/theater")
public class TheaterController {

    @Autowired
    TheaterMailService mailService;

    @PutMapping("/notify")
    public void notifyAdminForTheaterCreation(@RequestBody TheaterRequestBody theaterRequestBody) throws MessagingException {

        mailService.notifyAdminForTheaterCreation(theaterRequestBody);

    }

}
