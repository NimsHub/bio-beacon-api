package com.nimshub.biobeacon.session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SessionMotionData {
    @Id
    @SequenceGenerator(name = "SessionMotion_SEQ", sequenceName = "SessionMotion_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SessionMotion_SEQ")
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_session_id")
    )
    private Session session;
    private String deviceOneMotionData;
    private String deviceTwoMotionData;
    private String deviceThreeMotionData;
    private String deviceFourMotionData;
    private String deviceFiveMotionData;
}
