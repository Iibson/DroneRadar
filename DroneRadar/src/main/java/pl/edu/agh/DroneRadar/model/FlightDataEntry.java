package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.DroneRadar.component.Coordinate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDataEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @Embedded
    private Coordinate coordinate;
    private float heading;
    private float speed;
    private float altitude;
}
