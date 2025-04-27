package com.bms.central_api_v1.service;


import com.bms.central_api_v1.enums.UserType;
import com.bms.central_api_v1.exception.UserNotFoundException;
import com.bms.central_api_v1.integration.DbApi;
import com.bms.central_api_v1.model.AppUser;
import com.bms.central_api_v1.requestdto.CreateUserDb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    DbApi dbApi;

    public Object registerUser(CreateUserDb createUserDb){

        log.info("central api controller call the service and passes the request body"+createUserDb.toString());
         return dbApi.createUser(createUserDb);
    }

    public AppUser getUserById(UUID userId){
         //we need to call dbapi to check if the userid exist in the database

        return dbApi.getUserById(userId);
    }

    public boolean isTheaterOwner(UUID theaterOwnerId){
        AppUser theaterOwner = this.getUserById(theaterOwnerId);

        if(theaterOwner == null){
            throw new UserNotFoundException(String.format(
                    "Invalid theaterOwnerId %s",theaterOwnerId.toString())); // %s act as a placeholder as it is replaced with actual object passed in the string format.
              // String message = String.format(string,object);
        }

        return theaterOwner.getUserType().equals(UserType.THEATER_OWNER.toString()) ? true : false;

    }

    public List<AppUser> getAllAdmins(){
        return dbApi.getAllAdmins();
    }
}
