package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.config.JwtAuthFilter;
import com.nimshub.biobeacon.session.dto.CreateSessionRequest;
import com.nimshub.biobeacon.session.dto.StartSessionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    Logger logger = LoggerFactory.getLogger(SessionController.class);

    @PostMapping("/create-session")
    public ResponseEntity<?> createSession(@RequestBody StartSessionRequest request){
            Session session = sessionService.createSession(request);
            logger.info("new session has been created");
            return new  ResponseEntity<>(session, HttpStatus.CREATED);
    }
    @PostMapping("/update-session")
    public ResponseEntity<String> updateSession(@RequestBody CreateSessionRequest request){
            sessionService.updateSession(request);
            logger.info("session has been updated");
            return new  ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{userid}")
    public ResponseEntity<?> getSessionsByUserId(@PathVariable("userid") Integer userid){
            List<Session> sessions = sessionService.getSessionsByUserId(userid);
            logger.info("getting sessions of the user : " + userid);
            return new ResponseEntity<>(sessions,HttpStatus.OK);
    }
}
