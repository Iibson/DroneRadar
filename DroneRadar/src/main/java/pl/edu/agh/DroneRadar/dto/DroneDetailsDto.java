package pl.edu.agh.DroneRadar.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import pl.edu.agh.DroneRadar.component.RecordType;
import pl.edu.agh.DroneRadar.model.Drone;
import pl.edu.agh.DroneRadar.model.Flight;
import pl.edu.agh.DroneRadar.model.Record;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class DroneDetailsDto {
    private Long id;
    private Locale country;
    private Locale operator;
    private short identification;
    private String identificationLabel;
    private String model;
    private String registrationNumber;
    private String sign;
    private String type;
    private float fuel;

    List<FlightData> flights = new ArrayList<>();
    CurrentFlightData currentFlight;

    @Override
    public String toString() {
        return "DroneDetailsDto{" +
                "id=" + id +
                ", country=" + country +
                ", operator=" + operator +
                ", identification=" + identification +
                ", identificationLabel='" + identificationLabel + '\'' +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", sign='" + sign + '\'' +
                ", type='" + type + '\'' +
                ", fuel=" + fuel +
                ", flights=" + flights +
                '}';
    }

    @Getter
    @Setter
    private static class FlightData {
        FlightData(Long id, Timestamp takeOff, Timestamp landing){
            this.id = id;
            this.takeOff = takeOff;
            this.landing = landing;
        }

        Long id;
        Timestamp takeOff;
        Timestamp landing;
    }

    @Getter
    @Setter
    private static class CurrentFlightData {
        CurrentFlightData(float heading, float speed, float altitude){
            this.heading = heading;
            this.speed = speed;
            this.altitude = altitude;
        }

        float heading;
        float speed;
        float altitude;
    }

    public static DroneDetailsDto newInstance(Drone drone){
        DroneDetailsDto results = new DroneDetailsDto();
        results.country = drone.getCountry();
        results.operator = drone.getOperator();
        results.identification = drone.getIdentification();
        results.identificationLabel = drone.getIdentificationLabel();
        results.model = drone.getModel();
        results.registrationNumber = drone.getRegistrationNumber();
        results.sign = drone.getSign();
        results.type = drone.getType();
        results.fuel = drone.getFuel();

        //System.out.println(drone.getFlights());

        List<Timestamp> drones = drone.getFlights().get(0).getRecords().stream().map(record -> {
            if(ObjectUtils.isEmpty(record.getBasicRecordData().getTimestamp())) {
                return new Timestamp(12432141);
            }
            else{
                return record.getBasicRecordData().getTimestamp();
            }
        }).toList();
        //System.out.println(drones);
        Flight lastFlight = drone.getFlights().get(drone.getFlights().size()-1);
        Record lastRecord = lastFlight.getRecords().get(lastFlight.getRecords().size()-1);
        if(lastRecord.getBasicRecordData().getRecordType()!= RecordType.DROP) {
            System.out.println("DZIALA");
            System.out.println(lastRecord.getFlightDataEntry().getAltitude());
            results.currentFlight = new CurrentFlightData(lastRecord.getFlightDataEntry().getHeading(),
                    lastRecord.getFlightDataEntry().getSpeed(),
                    lastRecord.getFlightDataEntry().getAltitude());
        }
        System.out.println(results.currentFlight.altitude);
        results.flights = drone.getFlights().stream().map(flight -> new FlightData(
                flight.getId(),
                new Timestamp(32531),
                new Timestamp(32151234)
//                flight.getRecords().stream().map(a -> a.getBasicRecordData().getTimestamp()).sorted().findFirst().get(),
//                flight.getRecords().stream().sorted((a, b) -> b.getBasicRecordData().getTimestamp().compareTo(a.getBasicRecordData().getTimestamp())).findFirst().get().getBasicRecordData().getTimestamp()
                )
        ).collect(Collectors.toList());


        return results;
    }
}
