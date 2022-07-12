package pl.edu.agh.DroneRadar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.DroneRadar.component.Coordinate;
import pl.edu.agh.DroneRadar.component.RecordType;
import pl.edu.agh.DroneRadar.model.Flight;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class FlightDetailsDto {
    private Long id;
    private List<FlightRecord> records;

    @Getter
    @Setter
    @AllArgsConstructor
    private static class FlightRecord{
        private Timestamp timestamp;
        private RecordType recordType;
        private Coordinate coordinate;
        private float heading;
        private float speed;
        private float altitude;
    }

    private FlightDetailsDto(){}

    public static FlightDetailsDto newInstance(Flight flight) {
        FlightDetailsDto results = new FlightDetailsDto();
        results.id = flight.getId();
        results.records = flight.getRecords().stream().map(record -> new FlightRecord(
                record.getBasicRecordData().getTimestamp(),
                record.getBasicRecordData().getRecordType(),
                record.getFlightDataEntry().getCoordinate(),
                record.getFlightDataEntry().getHeading(),
                record.getFlightDataEntry().getSpeed(),
                record.getFlightDataEntry().getAltitude()
        )).collect(Collectors.toList());
        return results;
    }
}
