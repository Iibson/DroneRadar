package pl.edu.agh.DroneRadar;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.agh.DroneRadar.component.Coordinate;
import pl.edu.agh.DroneRadar.component.Direction;
import pl.edu.agh.DroneRadar.component.RecordType;
import pl.edu.agh.DroneRadar.model.*;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.repository.FlightDataEntryRepository;
import pl.edu.agh.DroneRadar.repository.SensorRepository;
import pl.edu.agh.DroneRadar.service.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class MyRunner implements CommandLineRunner {

    private final BasicRecordDataService basicRecordDataService;
    private final DroneService droneService;
    private final FlightDataEntryService flightDataEntryService;
    private final FlightService flightService;
    private final RecordService recordService;
    private final SensorService sensorService;

    public MyRunner(
            BasicRecordDataService basicRecordDataService,
            DroneService droneService,
            FlightDataEntryService flightDataEntryService,
            FlightService flightService,
            RecordService recordService,
            SensorService sensorService
    ) {
        this.basicRecordDataService = basicRecordDataService;
        this.droneService = droneService;
        this.flightDataEntryService = flightDataEntryService;
        this.flightService = flightService;
        this.recordService = recordService;
        this.sensorService = sensorService;
    }

    @Override
    public void run(String... args) throws Exception {
        Sensor sensor = Sensor.builder()
                .frequency(111)
                .sensorLabel("sensor")
                .signal("signal")
                .sensorCoordinate(new Coordinate(11,11,11, Direction.N, 11,11,11, Direction.E))
                .build();

        sensor = this.sensorService.addSensor(sensor);

        System.out.println(sensor);

        BasicRecordData basicRecordData = BasicRecordData.builder()
                .recordType(RecordType.BEG)
                .server("server")
                .sourceInternalId("asdf")
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        basicRecordData = basicRecordDataService.addBasicRecordData(basicRecordData);

        System.out.println(basicRecordData);

        Drone drone = Drone.builder()
                .country(Locale.CANADA)
                .fuel(11)
                .identification((short) 12)
                .identificationLabel("q2")
                .model("model")
                .operator(Locale.CANADA)
                .registrationNumber("xxxx")
                .type("type")
                .sign("ad")
                .build();

        drone = droneService.addDrone(drone);

        System.out.println(drone);

        FlightDataEntry flightDataEntry = FlightDataEntry.builder()
                .altitude(11)
                .coordinate(new Coordinate())
                .heading(12)
                .speed(111)
                .build();
        flightDataEntry = flightDataEntryService.addFlightDataEntry(flightDataEntry);

        System.out.println(flightDataEntry);

        Record record = Record.builder()
                .basicRecordData(basicRecordData)
                .flightDataEntry(flightDataEntry)
                .sensor(sensor)
                .build();
        record = recordService.addRecord(record);

        System.out.println(record);

        Flight flight = Flight.builder()
                .drone(drone)
                .records(Set.of(record))
                .build();
        flight = flightService.addFlight(flight);

        System.out.println(flight);
    }
}
