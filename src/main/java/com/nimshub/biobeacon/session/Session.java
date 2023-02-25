package com.nimshub.biobeacon.session;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Session {
    @Id
    private Integer sessionId;
    private Integer userId;
    private String heartRate;
    private String bloodPressure;
    private String respirationRate;
    private Date startDateTime;
    private Date endDateTime;
    private Date sessionDuration;
    @CreationTimestamp
    private Date createDateTime;
}
