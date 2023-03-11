package com.nimshub.biobeacon.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder
@Data
public class ApiError {
    private HttpStatus status;
    private String message;
    private String uri;
    private ZonedDateTime timeStamp;
    private StackTraceElement trace;

}
