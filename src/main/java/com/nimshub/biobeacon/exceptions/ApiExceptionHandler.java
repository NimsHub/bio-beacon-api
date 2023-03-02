package com.nimshub.biobeacon.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<Object> handleDeviceNotFoundException(DeviceNotFoundException e){
        return new  ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<Object> handleSessionNotFoundException(SessionNotFoundException e){
        return new  ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

}
