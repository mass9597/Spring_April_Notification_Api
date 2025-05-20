package com.bms.central_api_v1.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Hall {

    private UUID id;
    private String name;
    private int capacity;
    private Theater theater;
}
