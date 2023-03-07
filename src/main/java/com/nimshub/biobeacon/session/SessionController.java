package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.session.dto.CreateSessionRequest;
import com.nimshub.biobeacon.session.dto.UpdateSessionRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    Logger logger = LoggerFactory.getLogger(SessionController.class);

    @PostMapping("/create-session")
    public ResponseEntity<?> createSession(@RequestBody CreateSessionRequest request) {
        Session session = sessionService.createSession(request);
        logger.info("new session has been created from : {}", request);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    @PostMapping("/update-session")
    public ResponseEntity<String> updateSession(@RequestBody UpdateSessionRequest request) {
        sessionService.updateSession(request);
        logger.info("session from {} has been updated", request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ATHLETE')")
    public ResponseEntity<?> getSessionsByAthleteId(@PathVariable("id") UUID id) {
        List<Session> sessions = sessionService.getSessionsByAthleteId(id);
        logger.info("getting sessions of the user : {}", id);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }
}
