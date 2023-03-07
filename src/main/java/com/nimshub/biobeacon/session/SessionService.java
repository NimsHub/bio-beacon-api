package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.athlete.AthleteRepository;
import com.nimshub.biobeacon.device.DeviceRepository;
import com.nimshub.biobeacon.exceptions.DeviceNotFoundException;
import com.nimshub.biobeacon.exceptions.SessionNotFoundException;
import com.nimshub.biobeacon.session.dto.CreateSessionRequest;
import com.nimshub.biobeacon.session.dto.UpdateSessionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

/**
 * This class provides all the services related to sessions
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {
    private final SessionRepository sessionRepository;
    private final AthleteRepository athleteRepository;
    private final DeviceRepository deviceRepository;

    /**
     * @param id : UUID
     * @return List<Session>
     */
    public List<Session> getSessionsByAthleteId(UUID id) {
        log.info("getting all the sessions for the user id :{}", id);
        List<Session> sessions = sessionRepository.findAllByAthleteId(id).orElseThrow();
        if (sessions.isEmpty()) {
            log.info("Sessions not found for user ID : {}", id);
            throw new SessionNotFoundException("Sessions not found for user ID :" + id);
        }
        return sessions;
    }

    /**
     * This method created a new session
     *
     * @param request : StartSessionRequest
     * @return Session
     */
    public Session createSession(CreateSessionRequest request) {

        deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new DeviceNotFoundException("Device Not Found"));
        var athlete = athleteRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Session session = Session.builder()
                .id(UUID.randomUUID())
                .deviceId(request.getDeviceId())
                .athleteId(athlete.getId())
                .build();
        return sessionRepository.save(session);
    }

    /**
     * This method updates the data in created session
     *
     * @param request : CreateSessionRequest
     */
    @Transactional
    public void updateSession(UpdateSessionRequest request) {

        sessionRepository.updateSession(
                request.getDeviceId(),
                request.getHeartRate(),
                request.getBloodPressure(),
                request.getRespirationRate(),
                request.getStartDateTime(),
                request.getEndDateTime(),
                getDateDifferenceInSeconds(request.getStartDateTime(), request.getEndDateTime())
        );
    }

    /**
     * This method calculate time duration
     *
     * @param startDate : Date
     * @param endDate   : Date
     * @return Long
     */
    public long getDateDifferenceInSeconds(LocalDateTime startDate, LocalDateTime endDate) {
        Instant start = startDate.toInstant(ZoneOffset.UTC);
        Instant end = endDate.toInstant(ZoneOffset.UTC);
        Duration duration = Duration.between(start, end);
        return duration.getSeconds();
    }
}
