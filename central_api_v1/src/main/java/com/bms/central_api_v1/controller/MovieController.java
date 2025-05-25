package com.bms.central_api_v1.controller;


import com.bms.central_api_v1.exception.UnAuthorizedException;
import com.bms.central_api_v1.model.Movie;
import com.bms.central_api_v1.requestdto.CreateMovieRB;
import com.bms.central_api_v1.responseBody.GeneralMessageResponse;
import com.bms.central_api_v1.service.AuthService;
import com.bms.central_api_v1.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/movie")
public class MovieController {

    @Autowired
    AuthService authService;

    @Autowired
    MovieService movieService;

    @PostMapping("/create")
    public ResponseEntity<?> createMovie(@RequestParam UUID movieOwnerId,
                                         @RequestBody CreateMovieRB createMovieRB,
                                         @RequestHeader String Authorization){
        try{
            authService.verifyToken(Authorization);
            Movie movie = movieService.createMovie(movieOwnerId,createMovieRB);
            return new ResponseEntity<>(movie,HttpStatus.CREATED);

        } catch (UnAuthorizedException e) {
            GeneralMessageResponse response = new GeneralMessageResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
