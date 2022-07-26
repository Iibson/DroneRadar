package pl.edu.agh.DroneRadar.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.component.Coordinate;
import pl.edu.agh.DroneRadar.component.RecordType;
import pl.edu.agh.DroneRadar.model.*;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.parser.model.CSVFlightData;
import pl.edu.agh.DroneRadar.service.*;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

import java.io.FileReader;
import java.sql.Timestamp;

@Service
public class Parser {

    private final SystemCache systemCache;
    private final DroneService droneService;
    private final BasicRecordDataService basicRecordDataService;
    private final FlightDataEntryService flightDataEntryService;
    private final FlightService flightService;
    private final RecordService recordService;
    private final SensorService sensorService;

    public Parser(SystemCache systemCache,
                  DroneService droneService,
                  BasicRecordDataService basicRecordDataService,
                  FlightDataEntryService flightDataEntryService,
                  FlightService flightService,
                  RecordService recordService,
                  SensorService sensorService) {
        this.systemCache = systemCache;
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
        var registrationNumber = flightData.getRegistrationNumber();
        //var flightId = Long.parseLong(flightData.getId());
//        var droneCacheEntry = this.systemCache.getLatestEntries()
//                .stream()
//                .filter(x -> x.registrationNumber() == registrationNumber)
//                .findAny()
//                .orElse(null);

        Drone drone = droneService.findDroneByRegistrationNumber(flightData.getRegistrationNumber());


        if (drone == null) {
            drone = Drone.builder()
                    .country(LocaleUtils.toLocale(flightData.getCountry()))
                    .identification(Short.parseShort(flightData.getDroneIdentification()))
                    .model(flightData.getModel())
                    .operator(LocaleUtils.toLocale(flightData.getOperator()))
                    .identificationLabel(flightData.getIdentificationLabel())
                    .registrationNumber(flightData.getRegistrationNumber())
                    .sign(flightData.getSign())
                    .type(flightData.getType())
                    .fuel(Float.parseFloat(flightData.getFuel()))
                    .build();
        }
        //TODO add finding current flight if FLAG = UPD
        var flight = this.flightService.findLastFlightForDrone(drone);
//flight.getRecords().stream().filter(o->o.getBasicRecordData().getRecordType() == RecordType.DROP).findAny().orElse(null) == null
        if (flight == null || RecordType.valueOf(flightData.getFlag()) == RecordType.BEG) {
            flight = Flight.builder()
                    .drone(drone)
                    .build();
            //drone.getFlights().add(flight);

        }

/*        var flight = drone.getFlights()
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

        }*/

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

        var basicRecordData = BasicRecordData.builder()
                .recordType(RecordType.valueOf(flightData.getFlag()))
//                .timestamp(Timestamp.valueOf(flightData.getTime()))
                .build();

        var record = Record.builder()
                .basicRecordData(basicRecordData)
                .flightDataEntry(flightDataEntry)
                .sensor(sensor)
                .build();

        System.out.println("file parsed");
        var toInsertOrUpdate = DroneCacheEntry.builder()
                .latitude(Float.parseFloat(flightData.getLatitude()))
                .longitude(Float.parseFloat(flightData.getLongitude()))
                .heading(Float.parseFloat(flightData.getHeading()))
                .registrationNumber(flightData.getRegistrationNumber())
                .id(flightData.getId())
                .country(flightData.getCountry())
                .fuelState(flightData.getFuel())
                .frequency(flightData.getFrequency())
                .model(flightData.getModel())
                .build();
        this.systemCache.insertOrUpdateEntry(toInsertOrUpdate);

        //if(!sensorService.checkIfSensorExistsById(sensor.getId())){
        sensorService.addSensor(sensor);
        // }
        if (!droneService.checkIfDroneExistsByRegistrationNumber(drone.getRegistrationNumber())) {
            droneService.addDrone(drone);
        }
        if (RecordType.valueOf(flightData.getFlag()) == RecordType.BEG) {
            flight = flightService.addFlight(flight);
        }
     /*   if(!flightService.checkIfFlightExistsById(flightId)){
            flightService.addFlight(flight);
        }*/

        flightService.addRecordToFlight(flight.getId(), record);
    }
}
