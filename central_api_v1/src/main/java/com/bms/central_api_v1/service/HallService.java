package com.bms.central_api_v1.service;

import com.bms.central_api_v1.exception.UnAuthorizedException;
import com.bms.central_api_v1.integration.DbApi;
import com.bms.central_api_v1.model.Hall;
import com.bms.central_api_v1.model.Theater;
import com.bms.central_api_v1.requestdto.CreateHallRB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HallService {

    @Autowired
    DbApi dbApi;

    public Hall callCreateHall(UUID ownerId, UUID theaterId, String Authorization, CreateHallRB hallRB){

        Theater theater = dbApi.getTheaterById(theaterId);

        if(!theater.getOwner().getId().equals(theaterId)){
            throw new UnAuthorizedException(String.format(
                    "Theater with id %s does not own by owner with id %s ",theaterId.toString(),ownerId.toString()
            ));
        }

        Hall hall = new Hall();
        hall.setCapacity(hallRB.getCapacity());
        hall.setName(hallRB.getName());
        hall.setTheater(theater);

        return dbApi.createHall(hall);
    }
}
