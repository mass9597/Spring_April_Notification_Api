package com.bms.central_api_v1.integration;

import com.bms.central_api_v1.model.*;
import com.bms.central_api_v1.requestdto.CreateTheaterRB;
import com.bms.central_api_v1.requestdto.CreateUserDb;
import com.bms.central_api_v1.responseBody.AdminResponseBody;
import com.bms.central_api_v1.responseBody.ShowsByHallIdRB;
import com.bms.central_api_v1.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class DbApi extends RestApi{

    @Value("${db.api.base}")
    String baseUrl;

    @Autowired
    Mapper mapper;

    @Autowired
    ModelMapper modelMapper;

    public AppUser createUser(CreateUserDb createUserDb){

        AppUser user = mapper.mapCreateUserDbToAppUser(createUserDb); // this will convert the requestdto to appuser class

        String apiEndPoint = "/user/create";

        Object response = this.makePostCall(baseUrl,apiEndPoint,user, new HashMap<>());

        AppUser res = modelMapper.map(response,AppUser.class);

        return res;
    }

    public AppUser getUserById(UUID userID){

        String endPoint = "/user/"+ userID.toString();

        Object response = this.makeGetCall(baseUrl,endPoint,new HashMap<>());

        if(response == null){
            return null;
        }

        AppUser user = modelMapper.map(response,AppUser.class);

        return user;

    }

    public Theater createTheater(CreateTheaterRB createTheaterRB, AppUser owner){

        // convert the dto to theater class

        Theater theater = mapper.mapCreateTheaterRBToTheater(createTheaterRB,owner);

        String endPoint = "/theater/create";

        Object res = this.makePostCall(baseUrl,endPoint,theater,new HashMap<>());

        return modelMapper.map(res,Theater.class);
    }

    public List<AppUser> getAllAdmins(){

        String endPoint = "/user/admins";

        Object response = this.makeGetCall(baseUrl,endPoint,new HashMap<>());

        AdminResponseBody admins = modelMapper.map(response,AdminResponseBody.class);

        return admins.getAdmins();

    }

    public Theater getTheaterById(UUID theaterId){
        String endPoint = "/theater/"+ theaterId;

        Object response = this.makeGetCall(baseUrl,endPoint,new HashMap<>());

        return modelMapper.map(response, Theater.class);
    }

    public Theater updateTheater(Theater theater){
         String endPoint = "/theater/update";

         Object response = this.makePutCall(baseUrl,endPoint,theater,new HashMap<>());
         return modelMapper.map(response, Theater.class);
    }

    public Hall createHall(Hall hall){
        String endPoint = "/hall/create";

        Object response = this.makePostCall(baseUrl,endPoint,hall,new HashMap<>());
        return modelMapper.map(response, Hall.class);
    }

    public Movie createMovie(Movie movie){
        String endpoint = "/movie/create";

        Object response = this.makePostCall(baseUrl,endpoint,movie,new HashMap<>());

        return modelMapper.map(response, Movie.class);
    }

    public Hall getHallById(UUID hallId){
        String endPoint = "/hall/"+hallId.toString();

        Object response = this.makeGetCall(baseUrl,endPoint,new HashMap<>());

        return modelMapper.map(response,Hall.class);
    }

    public Movie getMovieById(UUID movieId){
        String endPoint = "/movie/"+ movieId.toString();

        Object response = this.makeGetCall(baseUrl,endPoint,new HashMap<>());

        return modelMapper.map(response, Movie.class);
    }

    public ShowsByHallIdRB getShowsByHallId(UUID hallId){
        String endPoint = "/show/hall/"+ hallId.toString();

        Object response = this.makeGetCall(baseUrl,endPoint,new HashMap<>());

        return modelMapper.map(response, ShowsByHallIdRB.class);
    }

    public Show callCreateShowEndpoint(Show show){
        String endPoint = "/show/create";

        Object response = this.makePostCall(baseUrl,endPoint,show,new HashMap<>());

        return modelMapper.map(response,Show.class);
    }

    public Show callGetShowById(UUID showId){
        String endPoint = "/show/"+ showId.toString();

        Object response = this.makeGetCall(baseUrl,endPoint,new HashMap<>());

        return modelMapper.map(response,Show.class);
    }

    public BookedSeat callIsSeatBookedEndPoint(UUID showId, int seat){

        String endPoint = "/bookedSeat/check";

        HashMap<String,String> queryParam = new HashMap<>();

        queryParam.put("showId",showId.toString());
        queryParam.put("seat",seat+"");

       Object response = this.makeGetCall(baseUrl,endPoint,queryParam);

       if(response == null){
           return null;
       }

       return modelMapper.map(response, BookedSeat.class);
    }

    public void callCreateBookedSeat(BookedSeat bookedSeat){
        String endPoint = "/bookedSeat/create";

        this.makePostCall(baseUrl,endPoint,bookedSeat,new HashMap<>());

    }
}
