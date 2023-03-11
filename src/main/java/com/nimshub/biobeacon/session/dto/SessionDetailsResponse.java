package com.nimshub.biobeacon.session.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDetailsResponse {
    private String heartRate;
    private String bloodPressure;
    private String respirationRate;
}
