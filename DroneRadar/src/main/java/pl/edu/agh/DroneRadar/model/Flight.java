package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    Drone drone;

    @ManyToOne
    Set<Record> records = new HashSet<>();
}
