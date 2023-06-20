package com.nimshub.biobeacon.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateSessionRequest {
    private Long deviceId;
    private String heartRate;
    private String bloodOxygen;
    private String respirationRate;
    private String ecg;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endDateTime;
    private String modules;
    private String motionData;
}
