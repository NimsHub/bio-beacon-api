package com.nimshub.biobeacon.session;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Integer> {
    Optional<List<Session>>  findAllByUserId(@NotNull(message = "User ID cannot be null for a session") Integer userId);
}
