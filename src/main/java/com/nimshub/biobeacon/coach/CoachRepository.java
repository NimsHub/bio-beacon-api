package com.nimshub.biobeacon.coach;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {
    Optional<Coach> findByEmail(String email);

    Optional<Coach> findByUserId(UUID id);

    Optional<Coach> findByCoachId(UUID id);
}
