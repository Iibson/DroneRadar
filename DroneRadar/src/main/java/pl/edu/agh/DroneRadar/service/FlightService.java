package pl.edu.agh.DroneRadar.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.model.Drone;
import pl.edu.agh.DroneRadar.model.Flight;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.repository.FlightRepository;

@Service
@Transactional
public class FlightService {
    private final FlightRepository flightRepository;
    private final RecordService recordService;

    public FlightService(FlightRepository flightRepository, RecordService recordService) {
        this.flightRepository = flightRepository;
        this.recordService = recordService;
    }

    public Flight addFlight(Flight flight){
        return this.flightRepository.save(flight);
    }

    public void addRecordToFlight(Long flightId, Record record){
        Record r = recordService.addRecord(record);
        Flight flight = flightRepository.getReferenceById(flightId);
        flight.getRecords().add(r);
        flightRepository.save(flight);
    }

    public boolean checkIfFlightExistsById(Long flightId) {
        return this.flightRepository.existsById(flightId);
    }

    public void removeFlightById(Long flightId){
        this.flightRepository.deleteById(flightId);
    }

    public Flight findLastFlightForDrone(Drone drone) {
        return this.flightRepository.findTopByDrone(drone);
    }
}
