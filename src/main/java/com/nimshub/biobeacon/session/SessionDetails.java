package com.nimshub.biobeacon.session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BioBeacon-Api
 * This Entity Class contains all the fields related to SessionDetails
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SessionDetails {
    @Id
    @SequenceGenerator(name = "SessionDetails_SEQ", sequenceName = "SessionDetails_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SessionDetails_SEQ")
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_session_id")
    )
    private Session session;
    private String heartRate;
    private String bloodOxygen;
    private String respirationRate;
    private String ecg;
}


