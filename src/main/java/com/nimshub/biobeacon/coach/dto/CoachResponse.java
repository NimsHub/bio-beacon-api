package com.nimshub.biobeacon.coach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachResponse {
    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
}
