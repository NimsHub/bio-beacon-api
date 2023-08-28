package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.athlete.AthleteRepository;
import com.nimshub.biobeacon.device.Device;
import com.nimshub.biobeacon.device.DeviceRepository;
import com.nimshub.biobeacon.exceptions.DeviceNotFoundException;
import com.nimshub.biobeacon.exceptions.SessionNotFoundException;
import com.nimshub.biobeacon.session.dto.*;
import com.nimshub.biobeacon.utils.BitReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
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
    private final SessionDetailsRepositoryRepository sessionDetailsRepositoryRepository;
    private final SessionMotionDataRepository sessionMotionDataRepository;
    private final BitReader bitReader;

    /**
     * @param id : UUID
     * @return List<Session>
     */
    public List<SessionResponse> getSessionsByAthleteId(UUID id) {
        log.info("getting all the sessions for the user id :{}", id);
        List<Session> sessions = sessionRepository.findAllByAthleteId(id).orElseThrow();
        if (sessions.isEmpty()) {
            log.info("Sessions not found for user ID : {}", id);
            throw new SessionNotFoundException("Sessions not found for user ID : [%s]".formatted(id));
        }
        return sessions.stream().map(session ->
                SessionResponse.builder()
                        .sessionId(session.getSessionId())
                        .deviceId(session.getDeviceId())
                        .isComplete(session.isComplete())
                        .startDateTime(session.getStartDateTime())
                        .endDateTime(session.getEndDateTime())
                        .sessionDuration(session.getSessionDuration())
                        .build()).toList();
    }

    /**
     * This method created a new session
     *
     * @param request : StartSessionRequest
     */
    public void createSession(CreateSessionRequest request) {

        Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new DeviceNotFoundException("Device with id : [%s] Not Found"
                        .formatted(request.getDeviceId())));

        var athlete = athleteRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email : [%s] Not Found"
                        .formatted(request.getUserEmail())));

        Session session = Session.builder()
                .sessionId(UUID.randomUUID())
                .deviceId(device.getId())
                .athleteId(athlete.getAthleteId())
                .build();
        sessionRepository.save(session);
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

    /**
     * This method updates the session details
     *
     * @param request : UpdateSessionRequest
     */
    @Transactional
    public void updateSessionDetails(UpdateSessionRequest request) {

        Session session = sessionRepository
                .findTopByDeviceIdAndIsCompleteFalseOrderByCreateDateTimeDesc(
                        request.getDeviceId())
                .orElseThrow(() -> new SessionNotFoundException("Session Not Found"));

        session.setStartDateTime(request.getStartDateTime());
        session.setEndDateTime(request.getEndDateTime());
        session.setSessionDuration(getDateDifferenceInSeconds(request.getStartDateTime(), request.getEndDateTime()));
        session.setComplete(true);

        SessionDetails sessionDetails = SessionDetails.builder()
                .session(session)
                .bloodOxygen(bitReader.getVitalRecord(request.getBloodOxygen()))
                .respirationRate(bitReader.getVitalRecord(request.getRespirationRate()))
                .heartRate(bitReader.getVitalRecord(request.getHeartRate()))
                .ecg(request.getEcg())
                .build();

        Map<Integer, String> data = bitReader.getMotionData(request.getModules(), request.getMotionData());

        SessionMotionData motionData = SessionMotionData.builder()
                .session(session)
                .deviceOneMotionData(data.get(1))
                .deviceTwoMotionData(data.get(2))
                .deviceThreeMotionData(data.get(3))
                .deviceFourMotionData(data.get(4))
                .deviceFiveMotionData(data.get(5))
                .build();

        sessionDetailsRepositoryRepository.save(sessionDetails);
        sessionMotionDataRepository.save(motionData);
    }

    /**
     * This method retrieves the details of a session
     *
     * @param sessionId : UUID
     * @return SessionDetailsResponse
     */
    public SessionDetailsResponse getSessionDetails(UUID sessionId) {
        Session session = sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session with ID : [%s] not found"
                        .formatted(sessionId)));

        SessionDetails sessionDetails = sessionDetailsRepositoryRepository
                .findSessionDetailsBySession(session)
                .orElseThrow(() -> new SessionNotFoundException("Session Details for session : [%s] not found"
                        .formatted(sessionId)));

        return SessionDetailsResponse.builder()
                .heartRate(sessionDetails.getHeartRate())
                .bloodOxygen(sessionDetails.getBloodOxygen())
                .respirationRate(sessionDetails.getRespirationRate())
                .build();
    }

    /**
     * This method retrieves motion data of a session
     * @param sessionId : UUID
     * @return SessionMotionDataResponse
     */
    public SessionMotionDataResponse getMotionData(UUID sessionId) {
        Session session = sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session with ID : [%s] not found"
                        .formatted(sessionId)));

        SessionMotionData motionData = sessionMotionDataRepository.findSessionMotionDataBySession(session)
                .orElseThrow(() -> new SessionNotFoundException("Motion data for session : [%s] not found"
                        .formatted(sessionId)));

        return SessionMotionDataResponse.builder()
                .deviceOneMotionData(motionData.getDeviceOneMotionData())
                .deviceTwoMotionData(motionData.getDeviceTwoMotionData())
                .deviceThreeMotionData(motionData.getDeviceThreeMotionData())
                .deviceFourMotionData(motionData.getDeviceFourMotionData())
                .deviceFiveMotionData(motionData.getDeviceFiveMotionData())
                .build();
    }

}
