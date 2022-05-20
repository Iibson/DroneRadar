package pl.edu.agh.DroneRadar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.model.Flight;
import pl.edu.agh.DroneRadar.repository.FlightRepository;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(Flight flight){
        return this.flightRepository.save(flight);
    }

    public void removeFlightById(Long flightId){
        this.flightRepository.deleteById(flightId);
    }
}
