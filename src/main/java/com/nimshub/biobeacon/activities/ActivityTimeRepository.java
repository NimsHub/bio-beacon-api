package com.nimshub.biobeacon.activities;

import com.nimshub.biobeacon.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author Nirmala : 11:October:2023
 * This interface implements all methods required database transactions for ActivityTime using Jpa
 */

@Repository
public interface ActivityTimeRepository extends JpaRepository<ActivityTime, Integer> {
    Optional<ActivityTime> findBySession(Session session);
}
