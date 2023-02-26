package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.session.dto.CreateSessionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
@Slf4j
public class SessionController {
    private final SessionService sessionService;
    @PostMapping("/create-session")
    public ResponseEntity<Session> createSession(@RequestBody CreateSessionRequest request){
        log.info("creating new session");
        Session session = sessionService.createSession(request);
        return new  ResponseEntity<>(session, HttpStatus.CREATED);
    }
    @GetMapping("/{userid}")
    public ResponseEntity<List<Session>> getSessionsByUserId(@PathVariable("userid") Integer userid){
        List<Session> sessions = sessionService.getSessionsByUserId(userid);
        log.info("getting sessions of the user : " + userid);
        return new ResponseEntity<>(sessions,HttpStatus.OK);
    }
}
