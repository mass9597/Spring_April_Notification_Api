package com.bms.central_api_v1.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Movie {


    private UUID id;
    private String name;
    private Double duration;
    private boolean released;
    private int review;
    private int totalReviewVotes;
    private String language;
    private AppUser movieOwner;

}
