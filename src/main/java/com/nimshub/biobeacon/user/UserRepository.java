package com.nimshub.biobeacon.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findUserByUserId(UUID userId);

    boolean existsByEmail(String email);
}
