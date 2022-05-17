package pl.edu.agh.DroneRadar;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.agh.DroneRadar.component.Coordinate;
import pl.edu.agh.DroneRadar.model.Sensor;
import pl.edu.agh.DroneRadar.repository.SensorRepository;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    SensorRepository sensorRepository;

    @Override
    public void run(String... args) throws Exception {
        Sensor sensor = new Sensor();
        sensor.setSensorCoordinate(new Coordinate());
        sensorRepository.save(sensor);

        System.out.println("new sensor");

        System.out.println(sensor);
        System.out.println(sensorRepository.findById(1L));

        sensor = sensorRepository.findById(1L).get();

        sensor.setFrequency(111);
//        sensorRepository.save(sensor);

        System.out.println(sensorRepository.findById(1L));
    }
}
