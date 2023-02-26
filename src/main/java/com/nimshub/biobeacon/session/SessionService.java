package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.exceptions.SessionNotFoundException;
import com.nimshub.biobeacon.session.dto.CreateSessionRequest;
import com.nimshub.biobeacon.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    public List<Session>  getSessionsByUserId(Integer userId){
        return sessionRepository.findAllByUserId(userId)
                .orElseThrow(()-> new SessionNotFoundException("Sessions not found for user ID :"+userId));
    }
    @Transactional
    public Session createSession(CreateSessionRequest request){

        var user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

        var session = Session.builder()
                .userId(user.getId())
                .heartRate(request.getHeartRate())
                .bloodPressure(request.getBloodPressure())
                .respirationRate(request.getRespirationRate())
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                .sessionDuration(getDateDifferenceInSeconds(request.getStartDateTime(),request.getEndDateTime()))
                .build();

        return sessionRepository.save(session);
    }

    /**
     * This method calculate time duration
     * @param startDate : Date
     * @param endDate : Date
     * @return Long
     */
    public long getDateDifferenceInSeconds(Date startDate, Date endDate) {
        Instant instant1 = startDate.toInstant();
        Instant instant2 = endDate.toInstant();
        Duration duration = Duration.between(instant1, instant2);
        return duration.getSeconds();
    }
}
