package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.DroneRadar.model.Drone;

@Repository

public interface DroneRepository extends JpaRepository<Drone, Long> {
    public Drone findByRegistrationNumber(String registrationNumber);
    public boolean existsByRegistrationNumber(String registrationNumber);
}
