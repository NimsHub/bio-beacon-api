package com.nimshub.biobeacon.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Data;
import java.util.Date;

@Data
public class CreateSessionRequest {
    @Email
    private String userEmail;
    private String heartRate;
    private String bloodPressure;
    private String respirationRate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Date startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private Date endDateTime;

}
