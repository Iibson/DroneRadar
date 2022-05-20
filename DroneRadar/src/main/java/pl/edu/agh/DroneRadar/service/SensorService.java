package pl.edu.agh.DroneRadar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.model.Sensor;
import pl.edu.agh.DroneRadar.repository.SensorRepository;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor addSensor(Sensor sensor){
        return this.sensorRepository.save(sensor);
    }

    public void removeSensor(Long sensorId){
        this.sensorRepository.deleteById(sensorId);
    }
}
