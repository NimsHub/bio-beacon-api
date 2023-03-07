package com.nimshub.biobeacon.device;

import jakarta.persistence.*;
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
    @SequenceGenerator(name = "DEVICE_SEQ", sequenceName = "DEVICE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEVICE_SEQ")
    private Long id;
    private String apiKey;
    @CreationTimestamp
    private LocalDateTime createdAt;

}
