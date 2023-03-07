package com.nimshub.biobeacon.coach.dto;

import com.nimshub.biobeacon.user.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCoachRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String mobile;
    private String address;
}
