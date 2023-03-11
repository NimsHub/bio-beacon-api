package com.nimshub.biobeacon.coach;

import com.nimshub.biobeacon.auth.AuthenticationResponse;
import com.nimshub.biobeacon.coach.dto.CoachDetailsResponse;
import com.nimshub.biobeacon.coach.dto.CreateCoachRequest;
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

class CoachControllerTest {
    private static CoachDetailsResponse coachDetailsResponse;
    @Mock
    private CoachService coachService;
    @InjectMocks
    private CoachController coachController;

    @BeforeAll
    static void setUpData() {
        coachDetailsResponse = CoachDetailsResponse.builder()
                .id(UUID.randomUUID())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .mobile("1234567890")
                .address("123.Main St")
                .build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCoach() {
        // given
        CreateCoachRequest createCoachRequest = CreateCoachRequest.builder()
                .email("test@example.com")
                .password("password")
                .firstname("John")
                .lastname("Doe")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .mobile("1234567890")
                .address("123 Main St")
                .build();

        AuthenticationResponse authenticationResponse = new AuthenticationResponse("token");

        when(coachService.createCoach(createCoachRequest)).thenReturn(authenticationResponse);

        // when
        ResponseEntity<AuthenticationResponse> response = coachController.createCoach(createCoachRequest);

        // then
        verify(coachService).createCoach(createCoachRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(authenticationResponse);
    }

    @Test
    void testGetCoaches() {
        // given
        when(coachService.getCoaches()).thenReturn(Collections.singletonList(coachDetailsResponse));

        // when
        ResponseEntity<List<CoachDetailsResponse>> response = coachController.getCoaches();

        // then
        verify(coachService).getCoaches();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Collections.singletonList(coachDetailsResponse));
    }

    @Test
    void testGetCoach() {
        // given

        when(coachService.getCoach(any())).thenReturn(coachDetailsResponse);

        // when
        ResponseEntity<CoachDetailsResponse> response = coachController.getCoach("Bearer token");

        // then
        verify(coachService).getCoach("Bearer token");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(coachDetailsResponse);
    }
}