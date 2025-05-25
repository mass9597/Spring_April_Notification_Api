package com.bms.central_api_v1.service;


import com.bms.central_api_v1.exception.UnAuthorizedException;
import com.bms.central_api_v1.integration.DbApi;
import com.bms.central_api_v1.model.AppUser;
import com.bms.central_api_v1.model.Movie;
import com.bms.central_api_v1.requestdto.CreateMovieRB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

@Slf4j
public class MovieService {

    @Autowired
    DbApi dbApi;

    public Movie createMovie(UUID movieOwnerId,
                             CreateMovieRB createMovieRB){

        log.info("values of create request movie request body received from the client"+
                createMovieRB.toString());

        AppUser movieOwner = dbApi.getUserById(movieOwnerId);

        if(!movieOwner.getUserType().equals("MOVIE_OWNER")){
            throw new UnAuthorizedException(String.format("User with id %s is not a Movie Owner,so He/she is not authorized to create a movie in our application ",movieOwner.getId().toString()));
        }

        Movie movie = new Movie();

        movie.setName(createMovieRB.getName());
        movie.setDuration(createMovieRB.getDuration());
        movie.setMovieOwner(movieOwner);
        movie.setReleased(createMovieRB.isReleased());
        movie.setLanguage(createMovieRB.getLanguage());
        movie.setReview(0);
        movie.setTotalReviewVotes(0);

        return dbApi.createMovie(movie);


    }

}
