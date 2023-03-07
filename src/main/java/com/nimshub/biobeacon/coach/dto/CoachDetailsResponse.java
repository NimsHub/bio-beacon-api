package com.nimshub.biobeacon.coach.dto;

import com.nimshub.biobeacon.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachDetailsResponse {
    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String mobile;
    private String address;
}
