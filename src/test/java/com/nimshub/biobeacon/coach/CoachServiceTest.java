package com.nimshub.biobeacon.coach;

import com.nimshub.biobeacon.auth.AuthService;
import com.nimshub.biobeacon.auth.RegisterRequest;
import com.nimshub.biobeacon.auth.RegistrationResponse;
import com.nimshub.biobeacon.coach.dto.CoachDetailsResponse;
import com.nimshub.biobeacon.coach.dto.CoachResponse;
import com.nimshub.biobeacon.coach.dto.CreateCoachRequest;
import com.nimshub.biobeacon.config.JwtService;
import com.nimshub.biobeacon.user.Gender;
import com.nimshub.biobeacon.user.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoachServiceTest {

    private static Coach coach;
    @Mock
    private CoachRepository coachRepository;
    @Mock
    private AuthService authService;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private CoachService coachService;

    @BeforeAll()
    static void setUp() {
        coach = Coach.builder()
                .coachId(UUID.randomUUID())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .mobile("1234567890")
                .address("123 Main St")
                .build();
    }

    @Test
    void testCreateCoach() {
        // given
        String email = "coach@example.com";
        String password = "password";
        String firstname = "John";
        String lastname = "Doe";
        Gender gender = Gender.MALE;
        LocalDate dateOfBirth = LocalDate.of(1980, 1, 1);
        String mobile = "1234567890";
        String address = "123 Main St";

        RegisterRequest registerRequest = RegisterRequest.builder()
                .email(email)
                .password(password)
                .role(Role.COACH)
                .build();

        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .id(UUID.randomUUID())
                .token("token")
                .build();

        when(authService.register(registerRequest)).thenReturn(registrationResponse);

//        when(jwtService.extractUserName(any())).thenReturn(email);

        // when
        coachService.createCoach(CreateCoachRequest.builder()
                .email(email)
                .password(password)
                .firstname(firstname)
                .lastname(lastname)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .mobile(mobile)
                .address(address)
                .build());

        // then
        ArgumentCaptor<Coach> coachCaptor = ArgumentCaptor.forClass(Coach.class);
        verify(coachRepository).save(coachCaptor.capture());
        Coach savedCoach = coachCaptor.getValue();

        assertThat(savedCoach.getCoachId()).isNotNull();
        assertThat(savedCoach.getUserId()).isEqualTo(registrationResponse.getId());
        assertThat(savedCoach.getEmail()).isEqualTo(email);
        assertThat(savedCoach.getFirstname()).isEqualTo(firstname);
        assertThat(savedCoach.getLastname()).isEqualTo(lastname);
        assertThat(savedCoach.getGender()).isEqualTo(gender);
        assertThat(savedCoach.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(savedCoach.getMobile()).isEqualTo(mobile);
        assertThat(savedCoach.getAddress()).isEqualTo(address);
    }

    @Test
    void testGetCoaches() {
        // given

        when(coachRepository.findAll()).thenReturn(Collections.singletonList(coach));

        // when
        List<CoachResponse> coaches = coachService.getCoaches();

        // then
        assertThat(coaches).hasSize(1);
        assertThat(coaches.get(0).getId()).isEqualTo(coach.getCoachId());
        assertThat(coaches.get(0).getFirstname()).isEqualTo(coach.getFirstname());
        assertThat(coaches.get(0).getLastname()).isEqualTo(coach.getLastname());
        assertThat(coaches.get(0).getEmail()).isEqualTo(coach.getEmail());
    }

    @Test
    void testGetAthlete() {

        String email = "johndoe@example.com";
        String token = "token";

        when(jwtService.extractUserName(token)).thenReturn(email);

        when(coachRepository.findByEmail(email)).thenReturn(Optional.of(coach));

        CoachDetailsResponse response = coachService.getCoach("Bearer " + token);
        System.out.println(response.toString());
        assertEquals(coach.getCoachId(), response.getId());
        assertEquals(coach.getFirstname(), response.getFirstname());
        assertEquals(coach.getLastname(), response.getLastname());
        assertEquals(coach.getEmail(), response.getEmail());
        assertEquals(coach.getGender(), response.getGender());
        assertEquals(coach.getDateOfBirth(), response.getDateOfBirth());
        assertEquals(coach.getMobile(), response.getMobile());
        assertEquals(coach.getAddress(), response.getAddress());
    }
}
