package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    Sensor sensor;

    @OneToOne
    BasicRecordData basicRecordData;

    @OneToOne
    FlightDataEntry flightDataEntry;
}
