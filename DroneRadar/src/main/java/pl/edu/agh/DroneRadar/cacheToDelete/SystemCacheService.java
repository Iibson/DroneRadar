//package pl.edu.agh.DroneRadar.cache;
//
//import com.opencsv.bean.CsvToBeanBuilder;
//import lombok.Getter;
//import org.apache.commons.lang3.LocaleUtils;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Service;
//import pl.edu.agh.DroneRadar.cache.dto.LatestRecordForDroneDto;
//import pl.edu.agh.DroneRadar.component.Coordinate;
//import pl.edu.agh.DroneRadar.model.*;
//import pl.edu.agh.DroneRadar.model.Record;
//import pl.edu.agh.DroneRadar.parser.model.CSVFlightData;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Scope("singleton")
//public class SystemCacheService {
//    private final Map<Short, Drone> droneMap = new HashMap<>();
//    private final String directoryPath = "";
//
//    public SystemCacheService() {
//        new Thread(() -> {
//            try {
//                watchDirectory();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//
//    public List<LatestRecordForDroneDto> getLatestRecordsForDrones() {
//        return droneMap.values()
//                .stream()
//                .map(drone -> {
//                    var record = drone.getFlights().get(0).getRecords().get(drone.getFlights().get(0).getRecords().size() - 1);
//                    var id = drone.getIdentification();
//                    return new LatestRecordForDroneDto(record, id);
//                })
//                .toList();
//    }
//
//    private void watchDirectory() throws Exception {
//        WatchService watchService = FileSystems.getDefault().newWatchService();
//        Path path = Paths.get(directoryPath);
//        path.register(
//                watchService,
//                StandardWatchEventKinds.ENTRY_CREATE);
//
//        WatchKey key;
//        while ((key = watchService.take()) != null) {
//            for (WatchEvent<?> event : key.pollEvents()) {
//                WatchEvent<Path> ev = cast(event);
//                if(ev.context() == null) continue;
//                var filePath = Paths.get(directoryPath, ev.context().toString());
//                handleFileAsync(filePath);
//            }
//            key.reset();
//        }
//    }
//
//    private void handleFileAsync(Path filePath) {
//        new Thread(() -> {
//            try {
//                Thread.sleep(3000);
//                parseCSV(filePath.toString());
//                Files.delete(filePath);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        })
//        .start();
//    }
//
//    private void parseCSV(String filePath) {
//        try (var filerReader = new FileReader(filePath)) {
//            new CsvToBeanBuilder<CSVFlightData>(filerReader)
//                    .withType(CSVFlightData.class)
//                    .build()
//                    .parse()
//                    .forEach(this::updateDroneMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private <T> WatchEvent<T> cast(WatchEvent<?> event) {
//        return (WatchEvent<T>) event;
//    }
//
//    private void updateDroneMap(CSVFlightData flightData) {
//        var droneIdentification = Short.parseShort(flightData.getDroneIdentification());
//        var flightId = Long.parseLong(flightData.getId());
//        var drone = droneMap.getOrDefault(droneIdentification, null);
//        var isNewDrone = false;
//        if(drone == null) {
//            drone = Drone.builder()
//                    .country(LocaleUtils.toLocale(flightData.getCountry()))
//                    .identification(droneIdentification)
//                    .model(flightData.getModel())
//                    .operator(LocaleUtils.toLocale(flightData.getOperator()))
//                    .identificationLabel(flightData.getIdentificationLabel())
//                    .registrationNumber(flightData.getRegistrationNumber())
//                    .sign(flightData.getSign())
//                    .type(flightData.getType())
//                    .fuel(Float.parseFloat(flightData.getFuel()))
//                    .build();
//            isNewDrone = true;
//        }
//        var flight = drone.getFlights()
//                .stream()
//                .filter(x -> x.getId() == flightId)
//                .findAny()
//                .orElse(null);
//        if(flight == null) {
//            flight = Flight.builder()
//                    .id(flightId)
//                    .drone(drone)
//                    .build();
//            drone.getFlights()
//                    .add(flight);
//        }
//
//        var sensor = Sensor.builder()
//                .signal(flightData.getSignal())
//                .frequency(Float.parseFloat(flightData.getFrequency()))
//                .sensorLabel(flightData.getSensorLabel())
//                .sensorCoordinate(new Coordinate())
//                .build();
//
//        var flightCoordinate = new Coordinate();
//        flightCoordinate.setLatitude(Float.parseFloat(flightData.getLatitude()));
//        flightCoordinate.setLongitude(Float.parseFloat(flightData.getLongitude()));
//
//        var flightDataEntry = FlightDataEntry.builder()
//                .altitude(Float.parseFloat(flightData.getAltitude()))
//                .heading(Float.parseFloat(flightData.getHeading()))
//                .coordinate(flightCoordinate)
//                .speed(Float.parseFloat(flightData.getSpeed()))
//                .build();
//
//        var record = Record.builder()
//                .basicRecordData(new BasicRecordData())
//                .flightDataEntry(flightDataEntry)
//                .sensor(sensor)
//                .build();
//
//        flight.getRecords().add(record);
//        System.out.println("file parsed: " + flight.getId());
//        if(isNewDrone) droneMap.put(droneIdentification, drone);
//        else droneMap.replace(droneIdentification, drone);
//    }
//}
