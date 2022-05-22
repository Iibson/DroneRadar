package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.DroneRadar.model.FlightDataEntry;

public interface FlightDataEntryRepository extends JpaRepository<FlightDataEntry, Long> {
}
