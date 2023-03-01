package com.nimshub.biobeacon.session.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartSessionRequest {
    private Long deviceId;
    private String userEmail;
}
