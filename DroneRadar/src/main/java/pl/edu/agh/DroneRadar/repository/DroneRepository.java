package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.DroneRadar.model.Drone;

import java.util.Collection;
import java.util.List;

@Repository

public interface DroneRepository extends JpaRepository<Drone, Long> {
    Drone findByRegistrationNumber(String registrationNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
    Page<Drone> findAllByFlightsNotNull(Pageable pageable);
    Page<Drone> findDronesByRegistrationNumberIn(List<String> registrationNumbers, Pageable pageable);
    List<Drone> findDronesByRegistrationNumberIn(List<String> registrationNumbers);

}
