package com.nimshub.biobeacon.coach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class CoachRepositoryTest {

    @Mock
    private CoachRepository coachRepository;

    @Test
    void testFindByEmail() {
        // given
        String email = "john.doe@example.com";
        UUID id = UUID.randomUUID();
        Coach coach = Coach.builder()
                .id(id)
                .userId(UUID.randomUUID())
                .email(email)
                .build();
        when(coachRepository.findByEmail(email)).thenReturn(Optional.of(coach));

        // when
        Optional<Coach> result = coachRepository.findByEmail(email);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getEmail()).isEqualTo(email);
    }

    @Test
    void testFindByUserId() {
        // given
        UUID id = UUID.randomUUID();
        Coach coach = Coach.builder()
                .id(UUID.randomUUID())
                .userId(id)
                .email("john.doe@example.com")
                .build();
        when(coachRepository.findByUserId(id)).thenReturn(Optional.of(coach));

        // when
        Optional<Coach> result = coachRepository.findByUserId(id);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(coach.getId());
        assertThat(result.get().getUserId()).isEqualTo(id);
    }

}
