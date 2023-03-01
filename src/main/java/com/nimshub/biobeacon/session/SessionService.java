package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.device.Device;
import com.nimshub.biobeacon.device.DeviceRepository;
import com.nimshub.biobeacon.exceptions.DeviceNotFoundException;
import com.nimshub.biobeacon.exceptions.SessionNotFoundException;
import com.nimshub.biobeacon.session.dto.CreateSessionRequest;
import com.nimshub.biobeacon.session.dto.StartSessionRequest;
import com.nimshub.biobeacon.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    /**
     *
     * @param userId : Integer
     * @return List<Session>
     */
    public List<Session>  getSessionsByUserId(Integer userId){
        return sessionRepository.findAllByUserId(userId)
                .orElseThrow(()-> new SessionNotFoundException("Sessions not found for user ID :" + userId));
    }
    public Session startSession(StartSessionRequest request){
        Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(()-> new DeviceNotFoundException("Device Not Found"));

        var user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

                Session session = Session.builder()
                        .deviceId(request.getDeviceId())
                        .userId(user.getId())
                        .build();
        return sessionRepository.save(session);
    }
    @Transactional
    public void createSession(CreateSessionRequest request){

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
