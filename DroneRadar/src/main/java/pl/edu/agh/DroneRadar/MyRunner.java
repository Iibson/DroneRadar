package pl.edu.agh.DroneRadar;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.agh.DroneRadar.component.Coordinate;
import pl.edu.agh.DroneRadar.component.Direction;
import pl.edu.agh.DroneRadar.component.RecordType;
import pl.edu.agh.DroneRadar.model.*;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.repository.FlightDataEntryRepository;
import pl.edu.agh.DroneRadar.model.*;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.repository.SensorRepository;
import pl.edu.agh.DroneRadar.service.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

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

        flightService.addRecordToFlight(flight.getId(), record);
        flightService.addRecordToFlight(flight.getId(), record);
        flightService.addRecordToFlight(flight.getId(), record);

        System.out.println(flight);
        System.out.println(sensorRepository.findById(1L));*/

        watchDirectory();
    }

    public void watchDirectory() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("../flightData/");
        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent<Path> ev = cast(event);
                Path filename = ev.context();
                parseCSV( filename.toString());
            }
            key.reset();
        }
    }

    public void parseCSV(String fileName) throws FileNotFoundException {

        String filePath = "../flightData/"+fileName;
        List<CSVFlightData> flights = new CsvToBeanBuilder(new FileReader(filePath))
                .withType(CSVFlightData.class)
                .build()
                .parse();
        for(CSVFlightData flightData: flights) {
            Flight flight = new Flight();

            Drone drone = new Drone();
            drone.setCountry(LocaleUtils.toLocale(flightData.getCountry()));
            drone.setIdentification(Short.parseShort(flightData.getIdentification()));
            drone.setModel(flightData.getModel());
            drone.setOperator(LocaleUtils.toLocale(flightData.getOperator()));
            drone.setIdentificationLabel(flightData.getIdentificationLabel());
            drone.setRegistrationNumber(flightData.getRegistrationNumber());
            drone.setSign(flightData.getSign());
            drone.setType(flightData.getType());
            drone.setFuel(Float.parseFloat(flightData.getFuel()));

            Record record = new Record();

            Sensor sensor = new Sensor();
            sensor.setSignal(flightData.getSignal());
            sensor.setFrequency(Float.parseFloat(flightData.getFrequency()));
            sensor.setSensorLabel(flightData.getSensorLabel());

            Coordinate sensorCoordinate = new Coordinate();
//            sensorCoordinate.setLatitude(Float.parseFloat(flightData.getSensorLat()));
//            sensorCoordinate.setLongitude(Float.parseFloat(flightData.getSensorLon()));
            sensor.setSensorCoordinate(sensorCoordinate);

            BasicRecordData basicRecordData = new BasicRecordData();
            FlightDataEntry flightDataEntry = new FlightDataEntry();

            Coordinate flightCoordinate = new Coordinate();
            flightCoordinate.setLatitude(Float.parseFloat(flightData.getLatitude()));
            flightCoordinate.setLongitude(Float.parseFloat(flightData.getLongitude()));

            flightDataEntry.setCoordinate(flightCoordinate);
            flightDataEntry.setAltitude(Float.parseFloat(flightData.getAltitude()));
            flightDataEntry.setHeading(Float.parseFloat(flightData.getHeading()));
            flightDataEntry.setSpeed(Float.parseFloat(flightData.getSpeed()));

            record.setBasicRecordData(basicRecordData);
            record.setFlightDataEntry(flightDataEntry);
            record.setSensor(sensor);

            flight.setDrone(drone);
            flight.getRecords().add(record);
            System.out.println(flight);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
}
