package pl.edu.agh.DroneRadar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.model.FlightDataEntry;
import pl.edu.agh.DroneRadar.repository.FlightDataEntryRepository;

@Service
public class FlightDataEntryService {
    private final FlightDataEntryRepository flightDataEntryRepository;

    public FlightDataEntryService(FlightDataEntryRepository flightDataEntryRepository) {
        this.flightDataEntryRepository = flightDataEntryRepository;
    }

    public FlightDataEntry addFlightDataEntry(FlightDataEntry flightDataEntry){
        return this.flightDataEntryRepository.save(flightDataEntry);
    }

    public void  removeFlightDataEntryById(Long flightDataEntryId){
        this.flightDataEntryRepository.deleteById(flightDataEntryId);
    }
}
