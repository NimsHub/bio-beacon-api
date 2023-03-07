package com.nimshub.biobeacon.coach;

import com.nimshub.biobeacon.auth.AuthService;
import com.nimshub.biobeacon.auth.AuthenticationResponse;
import com.nimshub.biobeacon.auth.RegisterRequest;
import com.nimshub.biobeacon.coach.dto.CoachDetailsResponse;
import com.nimshub.biobeacon.coach.dto.CreateCoachRequest;
import com.nimshub.biobeacon.config.JwtService;
import com.nimshub.biobeacon.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * @param request : CreateCoachRequest
     * @return Coach
     */
    @Transactional
    public AuthenticationResponse createCoach(CreateCoachRequest request) {

        RegisterRequest registerRequest = RegisterRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.COACH)
                .build();

        var response = authService.register(registerRequest);

        Coach coach = Coach.builder()
                .id(UUID.randomUUID())
                .userId(response.getId())
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .build();
        coachRepository.save(coach);

        return AuthenticationResponse.builder()
                .token(response.getToken())
                .build();
    }

    /**
     * @return : List<CoachDetailsResponse>
     */
    public List<CoachDetailsResponse> getCoaches() {

        List<Coach> coaches = coachRepository.findAll();

        return coaches.stream()
                .map(coach -> CoachDetailsResponse.builder()
                        .id(coach.getId())
                        .firstname(coach.getFirstname())
                        .lastname(coach.getLastname())
                        .email(coach.getEmail())
                        .gender(coach.getGender())
                        .dateOfBirth(coach.getDateOfBirth())
                        .mobile(coach.getMobile())
                        .address(coach.getAddress())
                        .build()).toList();
    }

    /**
     * @param authHeader : String
     * @return : CoachDetailsResponse
     */
    public CoachDetailsResponse getCoach(String authHeader) {

        String token = authHeader.substring(7);
        Coach coach = coachRepository.findByEmail(jwtService.extractUserName(token))
                .orElseThrow(() -> new UsernameNotFoundException("User name not found"));

        return CoachDetailsResponse.builder()
                .id(coach.getId())
                .firstname(coach.getFirstname())
                .lastname(coach.getLastname())
                .email(coach.getEmail())
                .gender(coach.getGender())
                .dateOfBirth(coach.getDateOfBirth())
                .mobile(coach.getMobile())
                .address(coach.getAddress())
                .build();
    }
}
