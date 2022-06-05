package pl.edu.agh.DroneRadar.cache;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.cache.dto.LatestRecordForDroneDto;
import pl.edu.agh.DroneRadar.component.Coordinate;
import pl.edu.agh.DroneRadar.model.*;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.parser.model.CSVFlightData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("singleton")
public class SystemCacheService {
    private final Map<Short, Drone> droneMap = new HashMap<>();

    public SystemCacheService() {

    }

    public List<LatestRecordForDroneDto> getLatestRecordsForDrones() {
        return droneMap.values()
                .stream()
                .map(drone -> {
                    var record = drone.getFlights().get(0).getRecords().get(drone.getFlights().get(0).getRecords().size() - 1);
                    var id = drone.getIdentification();
                    return new LatestRecordForDroneDto(record, id);
                })
                .toList();
    }

    public Map<Short, Drone> getDroneMap() {
        return droneMap;
    }
}
