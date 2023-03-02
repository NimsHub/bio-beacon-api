package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.device.Device;
import com.nimshub.biobeacon.device.DeviceRepository;
import com.nimshub.biobeacon.exceptions.DeviceNotFoundException;
import com.nimshub.biobeacon.exceptions.SessionNotFoundException;
import com.nimshub.biobeacon.session.dto.CreateSessionRequest;
import com.nimshub.biobeacon.session.dto.StartSessionRequest;
import com.nimshub.biobeacon.user.UserRepository;
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

/**
 * This class provides all the services related to sessions
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    /**
     *
     * @param userId : Integer
     * @return List<Session>
     */
    public List<Session> getSessionsByUserId(Integer userId) {
        log.info("getting all the sessions for the user id :" + userId);
        List<Session> sessions = sessionRepository.findAllByUserId(userId).orElseThrow();
        if (sessions.isEmpty()) {
            log.info("Sessions not found for user ID :" + userId);
            throw new SessionNotFoundException("Sessions not found for user ID :" + userId);
        }
        return sessions;
    }

    /**
     * This method created a new session
     * @param request : StartSessionRequest
     * @return Session
     */
    public Session createSession(StartSessionRequest request){

        deviceRepository.findById(request.getDeviceId())
                .orElseThrow(()-> new DeviceNotFoundException("Device Not Found"));
        var user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

                Session session = Session.builder()
                        .deviceId(request.getDeviceId())
                        .userId(user.getId())
                        .build();
        return sessionRepository.save(session);
    }

    /**
     * This method updates the data in created session
     * @param request : CreateSessionRequest
     */
    @Transactional
    public void updateSession(CreateSessionRequest request){
        deviceRepository.findById(request.getDeviceId())
                .orElseThrow(()-> new DeviceNotFoundException("Device Not Found"));

      sessionRepository.updateSession(
                request.getDeviceId(),
                request.getHeartRate(),
                request.getBloodPressure(),
                request.getRespirationRate(),
                request.getStartDateTime(),
                request.getEndDateTime(),
              getDateDifferenceInSeconds(request.getStartDateTime(),request.getEndDateTime())
                );
    }

    /**
     * This method calculate time duration
     * @param startDate : Date
     * @param endDate : Date
     * @return Long
     */
    public long getDateDifferenceInSeconds(LocalDateTime startDate, LocalDateTime endDate) {
        Instant start = startDate.toInstant(ZoneOffset.UTC);
        Instant end = endDate.toInstant(ZoneOffset.UTC);
        Duration duration = Duration.between(start, end);
        return duration.getSeconds();
    }
}
