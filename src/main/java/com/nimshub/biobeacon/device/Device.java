package com.nimshub.biobeacon.device;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Device {
    @Id
    @GeneratedValue
    private Long id;
    private String apiKey;
    @CreationTimestamp
    private LocalDateTime createdAt;

}
