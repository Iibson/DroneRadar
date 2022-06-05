package pl.edu.agh.DroneRadar.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.cache.SystemCacheService;
import pl.edu.agh.DroneRadar.component.Coordinate;
import pl.edu.agh.DroneRadar.model.*;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.parser.model.CSVFlightData;
import pl.edu.agh.DroneRadar.service.*;

import java.io.FileReader;

@Service
public class Parser {

    private final SystemCacheService systemCacheService;
    private final DroneService droneService;
    private final BasicRecordDataService basicRecordDataService;
    private final FlightDataEntryService flightDataEntryService;
    private final FlightService flightService;
    private final RecordService recordService;
    private final SensorService sensorService;

    public Parser(SystemCacheService systemCacheService,
                  DroneService droneService,
                  BasicRecordDataService basicRecordDataService,
                  FlightDataEntryService flightDataEntryService,
                  FlightService flightService,
                  RecordService recordService,
                  SensorService sensorService) {
        this.systemCacheService = systemCacheService;
        this.droneService = droneService;
        this.basicRecordDataService = basicRecordDataService;
        this.flightDataEntryService = flightDataEntryService;
        this.flightService = flightService;
        this.recordService = recordService;
        this.sensorService = sensorService;
    }

    public void parseCSV(String filePath) {
        try (var filerReader = new FileReader(filePath)) {
            new CsvToBeanBuilder<CSVFlightData>(filerReader)
                    .withType(CSVFlightData.class)
                    .build()
                    .parse()
                    .forEach(this::updateDroneMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDroneMap(CSVFlightData flightData) {
        var droneIdentification = Short.parseShort(flightData.getDroneIdentification());
        var flightId = Long.parseLong(flightData.getId());
        var drone = this.systemCacheService.getDroneMap().getOrDefault(droneIdentification, null);
        var isNewDrone = false;
        if(drone == null) {
            drone = Drone.builder()
                    .id((long) droneIdentification)
                    .country(LocaleUtils.toLocale(flightData.getCountry()))
                    .identification(droneIdentification)
                    .model(flightData.getModel())
                    .operator(LocaleUtils.toLocale(flightData.getOperator()))
                    .identificationLabel(flightData.getIdentificationLabel())
                    .registrationNumber(flightData.getRegistrationNumber())
                    .sign(flightData.getSign())
                    .type(flightData.getType())
                    .fuel(Float.parseFloat(flightData.getFuel()))
                    .build();
            isNewDrone = true;
        }
        var flight = drone.getFlights()
                .stream()
                .filter(x -> x.getId() == flightId)
                .findAny()
                .orElse(null);
        if(flight == null) {
            flight = Flight.builder()
                    .id(flightId)
                    .drone(drone)
                    .build();
            drone.getFlights()
                    .add(flight);

        }

        var sensor = Sensor.builder()
                .signal(flightData.getSignal())
                .frequency(Float.parseFloat(flightData.getFrequency()))
                .sensorLabel(flightData.getSensorLabel())
                .sensorCoordinate(new Coordinate())
                .build();

        var flightCoordinate = new Coordinate();
        flightCoordinate.setLatitude(Float.parseFloat(flightData.getLatitude()));
        flightCoordinate.setLongitude(Float.parseFloat(flightData.getLongitude()));

        var flightDataEntry = FlightDataEntry.builder()
                .altitude(Float.parseFloat(flightData.getAltitude()))
                .heading(Float.parseFloat(flightData.getHeading()))
                .coordinate(flightCoordinate)
                .speed(Float.parseFloat(flightData.getSpeed()))
                .build();

        var record = Record.builder()
                .basicRecordData(new BasicRecordData())
                .flightDataEntry(flightDataEntry)
                .sensor(sensor)
                .build();

        flight.getRecords().add(record);
        System.out.println("file parsed: " + flight.getId());

        if(isNewDrone) this.systemCacheService.getDroneMap().put(droneIdentification, drone);
        else this.systemCacheService.getDroneMap().replace(droneIdentification, drone);
        //if(!sensorService.checkIfSensorExistsById(sensor.getId())){
            sensorService.addSensor(sensor);
       // }
        if(!droneService.checkIfDroneExistsById((long) droneIdentification)){
            droneService.addDrone(drone);
        }
        if(!flightService.checkIfFlightExistsById(flightId)){
            flightService.addFlight(flight);
        }

       // flightDataEntryService.addFlightDataEntry(flightDataEntry);
        flightService.addRecordToFlight(flight.getId(), record);
    }
}
