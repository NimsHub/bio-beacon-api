package com.nimshub.biobeacon.coach;

import com.nimshub.biobeacon.user.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(uniqueConstraints = @UniqueConstraint(name = "email_cons", columnNames = "email"))
public class Coach {
    @Id
    @SequenceGenerator(name = "COACH_SEQ", sequenceName = "COACH_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COACH_SEQ")
    private Integer id;
    private UUID coachId;
    @NotNull
    private UUID userId;
    @Email
    @NotBlank
    private String email;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Past(message = "Date of Birth must be past date")
    private LocalDate dateOfBirth;
    private String mobile;
    private String address;
    @CreationTimestamp
    private LocalDateTime joinedDate;
}
