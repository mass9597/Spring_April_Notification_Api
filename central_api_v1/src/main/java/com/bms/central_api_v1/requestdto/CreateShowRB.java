package com.bms.central_api_v1.requestdto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CreateShowRB {

    UUID movieId;
    UUID hallId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Double price;
}
