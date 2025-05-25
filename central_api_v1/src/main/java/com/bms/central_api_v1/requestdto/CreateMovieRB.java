package com.bms.central_api_v1.requestdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CreateMovieRB {

    String name;
    Double duration;
    boolean released;
    String language;
}
