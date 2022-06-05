package pl.edu.agh.DroneRadar.service;

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
}
