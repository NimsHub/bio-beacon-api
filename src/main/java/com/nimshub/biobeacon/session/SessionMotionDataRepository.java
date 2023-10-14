package com.nimshub.biobeacon.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionMotionDataRepository extends JpaRepository<SessionMotionData, Integer> {
    Optional<SessionMotionData> findSessionMotionDataBySession(Session session);
}
