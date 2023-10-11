package com.nimshub.biobeacon.activities;

import com.nimshub.biobeacon.exceptions.SessionNotFoundException;
import com.nimshub.biobeacon.session.Session;
import com.nimshub.biobeacon.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author Nirmala : 11:October:2023
 * This service implements all methods required to handle the business logic of Activity
 */
@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityTimeRepository activityTimeRepository;
    private final SessionRepository sessionRepository;

    public ActivityTimeResponse getActivityTime(UUID id) {
        Session session = sessionRepository.findBySessionId(id)
                .orElseThrow(() -> new SessionNotFoundException("Session Not Found"));

        ActivityTime activityTime = activityTimeRepository.findBySession(session)
                .orElseThrow(() -> new SessionNotFoundException("Session Not Found"));


        return ActivityTimeResponse.builder()
                .cycling(activityTime.getCycling())
                .pushUp(activityTime.getPushUp())
                .squat(activityTime.getSquat())
                .tableTennis(activityTime.getTableTennis())
                .running(activityTime.getRunning())
                .walking(activityTime.getWalking())
                .build();
    }
}