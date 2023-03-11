package com.nimshub.biobeacon.athlete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Integer> {
    Optional<Athlete> findByEmail(String email);

    Optional<List<Athlete>> findByCoachId(UUID id);

    Optional<Athlete> findByAthleteId(UUID athleteId);
}
