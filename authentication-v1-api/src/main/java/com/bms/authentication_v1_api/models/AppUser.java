package com.bms.authentication_v1_api.models;

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
