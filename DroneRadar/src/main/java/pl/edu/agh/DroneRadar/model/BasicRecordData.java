package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import pl.edu.agh.DroneRadar.component.RecordType;

import java.sql.Timestamp;

@Data
@Entity
public class BasicRecordData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    String sourceInternalId;
    String server;
    Timestamp timestamp;
    RecordType recordType;
}
