package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.agh.DroneRadar.component.RecordType;

import java.sql.Timestamp;
import java.util.Date;

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
    private Date date;
    private RecordType recordType;

    @ToString.Exclude
    @OneToOne(mappedBy = "basicRecordData")
    private Record record;
}
