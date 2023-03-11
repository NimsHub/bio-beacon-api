package com.nimshub.biobeacon.session.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SessionResponse {
    private UUID sessionId;
    private Long deviceId;
    private boolean isComplete;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long sessionDuration;
}
