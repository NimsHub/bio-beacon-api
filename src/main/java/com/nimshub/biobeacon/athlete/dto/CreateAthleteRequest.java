package com.nimshub.biobeacon.athlete.dto;

import com.nimshub.biobeacon.user.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CreateAthleteRequest {
    private UUID coachId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Double height;
    private Double weight;
    private String mobile;
    private String address;
    private String occupation;
}
