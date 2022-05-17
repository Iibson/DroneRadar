package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.agh.DroneRadar.component.Coordinate;

@Data
@Entity
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    String signal;
    float frequency;
    @Embedded
    Coordinate sensorCoordinate;
    String sensorLabel;
}
