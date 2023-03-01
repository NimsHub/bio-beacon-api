package com.nimshub.biobeacon.session;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
class SessionRepositoryTest {
    @Autowired
    private SessionRepository underTest;

    @Test
    void testFindAllByUserId() {
        //given
        Session expected = Session.builder()
                .userId(2)
                .heartRate("heartRate")
                .bloodPressure("bloodPressure")
                .respirationRate("respirationRate")
                .startDateTime(null)
                .endDateTime(null)
                .sessionDuration(1L)
                .createDateTime(new Date())
                .build();
          
        underTest.save(expected);

        //when
        List<Session> result = underTest.findAllByUserId(2).orElseThrow();
        //then
        assertThat(result.toString()).isEqualTo(List.of(expected).toString());
    }
}