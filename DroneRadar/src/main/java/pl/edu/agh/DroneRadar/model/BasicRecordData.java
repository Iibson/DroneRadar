package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.agh.DroneRadar.component.RecordType;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicRecordData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String sourceInternalId;
    private String server;
    private Timestamp timestamp;
    private RecordType recordType;

    @ToString.Exclude
    @OneToOne(mappedBy = "basicRecordData")
    private Record record;
}
