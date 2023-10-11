package com.nimshub.biobeacon.activities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityTimeResponse {
    private Integer cycling;
    private Integer pushUp;
    private Integer running;
    private Integer squat;
    private Integer tableTennis;
    private Integer walking;
}
