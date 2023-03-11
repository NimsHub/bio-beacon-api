package com.nimshub.biobeacon.athlete;

import com.nimshub.biobeacon.athlete.dto.AthleteDetailsResponse;
import com.nimshub.biobeacon.athlete.dto.AthleteResponse;
import com.nimshub.biobeacon.athlete.dto.CreateAthleteRequest;
import com.nimshub.biobeacon.auth.AuthService;
import com.nimshub.biobeacon.auth.AuthenticationResponse;
import com.nimshub.biobeacon.auth.RegisterRequest;
import com.nimshub.biobeacon.coach.CoachRepository;
import com.nimshub.biobeacon.config.JwtService;
import com.nimshub.biobeacon.exceptions.AthleteNotFoundException;
import com.nimshub.biobeacon.exceptions.CoachNotFoundException;
import com.nimshub.biobeacon.user.Role;
import com.nimshub.biobeacon.user.User;
import com.nimshub.biobeacon.user.UserRepository;
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
    private final CoachRepository coachRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
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
                .athleteId(UUID.randomUUID())
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
    public List<AthleteResponse> getAthletes() {

        List<Athlete> athletes = athleteRepository.findAll();

        return athletes.stream()
                .map(athlete -> AthleteResponse.builder()
                        .athleteId(athlete.getAthleteId())
                        .firstname(athlete.getFirstname())
                        .lastname(athlete.getLastname())
                        .email(athlete.getEmail())
                        .gender(athlete.getGender())
                        .build()).toList();
    }

    /**
     * This method retrieves athlete details from the token
     *
     * @param authHeader : String
     * @return : AthleteDetailsResponse
     */
    public AthleteDetailsResponse getAthleteByToken(String authHeader) {

        String email = jwtService.extractUserName(authHeader.substring(7));

        Athlete athlete = athleteRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with email : [%s] not found".formatted(email)));

        return AthleteDetailsResponse.builder()
                .athleteId(athlete.getAthleteId())
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
    public List<AthleteResponse> getAthletesByCoachId(UUID id) {

        coachRepository.findByCoachId(id)
                .orElseThrow(() -> new CoachNotFoundException("Coach with id : [%s] not found".formatted(id)));

        List<Athlete> athletes = athleteRepository.findByCoachId(id)
                .orElseThrow(() -> new AthleteNotFoundException("Athletes not found for Coach : [%s]".formatted(id)));

        return athletes.stream()
                .map(athlete -> AthleteResponse.builder()
                        .athleteId(athlete.getAthleteId())
                        .firstname(athlete.getFirstname())
                        .lastname(athlete.getLastname())
                        .email(athlete.getEmail())
                        .gender(athlete.getGender())
                        .occupation(athlete.getOccupation())
                        .build()).toList();
    }

    /**
     * This method get athlete details by athlete ID
     * @param athleteId : UUID
     * @return : AthleteDetailsResponse
     */
    public AthleteDetailsResponse getAthleteDetailsByAthleteId(UUID athleteId) {

        Athlete athlete = athleteRepository.findByAthleteId(athleteId)
                .orElseThrow(() -> new AthleteNotFoundException("Athlete with id : [%s] not found".formatted(athleteId)));

        return AthleteDetailsResponse.builder()
                .athleteId(athlete.getAthleteId())
                .coachId(athlete.getCoachId())
                .firstname(athlete.getFirstname())
                .lastname(athlete.getLastname())
                .email(athlete.getEmail())
                .gender(athlete.getGender())
                .height(athlete.getHeight())
                .weight(athlete.getWeight())
                .mobile(athlete.getMobile())
                .occupation(athlete.getOccupation())
                .build();
    }

    /**
     * This method updates the athlete
     * @param request : CreateAthleteRequest
     * @param authHeader : String
     * @return : AuthenticationResponse
     */
    public AuthenticationResponse updateAthlete(CreateAthleteRequest request, String authHeader) {

        String email = jwtService.extractUserName(authHeader.substring(7));
        Athlete athlete = athleteRepository.findByEmail(email)
                .orElseThrow(() -> new AthleteNotFoundException("Athlete with id : [%s] not found"
                        .formatted(email)));
        User user = userRepository.findUserByUserId(athlete.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User with id : [%s] Not Found"
                        .formatted(athlete.getUserId())));

        athlete.setFirstname(request.getFirstname());
        athlete.setLastname(request.getLastname());
        athlete.setEmail(request.getEmail());
        athlete.setHeight(request.getHeight());
        athlete.setWeight(request.getWeight());
        athlete.setMobile(request.getMobile());
        athlete.setAddress(request.getAddress());
        athlete.setOccupation(request.getOccupation());

        athleteRepository.save(athlete);

        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        return authService.update(user);
    }
}
