package com.nimshub.biobeacon.session;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

// This class defines Session Entity and its properties

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Session {
    @Id
    @GeneratedValue
    private Integer sessionId;
    @NotNull(message = "User ID cannot be null for a session")
    private Integer userId;
    private String heartRate;
    private String bloodPressure;
    private String respirationRate;
    private Date startDateTime;
    private Date endDateTime;
    private Long sessionDuration;
    @CreationTimestamp
    private Date createDateTime;
}
