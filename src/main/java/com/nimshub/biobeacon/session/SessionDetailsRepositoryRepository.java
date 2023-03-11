package com.nimshub.biobeacon.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * BioBeacon-Api
 * This Repository Interface contains all the functionality related to SessionDetailsRepository
 */
@Repository
public interface SessionDetailsRepositoryRepository extends JpaRepository<SessionDetails, Integer> {
    Optional<SessionDetails> findSessionDetailsBySession(Session session);
}


