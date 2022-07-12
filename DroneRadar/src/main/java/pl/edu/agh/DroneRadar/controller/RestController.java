package pl.edu.agh.DroneRadar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.DroneRadar.dto.DroneBasicDataDto;
import pl.edu.agh.DroneRadar.dto.DroneDetailsDto;
import pl.edu.agh.DroneRadar.dto.FlightDetailsDto;
import pl.edu.agh.DroneRadar.model.Drone;
import pl.edu.agh.DroneRadar.service.DroneService;
import pl.edu.agh.DroneRadar.service.FlightService;

import java.util.function.Function;

@Controller
public class RestController {

    @Autowired
    DroneService droneService;

    @Autowired
    FlightService flightService;

    @GetMapping("/drone/{registrationNumber}")
    public @ResponseBody ResponseEntity<DroneDetailsDto> getDroneDetails(@PathVariable String registrationNumber) {
        DroneDetailsDto dto =  DroneDetailsDto.newInstance(droneService.findDroneByRegistrationNumber(registrationNumber));
        System.out.println(dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/flight/{id}")
    public @ResponseBody ResponseEntity<FlightDetailsDto> getFlightDetails(@PathVariable Long id) {
        FlightDetailsDto dto = FlightDetailsDto.newInstance(flightService.findFlightById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/drones")
    public @ResponseBody ResponseEntity<Page<DroneBasicDataDto>> getDronesBasicData(@RequestParam int page, @RequestParam int elements){
        Page<Drone> dtoList = droneService.findAllWithPagination(page, elements);
        Page<DroneBasicDataDto> dtoPage = dtoList.map(new Function<Drone, DroneBasicDataDto>() {
            @Override
            public DroneBasicDataDto apply(Drone drone) {
                return DroneBasicDataDto.newInstance(drone);
            }
        });
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }
}
