package com.bms.central_api_v1.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookedSeat {

    UUID id;
    UUID showId;
    int seatNo;

}
