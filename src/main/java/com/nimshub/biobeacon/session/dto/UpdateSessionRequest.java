package com.nimshub.biobeacon.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateSessionRequest {
    @Email
    private String userEmail;
    private Long deviceId;
    private String heartRate;
    private String bloodPressure;
    private String respirationRate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endDateTime;

}
