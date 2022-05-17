package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Locale;

@Entity
@Data
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    Locale country;
    Locale operator;
    short identification;
    String identificationLabel;
    String model;
    String registrationNumber;
    String sign;
    String type;
    float fuel;
}
