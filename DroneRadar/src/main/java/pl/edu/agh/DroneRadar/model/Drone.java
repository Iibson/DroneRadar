package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Locale country;
    private Locale operator;
    private short identification;
    private String identificationLabel;
    private String model;
    private String registrationNumber;
    private String sign;
    private String type;
    private float fuel;
    @OneToMany(fetch = FetchType.EAGER)
    @Builder.Default
    List<Flight> flights = new ArrayList<>();
}
