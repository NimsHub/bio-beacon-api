package com.nimshub.biobeacon.athlete;

import com.nimshub.biobeacon.athlete.dto.AthleteDetailsResponse;
import com.nimshub.biobeacon.athlete.dto.AthleteResponse;
import com.nimshub.biobeacon.athlete.dto.CreateAthleteRequest;
import com.nimshub.biobeacon.auth.AuthenticationResponse;
import com.nimshub.biobeacon.user.Gender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AthleteControllerTest {
    private static AthleteDetailsResponse athleteDetailsResponse;
    private static AthleteResponse athleteResponse;
    @Mock
    private AthleteService athleteService;
    @InjectMocks
    private AthleteController athleteController;

    @BeforeAll
    static void setUpData() {
        athleteDetailsResponse = AthleteDetailsResponse.builder()
                .athleteId(UUID.randomUUID())
                .coachId(UUID.randomUUID())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .height(180.0)
                .weight(80.0)
                .mobile("1234567890")
                .address("123.Main St")
                .occupation(null)
                .build();

        athleteResponse = AthleteResponse.builder()
                .athleteId(UUID.randomUUID())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .gender(Gender.MALE)
                .occupation(null)
                .build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testCreateAthlete() {
        // given
        CreateAthleteRequest createAthleteRequest = CreateAthleteRequest.builder()
                .email("test@example.com")
                .password("password")
                .firstname("John")
                .lastname("Doe")
                .gender(Gender.MALE)
                .height(180.0)
                .weight(70.0)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .occupation("programmer")
                .mobile("1234567890")
                .address("123 Main St")
                .coachId(UUID.randomUUID())
                .build();

        AuthenticationResponse authenticationResponse = new AuthenticationResponse("token");

        when(athleteService.createAthlete(any())).thenReturn(authenticationResponse);

        // when
        ResponseEntity<AuthenticationResponse> response = athleteController.createAthlete(createAthleteRequest);

        // then
        verify(athleteService).createAthlete(createAthleteRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(authenticationResponse);
    }

    @Test
    void testGetAthletes() {
        // given
        when(athleteService.getAthletes()).thenReturn(Collections.singletonList(athleteResponse));

        // when
        ResponseEntity<List<AthleteResponse>> response = athleteController.getAthletes();

        // then
        verify(athleteService).getAthletes();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Collections.singletonList(athleteResponse));
    }

    @Test
    void testGetAthleteByToken() {
        // given

        when(athleteService.getAthleteByToken(any())).thenReturn(athleteDetailsResponse);

        // when
        ResponseEntity<AthleteDetailsResponse> response = athleteController.getAthleteByToken("Bearer token");

        // then
        verify(athleteService).getAthleteByToken("Bearer token");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(athleteDetailsResponse);
    }

    @Test
    void testGetAthletesByCoachId() {
        // given

        when(athleteService.getAthletesByCoach(any())).thenReturn(Collections.singletonList(athleteResponse));

        // when
        ResponseEntity<List<AthleteResponse>> response = athleteController.getAthletesByCoachId("token");

        // then
        verify(athleteService).getAthletesByCoach(any());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Collections.singletonList(athleteResponse));
    }
}
