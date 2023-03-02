package com.nimshub.biobeacon.session;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This interface holds all the database calls related to sessions
 */
public interface SessionRepository extends JpaRepository<Session,Integer> {
    Optional<List<Session>>  findAllByUserId(@NotNull(message = "User ID cannot be null for a session") Integer userId);
    @Modifying
    @Query(nativeQuery = true,value=
            "UPDATE session\n " +
            "SET heart_rate = :heartRate," +
            " blood_pressure = :bloodPressure," +
            " respiration_rate = :respirationRate," +
            " start_date_time = :startDateTime," +
            " end_date_time = :endDateTime," +
            " session_duration = :sessionDuration," +
            " is_complete = true\n" +
            "WHERE device_id = :deviceId AND is_complete = false "+
            "ORDER BY create_date_time DESC LIMIT 1 "
    )
    void updateSession(
            @Param(value = "deviceId") Long deviceId,
            @Param(value = "heartRate") String heartRate,
            @Param(value = "bloodPressure") String bloodPressure,
            @Param(value = "respirationRate") String respirationRate,
            @Param(value = "startDateTime") LocalDateTime startDateTime,
            @Param(value = "endDateTime") LocalDateTime endDateTime,
            @Param(value = "sessionDuration") Long sessionDuration

    );

}
