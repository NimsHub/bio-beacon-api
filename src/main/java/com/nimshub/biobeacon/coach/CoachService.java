package com.nimshub.biobeacon.coach;

import com.nimshub.biobeacon.auth.AuthService;
import com.nimshub.biobeacon.auth.AuthenticationResponse;
import com.nimshub.biobeacon.auth.RegisterRequest;
import com.nimshub.biobeacon.coach.dto.CoachDetailsResponse;
import com.nimshub.biobeacon.coach.dto.CoachResponse;
import com.nimshub.biobeacon.coach.dto.CreateCoachRequest;
import com.nimshub.biobeacon.config.JwtService;
import com.nimshub.biobeacon.user.Role;
import com.nimshub.biobeacon.user.User;
import com.nimshub.biobeacon.user.UserRepository;
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
    private final UserRepository userRepository;
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
                .coachId(UUID.randomUUID())
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
    public List<CoachResponse> getCoaches() {

        List<Coach> coaches = coachRepository.findAll();

        return coaches.stream()
                .map(coach -> CoachResponse.builder()
                        .id(coach.getCoachId())
                        .firstname(coach.getFirstname())
                        .lastname(coach.getLastname())
                        .email(coach.getEmail())
                        .build()).toList();
    }

    /**
     * @param authHeader : String
     * @return : CoachDetailsResponse
     */
    public CoachDetailsResponse getCoach(String authHeader) {

        String email = jwtService.extractUserName(authHeader.substring(7));

        Coach coach = coachRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with email : [%s] not found".formatted(email)));

        return CoachDetailsResponse.builder()
                .id(coach.getCoachId())
                .firstname(coach.getFirstname())
                .lastname(coach.getLastname())
                .email(coach.getEmail())
                .gender(coach.getGender())
                .dateOfBirth(coach.getDateOfBirth())
                .mobile(coach.getMobile())
                .address(coach.getAddress())
                .build();
    }

    /**
     * This method updates the coach
     * @param request : CreateCoachRequest
     * @param authHeader : String
     * @return : AuthenticationResponse
     */
    public AuthenticationResponse updateCoach(CreateCoachRequest request, String authHeader) {

        String email = jwtService.extractUserName(authHeader.substring(7));
        Coach coach = coachRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with email : [%s] not found"
                                .formatted(email)));

        User user = userRepository.findUserByUserId(coach.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User with id : [%s] Not Found"
                        .formatted(coach.getUserId())));

        coach.setFirstname(request.getFirstname());
        coach.setLastname(request.getLastname());
        coach.setEmail(request.getEmail());
        coach.setMobile(request.getMobile());
        coach.setAddress(request.getAddress());

        coachRepository.save(coach);

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return authService.update(user);
    }
}
