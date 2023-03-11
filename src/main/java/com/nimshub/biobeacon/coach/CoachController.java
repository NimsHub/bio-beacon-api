package com.nimshub.biobeacon.coach;

import com.nimshub.biobeacon.auth.AuthenticationResponse;
import com.nimshub.biobeacon.coach.dto.CoachDetailsResponse;
import com.nimshub.biobeacon.coach.dto.CoachResponse;
import com.nimshub.biobeacon.coach.dto.CreateCoachRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coaches")
public class CoachController {
    private final CoachService coachService;
    Logger logger = LoggerFactory.getLogger(CoachController.class);

    @PostMapping("/create-coach")
    public ResponseEntity<AuthenticationResponse> createCoach(@RequestBody CreateCoachRequest request) {
        AuthenticationResponse authenticationResponse = coachService.createCoach(request);
        logger.info("new coach has been created from : {}", request);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CoachResponse>> getCoaches() {
        List<CoachResponse> coaches = coachService.getCoaches();
        logger.info("list of all coaches has been retrieved");
        return new ResponseEntity<>(coaches, HttpStatus.OK);
    }

    @GetMapping("/get-coach")
    public ResponseEntity<CoachDetailsResponse> getCoach(@RequestHeader("Authorization") String authHeader) {
        CoachDetailsResponse response = coachService.getCoach(authHeader);
        logger.info("coach details retrieved : {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-coach")
    public ResponseEntity<AuthenticationResponse> updateCoach(
            @RequestBody CreateCoachRequest request, @RequestHeader("Authorization") String authHeader) {
        AuthenticationResponse response = coachService.updateCoach(request, authHeader);
        logger.info("successfully updated coach : {}", request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
