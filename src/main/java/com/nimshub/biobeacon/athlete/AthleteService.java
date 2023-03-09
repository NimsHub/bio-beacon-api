package com.nimshub.biobeacon.athlete;

import com.nimshub.biobeacon.athlete.dto.AthleteDetailsResponse;
import com.nimshub.biobeacon.athlete.dto.CreateAthleteRequest;
import com.nimshub.biobeacon.auth.AuthService;
import com.nimshub.biobeacon.auth.AuthenticationResponse;
import com.nimshub.biobeacon.auth.RegisterRequest;
import com.nimshub.biobeacon.config.JwtService;
import com.nimshub.biobeacon.exceptions.AthleteNotFoundException;
import com.nimshub.biobeacon.user.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AthleteService {
    private final AthleteRepository athleteRepository;
    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * This method creates new Athlete
     *
     * @param request : CreateAthleteRequest
     * @return : AuthenticationResponse
     */
    @Transactional
    public AuthenticationResponse createAthlete(CreateAthleteRequest request) {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.ATHLETE)
                .build();

        var response = authService.register(registerRequest);

        Athlete athlete = Athlete.builder()
                .id(UUID.randomUUID())
                .userId(response.getId())
                .coachId(request.getCoachId())
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .gender(request.getGender())
                .height(request.getHeight())
                .weight(request.getWeight())
                .dateOfBirth(request.getDateOfBirth())
                .occupation(request.getOccupation())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .build();
        athleteRepository.save(athlete);

        return AuthenticationResponse.builder()
                .token(response.getToken())
                .build();
    }

    /**
     * This method retrieves all the Athletes
     *
     * @return : List<AthleteDetailsResponse>
     */
    public List<AthleteDetailsResponse> getAthletes() {

        List<Athlete> athletes = athleteRepository.findAll();

        return athletes.stream()
                .map(athlete -> AthleteDetailsResponse.builder()
                        .id(athlete.getId())
                        .coachId(athlete.getCoachId())
                        .firstname(athlete.getFirstname())
                        .lastname(athlete.getLastname())
                        .email(athlete.getEmail())
                        .gender(athlete.getGender())
                        .dateOfBirth(athlete.getDateOfBirth())
                        .weight(athlete.getWeight())
                        .height(athlete.getHeight())
                        .mobile(athlete.getMobile())
                        .address(athlete.getAddress())
                        .build()).toList();
    }

    /**
     * This method retrieves athlete details from the token
     *
     * @param authHeader : String
     * @return : AthleteDetailsResponse
     */
    public AthleteDetailsResponse getAthlete(String authHeader) {

        String token = authHeader.substring(7);
        Athlete athlete = athleteRepository.findByEmail(jwtService.extractUserName(token))
                .orElseThrow(() -> new UsernameNotFoundException("User name not found"));

        return AthleteDetailsResponse.builder()
                .id(athlete.getId())
                .coachId(athlete.getCoachId())
                .firstname(athlete.getFirstname())
                .lastname(athlete.getLastname())
                .email(athlete.getEmail())
                .gender(athlete.getGender())
                .height(athlete.getHeight())
                .weight(athlete.getWeight())
                .dateOfBirth(athlete.getDateOfBirth())
                .mobile(athlete.getMobile())
                .address(athlete.getAddress())
                .build();
    }

    /**
     * This method retrieves athletes of specific Coach
     *
     * @param id : UUID
     * @return : List<AthleteDetailsResponse>
     */
    public List<AthleteDetailsResponse> getAthletesByCoachId(UUID id) {

        List<Athlete> athletes = athleteRepository.findByCoachId(id)
                .orElseThrow(() -> new AthleteNotFoundException("Athletes not found for Coach : " + id));

        return athletes.stream()
                .map(athlete -> AthleteDetailsResponse.builder()
                        .id(athlete.getId())
                        .firstname(athlete.getFirstname())
                        .lastname(athlete.getLastname())
                        .email(athlete.getEmail())
                        .coachId(athlete.getCoachId())
                        .gender(athlete.getGender())
                        .weight(athlete.getWeight())
                        .height(athlete.getHeight())
                        .occupation(athlete.getOccupation())
                        .dateOfBirth(athlete.getDateOfBirth())
                        .mobile(athlete.getMobile())
                        .address(athlete.getAddress())
                        .build()).toList();
    }
}
