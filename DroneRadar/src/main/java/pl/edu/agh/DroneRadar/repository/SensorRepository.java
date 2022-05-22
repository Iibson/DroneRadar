package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.DroneRadar.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
