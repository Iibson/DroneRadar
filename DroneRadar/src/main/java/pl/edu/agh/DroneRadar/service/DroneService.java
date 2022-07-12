package pl.edu.agh.DroneRadar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.model.Drone;
import pl.edu.agh.DroneRadar.repository.DroneRepository;

@Service
public class DroneService {
    private final DroneRepository droneRepository;

    public DroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    public Drone addDrone(Drone drone){
        return this.droneRepository.save(drone);
    }

    public void removeDroneById(Long droneId){
        this.droneRepository.deleteById(droneId);
    }

    public boolean checkIfDroneExistsById(Long droneId) {
        return this.droneRepository.existsById(droneId);
    }

    public Drone findDroneByRegistrationNumber(String registrationNumber) {
       return droneRepository.findByRegistrationNumber(registrationNumber);
    }

    public boolean checkIfDroneExistsByRegistrationNumber(String registrationNumber) {
        return droneRepository.existsByRegistrationNumber(registrationNumber);
    }

    public Page<Drone> findAllWithPagination(int page, int elements){
        Pageable pageable = PageRequest.of(page, elements, Sort.by("identification").ascending());
        return droneRepository.findAllByFlightsNotNull(pageable);
    }
}
