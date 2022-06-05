package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Sensor sensor;

    @OneToOne(cascade = CascadeType.ALL)
    private BasicRecordData basicRecordData;

    @OneToOne(cascade = CascadeType.ALL)
    private FlightDataEntry flightDataEntry;
}
