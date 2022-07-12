package pl.edu.agh.DroneRadar.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.DroneRadar.model.Drone;

import java.util.Locale;

@Component
@Getter
@Setter
public class DroneBasicDataDto {
    private Locale country;
    private short identification;
    private String model;
    private String registrationNumber;

    public static DroneBasicDataDto newInstance(Drone drone) {
        DroneBasicDataDto results = new DroneBasicDataDto();
        results.country = drone.getCountry();
        results.identification = drone.getIdentification();
        results.model = drone.getModel();
        results.registrationNumber = drone.getRegistrationNumber();

        return results;
    }
}