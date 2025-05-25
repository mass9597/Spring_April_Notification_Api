package com.bms.central_api_v1.model;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Show {

    UUID id;
    Long startTime;
    Long endTime;
    Hall hall;
    Movie movie;
    Double price;
}
