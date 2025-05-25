package com.bms.central_api_v1.responseBody;

import com.bms.central_api_v1.model.Show;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ShowsByHallIdRB {

    List<Show> shows;
}
