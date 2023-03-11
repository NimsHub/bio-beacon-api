package com.nimshub.biobeacon.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<Object> handleDeviceNotFoundException(DeviceNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(e.getMessage())
                        .uri(request.getRequestURI())
                        .trace(e.getStackTrace()[0])
                        .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<Object> handleSessionNotFoundException(SessionNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(e.getMessage())
                        .uri(request.getRequestURI())
                        .trace(e.getStackTrace()[0])
                        .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.CONFLICT)
                        .message(e.getMessage())
                        .uri(request.getRequestURI())
                        .trace(e.getStackTrace()[0])
                        .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(e.getMessage())
                        .uri(request.getRequestURI())
                        .trace(e.getStackTrace()[0])
                        .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AthleteNotFoundException.class)
    public ResponseEntity<Object> handleAthleteNotFoundException(AthleteNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(e.getMessage())
                        .uri(request.getRequestURI())
                        .trace(e.getStackTrace()[0])
                        .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CoachNotFoundException.class)
    public ResponseEntity<Object> handleCoachNotFoundException(CoachNotFoundException e, HttpServletRequest request) {

        return new ResponseEntity<>(
                ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(e.getMessage())
                        .uri(request.getRequestURI())
                        .trace(e.getStackTrace()[0])
                        .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                        .build(), HttpStatus.NOT_FOUND);
    }
}
