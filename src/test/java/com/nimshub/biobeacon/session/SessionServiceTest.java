package com.nimshub.biobeacon.session;

import com.nimshub.biobeacon.athlete.Athlete;
import com.nimshub.biobeacon.athlete.AthleteRepository;
import com.nimshub.biobeacon.device.Device;
import com.nimshub.biobeacon.device.DeviceRepository;
import com.nimshub.biobeacon.session.dto.SessionResponse;
import com.nimshub.biobeacon.user.Gender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

class SessionServiceTest {
    private static final UUID athleteId = UUID.randomUUID();
    private static Session session1;
    private static Session session2;
    private static Athlete athlete1;
    private static Device device;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private AthleteRepository athleteRepository;
    @InjectMocks
    private SessionService sessionService;

    @BeforeAll
    static void setUpData() {


        session1 = Session.builder()
                .sessionId(UUID.randomUUID())
                .athleteId(athleteId)
                .deviceId(1L)
                .startDateTime(null)
                .endDateTime(null)
                .sessionDuration(1L)
                .createDateTime(new Date())
                .build();

        session2 = Session.builder()
                .sessionId(UUID.randomUUID())
                .athleteId(athleteId)
                .deviceId(2L)
                .startDateTime(null)
                .endDateTime(null)
                .sessionDuration(1L)
                .createDateTime(new Date())
                .build();

        athlete1 = Athlete.builder()
                .athleteId(UUID.randomUUID())
                .coachId(UUID.randomUUID())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .weight(80.0)
                .height(180.0)
                .mobile("1234567890")
                .address("123 Main St")
                .build();

        device = Device.builder()
                .id(1L)
                .apiKey(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSessionsByAthleteId() {
        // Given
        List<Session> sessions = Arrays.asList(session1, session2);
        List<SessionResponse> sessionResponses = sessions.stream().map(
                session -> SessionResponse.builder()
                        .sessionId(session.getSessionId())
                        .deviceId(session.getDeviceId())
                        .isComplete(session.isComplete())
                        .startDateTime(session.getStartDateTime())
                        .endDateTime(session.getEndDateTime())
                        .sessionDuration(session.getSessionDuration())
                        .build()
        ).toList();

        when(sessionRepository.findAllByAthleteId(athleteId)).thenReturn(Optional.of(sessions));

        // When
        List<SessionResponse> sessionResponseList = sessionService.getSessionsByAthleteId(athleteId);

        // Then
        assertThat(sessionResponseList).hasSize(2);
        assertThat(sessionResponseList).usingRecursiveComparison().isEqualTo(sessionResponses);
    }

    @Test
    void createSession() {
        // When
        when(deviceRepository.findById(1L)).thenReturn(Optional.ofNullable(device));
        when(athleteRepository.findByEmail("")).thenReturn(Optional.ofNullable(athlete1));

        // Then

    }

    @Test
    void updateSession() {
    }

    @Test
    void getDateDifferenceInSeconds() {
    }
}