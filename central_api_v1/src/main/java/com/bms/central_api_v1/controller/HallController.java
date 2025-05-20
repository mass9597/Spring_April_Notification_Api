package com.bms.central_api_v1.controller;


import com.bms.central_api_v1.exception.UnAuthorizedException;
import com.bms.central_api_v1.model.Hall;
import com.bms.central_api_v1.requestdto.CreateHallRB;
import com.bms.central_api_v1.responseBody.GeneralMessageResponse;
import com.bms.central_api_v1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/hall")
public class HallController {

    @Autowired
    AuthService authService;

    @PostMapping("/create")
    public ResponseEntity createHall(@RequestParam UUID ownerId,
                                     @RequestParam UUID theaterId,
                                     @RequestHeader String Authorization,
                                     @RequestBody CreateHallRB hallRb){

        try{
            authService.verifyToken(Authorization);
            return;


        } catch (UnAuthorizedException e) {
            GeneralMessageResponse response = new GeneralMessageResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }
}
