package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.DroneRadar.model.Drone;
import pl.edu.agh.DroneRadar.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight findTopByDroneOrderByIdDesc(Drone drone);
    Flight getFlightById(Long id);

}
