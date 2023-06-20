package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.session.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    Logger logger = LoggerFactory.getLogger(SessionController.class);

    @PostMapping("/create-session")
    @PreAuthorize("hasAuthority('ATHLETE')")
    public ResponseEntity<String> createSession(@RequestBody CreateSessionRequest request) {
        sessionService.createSession(request);
        logger.info("new session has been created from : {}", request);
        return new ResponseEntity<>("new session has been created", HttpStatus.CREATED);
    }

    @GetMapping("/athlete/{id}")
    public ResponseEntity<List<SessionResponse>> getSessionsByAthleteId(@PathVariable("id") UUID id) {
        List<SessionResponse> sessions = sessionService.getSessionsByAthleteId(id);
        logger.info("getting sessions of the user : {}", id);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @PostMapping("/update-session")
    @Transactional(timeout = 300)
    public ResponseEntity<String> updateSession(@RequestBody UpdateSessionRequest request) {
        sessionService.updateSessionDetails(request);
        logger.info("session from {} has been updated", request);
        return new ResponseEntity<>("session details has been updated", HttpStatus.OK);
    }

    @GetMapping("/session-details/{id}")
    public ResponseEntity<SessionDetailsResponse> getSessionDetailsBySessionId(@PathVariable("id") UUID id) {
        SessionDetailsResponse sessionDetailsResponse = sessionService.getSessionDetails(id);
        logger.info("getting session details of the session : {}", id);
        return new ResponseEntity<>(sessionDetailsResponse, HttpStatus.OK);
    }
    @GetMapping("/session-motion-data/{id}")
    public ResponseEntity<SessionMotionDataResponse> getSessionMotionDataBySessionId(@PathVariable("id") UUID id) {
        SessionMotionDataResponse motionData = sessionService.getMotionData(id);
        logger.info("getting motion data of the session : {}", id);
        return new ResponseEntity<>(motionData, HttpStatus.OK);
    }
}
