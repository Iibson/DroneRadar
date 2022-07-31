package pl.edu.agh.DroneRadar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.DroneRadar.dto.DroneBasicDataDto;
import pl.edu.agh.DroneRadar.dto.DroneDetailsDto;
import pl.edu.agh.DroneRadar.dto.FlightDetailsDto;
import pl.edu.agh.DroneRadar.model.Drone;
import pl.edu.agh.DroneRadar.service.DroneService;
import pl.edu.agh.DroneRadar.service.FlightService;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class RestController {

    @Autowired
    DroneService droneService;

    @Autowired
    FlightService flightService;

    @Autowired
    SystemCache systemCache;

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
        List<String> dronesRegistrationNumbers = systemCache.getLatestEntries().stream().map(DroneCacheEntry::getRegistrationNumber).toList();
        Page<Drone> dtoList = droneService.findDronesByRegistrationNumberIn(dronesRegistrationNumbers, page, elements);
        Page<DroneBasicDataDto> dtoPage = dtoList.map(new Function<Drone, DroneBasicDataDto>() {
            @Override
            public DroneBasicDataDto apply(Drone drone) {
                return DroneBasicDataDto.newInstance(drone);
            }
        });
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping("/drones-ids")
    public @ResponseBody ResponseEntity<List<DroneBasicDataDto>> getDronesBasicData(@RequestParam String[] drones){
        List<Drone> dtoList = droneService.findDronesByRegistrationNumberInList(Arrays.stream(drones).toList());

        return new ResponseEntity<>(dtoList.stream().map(DroneBasicDataDto::newInstance).toList(), HttpStatus.OK);
    }
}
