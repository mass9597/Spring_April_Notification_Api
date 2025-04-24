package com.bms.notification_v1_api.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUser {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Long phoneNumber;
    private String address;
    private int pinCode;
    private String state;
    private String userType;
}
