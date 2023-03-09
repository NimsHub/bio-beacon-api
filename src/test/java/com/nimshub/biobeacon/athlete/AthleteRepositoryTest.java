package com.nimshub.biobeacon.athlete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DataJpaTest
class AthleteRepositoryTest {

    @Mock
    private AthleteRepository mockedAthleteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindByEmail() {
        String email = "test@test.com";
        Athlete athlete = Athlete.builder()
                .id(UUID.randomUUID())
                .email(email)
                .build();

        when(mockedAthleteRepository.findByEmail(email)).thenReturn(Optional.of(athlete));
        Optional<Athlete> result = mockedAthleteRepository.findByEmail(email);
        assertTrue(result.isPresent());
        assertEquals(result.get().getEmail(), athlete.getEmail());
    }

    @Test
    void shouldFindByCoachId() {
        UUID coachId = UUID.randomUUID();
        Athlete athlete1 = Athlete.builder()
                .id(UUID.randomUUID())
                .coachId(coachId)
                .build();

        Athlete athlete2 = Athlete.builder()
                .id(UUID.randomUUID())
                .coachId(coachId)
                .build();

        List<Athlete> athletes = new ArrayList<>();
        athletes.add(athlete1);
        athletes.add(athlete2);

        when(mockedAthleteRepository.findByCoachId(coachId)).thenReturn(Optional.of(athletes));
        Optional<List<Athlete>> result = mockedAthleteRepository.findByCoachId(coachId);

        assertTrue(result.isPresent());
        assertEquals(result.get().size(), athletes.size());
        assertEquals(result.get().get(0).getCoachId(), athletes.get(0).getCoachId());
        assertEquals(result.get().get(1).getCoachId(), athletes.get(1).getCoachId());
    }
}
