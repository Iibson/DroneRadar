package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.agh.DroneRadar.component.Coordinate;

@Data
@Entity
public class FlightDataEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long Id;

    @Embedded
    Coordinate coordinate;
    float heading;
    float speed;
    float altitude;
}
