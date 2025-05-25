package com.bms.central_api_v1.responseBody;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookedDetailsRB {

    String name;
    String movieName;
    String hallName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Double totalPrice;
}
