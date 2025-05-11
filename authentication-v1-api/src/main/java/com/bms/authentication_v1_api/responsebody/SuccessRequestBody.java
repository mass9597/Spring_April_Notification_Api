package com.bms.authentication_v1_api.responsebody;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SuccessRequestBody {

    String response;
}
