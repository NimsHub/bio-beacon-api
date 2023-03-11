package com.nimshub.biobeacon.athlete;

import com.nimshub.biobeacon.user.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Athlete {
    @Id
    @SequenceGenerator(name = "ATHLETE_SEQ", sequenceName = "ATHLETE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATHLETE_SEQ")
    private Integer id;
    private UUID athleteId;
    private UUID userId;
    private UUID coachId;
    private String firstname;
    private String lastname;
    @Email
    @NotBlank
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Past
    private LocalDate dateOfBirth;
    private Double height;
    private Double weight;
    private String mobile;
    private String address;
    private String occupation;
    @CreationTimestamp
    private LocalDateTime joinedDate;

}
