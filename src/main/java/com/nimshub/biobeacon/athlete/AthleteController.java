package com.nimshub.biobeacon.athlete;

import com.nimshub.biobeacon.athlete.dto.AthleteDetailsResponse;
import com.nimshub.biobeacon.athlete.dto.AthleteResponse;
import com.nimshub.biobeacon.athlete.dto.CreateAthleteRequest;
import com.nimshub.biobeacon.auth.AuthenticationResponse;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/athletes")
public class AthleteController {
    private final AthleteService athleteService;
    Logger logger = LoggerFactory.getLogger(AthleteController.class);

    @PostMapping("/create-athlete")
    public ResponseEntity<AuthenticationResponse> createAthlete(@RequestBody CreateAthleteRequest request) {
        AuthenticationResponse authenticationResponse = athleteService.createAthlete(request);
        logger.info("new athlete has been created from : {}", request);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AthleteResponse>> getAthletes() {
        List<AthleteResponse> athletes = athleteService.getAthletes();
        logger.info("list of all athletes has been retrieved");
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @GetMapping("/get-athlete")
    public ResponseEntity<AthleteDetailsResponse> getAthleteByToken(@RequestHeader("Authorization") String authHeader) {
        AthleteDetailsResponse response = athleteService.getAthleteByToken(authHeader);
        logger.info("athlete details retrieved : {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/coach/{id}")
    @PreAuthorize("hasAuthority('COACH')")
    public ResponseEntity<List<AthleteResponse>> getAthletesByCoachId(@PathVariable UUID id) {
        List<AthleteResponse> athletes = athleteService.getAthletesByCoachId(id);
        logger.info("athletes of coach : {} retrieved", id);
        return new ResponseEntity<>(athletes, HttpStatus.OK);
    }

    @GetMapping("/athlete/{id}")
    public ResponseEntity<AthleteDetailsResponse> getAthleteDetailsByAthleteId(@PathVariable UUID id) {
        AthleteDetailsResponse response = athleteService.getAthleteDetailsByAthleteId(id);
        logger.info("athlete details retrieved : {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-athlete/{id}")
    public ResponseEntity<String> updateAthlete(@RequestBody CreateAthleteRequest request, @PathVariable UUID id) {
        athleteService.updateAthlete(request, id);
        logger.info("successfully updated athlete : {}", request);
        return new ResponseEntity<>("Profile Updated", HttpStatus.OK);
    }
}
