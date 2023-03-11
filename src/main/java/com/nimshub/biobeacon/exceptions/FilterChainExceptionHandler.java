package com.nimshub.biobeacon.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class FilterChainExceptionHandler {
    private FilterChainExceptionHandler() {
    }

    public void handelException(
            Exception e,
            HttpServletResponse response,
            HttpServletRequest request) throws IOException {

        HttpStatus httpStatus;

        if (e.getClass().equals(DeviceNotFoundException.class)) httpStatus = HttpStatus.NOT_FOUND;
        else httpStatus = HttpStatus.UNAUTHORIZED;

        ApiError apiError = ApiError.builder()
                .message(e.getMessage())
                .uri(request.getRequestURI())
                .trace(e.getStackTrace()[0])
                .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                .status(httpStatus)
                .build();

        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule()); // Register the JSR-310 module
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Configure to write dates in ISO-8601 format

        JsonNode error = mapper.valueToTree(apiError);

        response.setContentType("application/json");
        response.getWriter().print(error);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
