package com.nimshub.biobeacon.activities;

import com.nimshub.biobeacon.session.Session;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Nirmala : 11:October:2023
 * This entity class defines the all the properties of ActivityTime
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActivityTime {
    @Id
    @SequenceGenerator(name = "ActivityTime_SEQ", sequenceName = "ActivityTime_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ActivityTime_SEQ")
    private Integer id;
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "session", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_session_id")
    )
    private Session session;
    private Integer cycling;
    private Integer pushUp;
    private Integer running;
    private Integer squat;
    private Integer tableTennis;
    private Integer walking;

}