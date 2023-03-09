package com.nimshub.biobeacon.athlete;

import com.nimshub.biobeacon.athlete.dto.AthleteDetailsResponse;
import com.nimshub.biobeacon.athlete.dto.CreateAthleteRequest;
import com.nimshub.biobeacon.auth.AuthService;
import com.nimshub.biobeacon.auth.AuthenticationResponse;
import com.nimshub.biobeacon.auth.RegisterRequest;
import com.nimshub.biobeacon.auth.RegistrationResponse;
import com.nimshub.biobeacon.config.JwtService;
import com.nimshub.biobeacon.exceptions.AthleteNotFoundException;
import com.nimshub.biobeacon.user.Gender;
import com.nimshub.biobeacon.user.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AthleteServiceTest {

    @Mock
    private AthleteRepository athleteRepository;

    @Mock
    private AuthService authService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AthleteService athleteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    private static Athlete athlete1;
    private static Athlete athlete2;

    @BeforeAll()
    static void setUpData() {
        athlete1 = Athlete.builder()
                .id(UUID.randomUUID())
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

        athlete2 = Athlete.builder()
                .id(UUID.randomUUID())
                .coachId(UUID.randomUUID())
                .firstname("Jane")
                .lastname("Doe")
                .email("jane.doe@example.com")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(1995, 2, 2))
                .weight(60.0)
                .height(160.0)
                .mobile("9876543210")
                .address("456 High St")
                .build();
    }

    @Test
    @DisplayName("Test createAthlete method")
    void testCreateAthlete() {
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

        RegisterRequest registerRequest = RegisterRequest.builder()
                .email(createAthleteRequest.getEmail())
                .password(createAthleteRequest.getPassword())
                .role(Role.ATHLETE)
                .build();

        RegistrationResponse authenticationResponse = RegistrationResponse.builder()
                .id(UUID.randomUUID())
                .token("token")
                .build();

        when(authService.register(registerRequest)).thenReturn(authenticationResponse);


        when(athleteRepository.save(athlete1)).thenReturn(athlete1);

        AuthenticationResponse response = athleteService.createAthlete(createAthleteRequest);

        assertNotNull(response.getToken());
        assertEquals(authenticationResponse.getToken(), response.getToken());

        verify(authService).register(registerRequest);
//        verify(athleteRepository).save(athlete1);
    }

    @Test
    void testGetAthletes() {

        List<Athlete> athletes = Arrays.asList(athlete1, athlete2);
        when(athleteRepository.findAll()).thenReturn(athletes);

        List<AthleteDetailsResponse> athleteDetails = athleteService.getAthletes();

        assertEquals(2, athleteDetails.size());
        assertEquals(athlete1.getId(), athleteDetails.get(0).getId());
        assertEquals(athlete1.getCoachId(), athleteDetails.get(0).getCoachId());
        assertEquals(athlete1.getFirstname(), athleteDetails.get(0).getFirstname());
        assertEquals(athlete1.getLastname(), athleteDetails.get(0).getLastname());
        assertEquals(athlete1.getEmail(), athleteDetails.get(0).getEmail());
        assertEquals(athlete1.getGender(), athleteDetails.get(0).getGender());
        assertEquals(athlete1.getDateOfBirth(), athleteDetails.get(0).getDateOfBirth());
        assertEquals(athlete1.getWeight(), athleteDetails.get(0).getWeight());
        assertEquals(athlete1.getHeight(), athleteDetails.get(0).getHeight());
        assertEquals(athlete1.getMobile(), athleteDetails.get(0).getMobile());
        assertEquals(athlete1.getAddress(), athleteDetails.get(0).getAddress());

    }

    @Test
    void testGetAthlete() {

        String email = "johndoe@example.com";
        String token = "token";

        when(jwtService.extractUserName(token)).thenReturn(email);

        Athlete athlete = new Athlete();
        athlete.setId(UUID.randomUUID());
        athlete.setCoachId(UUID.randomUUID());
        athlete.setFirstname("John");
        athlete.setLastname("Doe");
        athlete.setEmail("john.doe@example.com");
        athlete.setGender(Gender.MALE);
        athlete.setDateOfBirth(LocalDate.of(1990, 1, 1));
        athlete.setWeight(80.0);
        athlete.setHeight(180.0);
        athlete.setMobile("1234567890");
        athlete.setAddress("123 Main St");

        when(athleteRepository.findByEmail(email)).thenReturn(Optional.of(athlete));

        AthleteDetailsResponse response = athleteService.getAthlete("Bearer " + token);
        System.out.println(response.toString());
        assertEquals(athlete.getId(), response.getId());
        assertEquals(athlete.getCoachId(), response.getCoachId());
        assertEquals(athlete.getFirstname(), response.getFirstname());
        assertEquals(athlete.getLastname(), response.getLastname());
        assertEquals(athlete.getEmail(), response.getEmail());
        assertEquals(athlete.getGender(), response.getGender());
        assertEquals(athlete.getDateOfBirth(), response.getDateOfBirth());
        assertEquals(athlete.getWeight(), response.getWeight());
        assertEquals(athlete.getHeight(), response.getHeight());
        assertEquals(athlete.getMobile(), response.getMobile());
        assertEquals(athlete.getAddress(), response.getAddress());
    }

    @Test
    void testGetAthletesByCoachId() {
        // Given

        UUID coachId = UUID.randomUUID();

        List<Athlete> athletes = Arrays.asList(athlete1, athlete2);
        when(athleteRepository.findByCoachId(coachId)).thenReturn(Optional.of(athletes));

        // When
        List<AthleteDetailsResponse> athleteDetailsResponses = athleteService.getAthletesByCoachId(coachId);

        // Then
        List<AthleteDetailsResponse> expectedAthleteDetailsResponses = new ArrayList<>();
        expectedAthleteDetailsResponses.add(
                AthleteDetailsResponse.builder()
                .id(athletes.get(0).getId())
                .coachId(athletes.get(0).getCoachId())
                .firstname(athletes.get(0).getFirstname())
                .lastname(athletes.get(0).getLastname())
                .email(athletes.get(0).getEmail())
                .gender(athletes.get(0).getGender())
                .dateOfBirth(athletes.get(0).getDateOfBirth())
                .weight(athletes.get(0).getWeight())
                .height(athletes.get(0).getHeight())
                .mobile(athletes.get(0).getMobile())
                .address(athletes.get(0).getAddress())
                .build()
        );
        expectedAthleteDetailsResponses.add(
                AthleteDetailsResponse.builder()
                .id(athletes.get(1).getId())
                .coachId(athletes.get(1).getCoachId())
                .firstname(athletes.get(1).getFirstname())
                .lastname(athletes.get(1).getLastname())
                .email(athletes.get(1).getEmail())
                .gender(athletes.get(1).getGender())
                .dateOfBirth(athletes.get(1).getDateOfBirth())
                .weight(athletes.get(1).getWeight())
                .height(athletes.get(1).getHeight())
                .mobile(athletes.get(1).getMobile())
                .address(athletes.get(1).getAddress())
                .build()
        );
        assertThat(athleteDetailsResponses).hasSize(2);
        assertThat(athleteDetailsResponses).usingRecursiveComparison().isEqualTo(expectedAthleteDetailsResponses);
    }

    @Test
    void testGetAthletesByCoachIdThrowsException() {
        // Given
        UUID coachId = UUID.randomUUID();
        when(athleteRepository.findByCoachId(coachId)).thenReturn(Optional.empty());

        // When/Then
        AthleteNotFoundException ex = assertThrows(AthleteNotFoundException.class, () ->
                athleteService.getAthletesByCoachId(coachId)
        );
        assertThat(ex.getMessage()).isEqualTo("Athletes not found for Coach : " + coachId);
    }

}



