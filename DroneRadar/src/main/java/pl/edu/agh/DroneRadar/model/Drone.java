package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Drone {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String registrationNumber;
    private Locale country;
    private Locale operator;
    private short identification;
    private String identificationLabel;
    private String model;
    private String sign;
    private String type;
    private float fuel;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "drone")
    @Builder.Default
    @ToString.Exclude
    List<Flight> flights = new ArrayList<>();

    //maybe used in future
    public int getCurrentMovementAngle() {
        var latestFlightRecords = this.flights.get(0).getRecords();
        if (latestFlightRecords.size() < 2) return 0;

        var latestRecord = latestFlightRecords.get(latestFlightRecords.size() - 1);
        var secondLatestRecord = latestFlightRecords.get(latestFlightRecords.size() - 2);
        var latestRecordCoords = latestRecord.getFlightDataEntry().getCoordinate();
        var secondLatestRecordCoords = secondLatestRecord.getFlightDataEntry().getCoordinate();

        var movementVectorY = latestRecordCoords.getLatitude() - secondLatestRecordCoords.getLatitude();
        var movementVectorX = latestRecordCoords.getLongitude() - secondLatestRecordCoords.getLongitude();
        var movementVectorModule = Math.sqrt(Math.pow(movementVectorX, 2) + Math.pow(movementVectorY, 2));
        var angle = (int)Math.toDegrees(Math.acos(movementVectorY / movementVectorModule));
        if (movementVectorX < 0) {
            return 60 - angle;
        } else return angle;
    }
}

