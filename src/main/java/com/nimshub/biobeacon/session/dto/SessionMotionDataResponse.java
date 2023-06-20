package com.nimshub.biobeacon.session.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionMotionDataResponse {
    private String deviceOneMotionData;
    private String deviceTwoMotionData;
    private String deviceThreeMotionData;
    private String deviceFourMotionData;
    private String deviceFiveMotionData;
}
