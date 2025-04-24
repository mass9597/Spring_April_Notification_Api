package com.bms.notification_v1_api.requestbody;

import com.bms.notification_v1_api.model.AppUser;
import com.bms.notification_v1_api.model.Theater;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TheaterRequestBody {

    Theater theater;
    AppUser Admin;
}
