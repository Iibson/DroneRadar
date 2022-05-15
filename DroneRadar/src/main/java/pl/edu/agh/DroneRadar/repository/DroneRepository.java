package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.DroneRadar.model.Drone;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
