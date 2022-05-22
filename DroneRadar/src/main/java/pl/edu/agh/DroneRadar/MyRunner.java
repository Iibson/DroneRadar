package pl.edu.agh.DroneRadar;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.agh.DroneRadar.component.Coordinate;
import pl.edu.agh.DroneRadar.model.*;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.repository.SensorRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    SensorRepository sensorRepository;

    @Override
    public void run(String... args) throws Exception {
        /*Sensor sensor = new Sensor();
        sensor.setSensorCoordinate(new Coordinate());
        sensorRepository.save(sensor);

        System.out.println("new sensor");

        System.out.println(sensor);
        System.out.println(sensorRepository.findById(1L));

        sensor = sensorRepository.findById(1L).get();

        sensor.setFrequency(111);
//        sensorRepository.save(sensor);

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
