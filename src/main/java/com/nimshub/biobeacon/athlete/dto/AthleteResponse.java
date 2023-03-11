package com.nimshub.biobeacon.athlete.dto;

import com.nimshub.biobeacon.user.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AthleteResponse {
    private UUID athleteId;
    private String firstname;
    private String lastname;
    private String email;
    private Gender gender;
    private String occupation;
}

