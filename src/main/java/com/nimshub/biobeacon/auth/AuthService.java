package com.nimshub.biobeacon.auth;

import com.nimshub.biobeacon.config.JwtService;
import com.nimshub.biobeacon.exceptions.UserAlreadyExistsException;
import com.nimshub.biobeacon.user.Role;
import com.nimshub.biobeacon.user.User;
import com.nimshub.biobeacon.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    Logger logger = LoggerFactory.getLogger(AuthService.class);

    /**
     * This method registers users in the system
     *
     * @param request : RegisterRequest
     * @return RegistrationResponse
     */
    public RegistrationResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email : [%s]  already exists".formatted(request.getEmail()));
        }

        var user = User.builder()
                .userId(UUID.randomUUID())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User registeredUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        logger.info("User registration success");
        return RegistrationResponse.builder()
                .id(registeredUser.getUserId())
                .token(jwtToken)
                .build();
    }

    /**
     * This method authenticates users
     *
     * @param request : AuthenticationRequest
     * @return AuthenticationResponse
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with E-mail : [%s]  does not exist".formatted(request.getEmail())));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Password is incorrect for user : [%s]"
                    .formatted(request.getEmail()));
        }
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role",user.getRole());

        var jwtToken = jwtService.generateToken(extraClaims,user);
        logger.info("User authentication success");

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     *  This method updates user
     * @param user : User
     * @return AuthenticationResponse
     */
    public AuthenticationResponse update(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
