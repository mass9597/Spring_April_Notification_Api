package com.bms.central_api_v1.util;

import com.bms.central_api_v1.enums.StatusType;
import com.bms.central_api_v1.model.AppUser;
import com.bms.central_api_v1.model.Theater;
import com.bms.central_api_v1.requestdto.CreateTheaterRB;
import com.bms.central_api_v1.requestdto.CreateUserDb;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Mapper {

    public AppUser mapCreateUserDbToAppUser(CreateUserDb createUserDb){

        AppUser user = new AppUser();

        user.setName(createUserDb.getName());
        user.setAddress(createUserDb.getAddress());
        user.setUserType(createUserDb.getUserType().toString());
        user.setEmail(createUserDb.getEmail());
        user.setPassword(createUserDb.getPassword());
        user.setPhoneNumber(createUserDb.getPhoneNumber());
        user.setPinCode(createUserDb.getPinCode());
        user.setState(createUserDb.getState());

        return user;

    }

    public Theater mapCreateTheaterRBToTheater(CreateTheaterRB createTheaterRB,AppUser owner){

        Theater theater = new Theater();

        theater.setAddress(createTheaterRB.getAddress());
        theater.setName(createTheaterRB.getName());
        theater.setState(createTheaterRB.getState());
        theater.setPinCode(createTheaterRB.getPinCode());
        theater.setOwner(owner);
        theater.setStatus(StatusType.REQUEST_RAISED.toString());

        return theater;
    }
}
