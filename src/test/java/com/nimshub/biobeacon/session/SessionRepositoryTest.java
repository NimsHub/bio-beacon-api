//package com.nimshub.biobeacon.session;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//@DataJpaTest
//@Transactional
//class SessionRepositoryTest {
//    @Autowired
//    private SessionRepository underTest;
//
//    @Test
//    @Disabled
//    void testFindAllByUserId() {
//        //given
//        UUID id = UUID.randomUUID();
//        Session expected = Session.builder()
//                .id(id)
//                .athleteId(id)
//                .deviceId(1L)
//                .heartRate("heartRate")
//                .bloodPressure("bloodPressure")
//                .respirationRate("respirationRate")
//                .startDateTime(null)
//                .endDateTime(null)
//                .sessionDuration(1L)
//                .createDateTime(new Date())
//                .build();
//
//        underTest.save(expected);
//
//        //when
//        List<Session> result = underTest.findAllByAthleteId(id).orElseThrow();
//        //then
//        assertThat(result.toString()).isEqualTo(List.of(expected).toString());
//    }
//    @Test
//    @Disabled
//    void testUpdateSession() {
//        //given
//        UUID id = UUID.randomUUID();
//        Session createdSession = Session.builder()
//                .athleteId(id)
//                .deviceId(1L)
//                .build();
//
//        Session savedSession = underTest.save(createdSession);
//
//        LocalDateTime startDateTime = LocalDateTime.now();
//        LocalDateTime endDateTime = LocalDateTime.now();
//
//        Session expected = Session.builder()
//                .id(savedSession.getId())
//                .athleteId(id)
//                .deviceId(1L)
//                .heartRate("test")
//                .bloodPressure("test")
//                .respirationRate("test")
//                .startDateTime(startDateTime)
//                .endDateTime(endDateTime)
//                .sessionDuration(1L)
//                .isComplete(true)
//                .createDateTime(new Date())
//                .build();
//        // when
//        underTest.updateSession(1L,"test","test","test", startDateTime,endDateTime,1L);
//        // then
//        assertThat(underTest.findById(savedSession.getKey())).isEqualTo(expected);
//    }
//}