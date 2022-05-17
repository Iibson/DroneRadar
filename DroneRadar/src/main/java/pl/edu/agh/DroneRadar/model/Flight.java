package pl.edu.agh.DroneRadar.model;

import jakarta.persistence.*;

import java.util.Set;

public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    Drone drone;

    @ManyToOne
    Set<Record> records;
}
