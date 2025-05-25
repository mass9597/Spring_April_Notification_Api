package com.bms.central_api_v1.controller;

import com.bms.central_api_v1.exception.UnAuthorizedException;
import com.bms.central_api_v1.model.Show;
import com.bms.central_api_v1.requestdto.BuyTicketsRB;
import com.bms.central_api_v1.requestdto.CreateShowRB;
import com.bms.central_api_v1.responseBody.BookedDetailsRB;
import com.bms.central_api_v1.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/show")
public class ShowController {

    @Autowired
    ShowService showService;

    @PostMapping("/create")
    public ResponseEntity<?> createShow(@RequestBody CreateShowRB createShowRB,
                                        @RequestParam UUID ownerId){
          try{
              Show show = showService.createShow(createShowRB,ownerId);

              return new ResponseEntity<>(show, HttpStatus.CREATED);
          }catch(UnAuthorizedException e){

              return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
          }
    }

    @GetMapping("/seats")
    public ResponseEntity<?> availableSeats(@PathVariable UUID showId){
        List<Integer> notBookedSeats = showService.availableSeats(showId);

        return new ResponseEntity<>(notBookedSeats,HttpStatus.OK);

    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyTickets(@RequestParam UUID userId,
                                        @RequestParam UUID showId,
                                        @RequestBody BuyTicketsRB buyTicketsRB){
        BookedDetailsRB bookedDetailsRB = showService.buyTickets(userId,showId,buyTicketsRB);

        return new ResponseEntity<>(bookedDetailsRB,HttpStatus.OK);
    }
 }
