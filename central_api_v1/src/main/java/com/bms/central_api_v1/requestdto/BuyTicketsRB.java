package com.bms.central_api_v1.requestdto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BuyTicketsRB {

    List<Integer> seat;
}
