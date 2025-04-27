package com.bms.central_api_v1.requestdto;

import com.bms.central_api_v1.model.AppUser;
import com.bms.central_api_v1.model.Theater;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTheaterNotificationRB
{
    Theater theater;
    AppUser Admin;
}
