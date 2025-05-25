package com.bms.central_api_v1.service;



import com.bms.central_api_v1.exception.AlreadyBookedException;
import com.bms.central_api_v1.exception.InvalidShowException;
import com.bms.central_api_v1.exception.UnAuthorizedException;
import com.bms.central_api_v1.integration.DbApi;
import com.bms.central_api_v1.model.*;
import com.bms.central_api_v1.requestdto.BuyTicketsRB;
import com.bms.central_api_v1.requestdto.CreateShowRB;
import com.bms.central_api_v1.responseBody.BookedDetailsRB;
import com.bms.central_api_v1.responseBody.ShowsByHallIdRB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ShowService {

    @Autowired
    DbApi dbApi;

    public boolean isOverlapping(List<Show> showList,Show show){
        Collections.sort(showList, Comparator.comparingLong(Show::getStartTime));

        for(int i=0; i< showList.size(); i++){
            if(showList.get(i).getEndTime() >= show.getStartTime() && showList.get(i).getStartTime() <= show.getEndTime()){
                return true;
            }
        }

        return false;
    }

    public Show createShow(CreateShowRB createShowRB, UUID ownerId){

        Hall hall = dbApi.getHallById(createShowRB.getHallId());
        Movie movie = dbApi.getMovieById(createShowRB.getMovieId());

        if(!hall.getTheater().getOwner().getId().equals(ownerId)){
            throw new UnAuthorizedException(String.format("ID %s is not a theater owner to create shows"+ownerId.toString()));
        }

        Show show = new Show();

        LocalDateTime clientStartTime = createShowRB.getStartTime();
        LocalDateTime clientEndTime = createShowRB.getEndTime();

        LocalDateTime referenceTime = LocalDateTime.of(2014,1,1,0,0);
        //duration class is used to convert the dateand time format to millis

        Long startTime = Duration.between(referenceTime,clientStartTime).toMillis();
        Long endTime = Duration.between(referenceTime,clientEndTime).toMillis();

        show.setHall(hall);
        show.setPrice(createShowRB.getPrice());
        show.setMovie(movie);
        show.setStartTime(startTime);
        show.setEndTime(endTime);

        ShowsByHallIdRB shows = dbApi.getShowsByHallId(createShowRB.getHallId());

        if(shows == null){
            return dbApi.callCreateShowEndpoint(show);
        }

        List<Show> showList = shows.getShows();

        Boolean isOverlapping = this.isOverlapping(showList,show);

        if(isOverlapping){
            throw new InvalidShowException(String.format("show overlaps with the other shows which has been pre-scheduled in the same hall, so show ID %s cannot be created during this interval"+show.getId().toString()));

        }
        return dbApi.callCreateShowEndpoint(show);

    }


    public List<Integer> availableSeats(UUID showId){
       Show show = dbApi.callGetShowById(showId);


       int totalSeats = show.getHall().getCapacity();

       List<Integer> availableSeat = new ArrayList<>();

       for(int seat=1; seat<=totalSeats; seat++){
           //we need to check if the seats for the showId is already booked or not

           BookedSeat bookedSeat=dbApi.callIsSeatBookedEndPoint(showId,seat);

           if(bookedSeat == null){
               availableSeat.add(seat);
           }

       }
       return availableSeat;
    }

    public BookedDetailsRB buyTickets(UUID userId, UUID showId, BuyTicketsRB buyTicketsRB){
        AppUser user = dbApi.getUserById(userId);
        Show show = dbApi.callGetShowById(showId);

        List<Integer> seats = buyTicketsRB.getSeat();

        Double totalPrice = 0.0;

        for(int seat:seats){

            BookedSeat bookedSeat = dbApi.callIsSeatBookedEndPoint(showId,seat);

            if(bookedSeat == null){
                BookedSeat bookedSeat1 = new BookedSeat();
                bookedSeat1.setShowId(showId);
                bookedSeat1.setSeatNo(seat);

                dbApi.callCreateBookedSeat(bookedSeat1);

                totalPrice += show.getPrice();

            }else{
                throw new AlreadyBookedException(String.format("seat %s is already booked"+seat));
            }
        }

        BookedDetailsRB bookedDetailsRB = new BookedDetailsRB();

        bookedDetailsRB.setName(user.getName());
        bookedDetailsRB.setHallName(show.getHall().getName());
        bookedDetailsRB.setMovieName(show.getMovie().getName());
        bookedDetailsRB.setTotalPrice(totalPrice);

        LocalDateTime startTime = this.convertMillisToLocalDateTime(show.getStartTime());
        LocalDateTime endTime = this.convertMillisToLocalDateTime(show.getEndTime());

        bookedDetailsRB.setStartTime(startTime);
        bookedDetailsRB.setEndTime(endTime);

        return bookedDetailsRB;

    }

    public LocalDateTime convertMillisToLocalDateTime(long millis){
        LocalDateTime referenceTime = LocalDateTime.of(2014,1,1,0,0);

        return referenceTime.plus(millis, ChronoUnit.MILLIS);

    }
}
