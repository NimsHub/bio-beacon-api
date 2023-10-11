package com.nimshub.biobeacon.activities;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Author Nirmala : 11:October:2023
 * This controller implements all end points required to handle the requests of Activity
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final ActivityService activityService;
    @GetMapping("/activity/{id}")
    public ResponseEntity<ActivityTimeResponse> getActivityTimes(@PathVariable UUID id){
        return new ResponseEntity<>(activityService.getActivityTime(id), HttpStatus.OK);
    }
}