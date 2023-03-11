package com.nimshub.biobeacon.session;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
class SessionRepositoryTest {
    @Autowired
    private SessionRepository underTest;

    @Test
//    @Disabled
    void testFindAllByUserId() {
        //given
        UUID id = UUID.randomUUID();
        Session expected = Session.builder()
                .sessionId(id)
                .athleteId(id)
                .deviceId(1L)
                .startDateTime(null)
                .endDateTime(null)
                .sessionDuration(1L)
                .createDateTime(new Date())
                .build();

        underTest.save(expected);

        //when
        List<Session> result = underTest.findAllByAthleteId(id).orElseThrow();
        //then
        assertThat(result.toString()).hasToString(List.of(expected).toString());
    }

    @Test
    @Disabled("does not update the database")
    void testUpdateSession() {
        //given
        UUID athleteId = UUID.randomUUID();
        UUID sessionId = UUID.randomUUID();
        Session createdSession = Session.builder()
                .athleteId(athleteId)
                .sessionId(sessionId)
                .deviceId(1L)
                .build();

        Session savedSession = underTest.save(createdSession);

        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.now();

        Session expected = Session.builder()
                .sessionId(savedSession.getSessionId())
                .athleteId(athleteId)
                .deviceId(1L)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .sessionDuration(1L)
                .isComplete(true)
                .createDateTime(new Date())
                .build();
        // when
        underTest.updateSession(1L, "test", "test", "test", startDateTime, endDateTime, 1L);
        // then
        assertTrue(underTest.findById(savedSession.getId()).isPresent());
        assertThat(underTest.findById(savedSession.getId()).get()).isEqualTo(List.of(expected));
    }
}