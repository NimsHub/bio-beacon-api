package com.nimshub.biobeacon.session;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * This class defines Session Entity and its properties
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Session {
    @Id
    @SequenceGenerator(name = "SESSION_SEQ", sequenceName = "SESSION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SESSION_SEQ")
    private Integer id;
    @NotNull
    private UUID sessionId;
    @NotNull(message = "User ID cannot be null for a session")
    private UUID athleteId;
    @NotNull(message = "Device ID cannot be null for a session")
    private Long deviceId;
    @Column(columnDefinition = "boolean default false")
    private boolean isComplete;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long sessionDuration;
    @CreationTimestamp
    private Date createDateTime;
}
