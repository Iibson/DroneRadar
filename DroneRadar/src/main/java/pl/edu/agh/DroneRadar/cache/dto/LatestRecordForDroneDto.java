package pl.edu.agh.DroneRadar.cache.dto;

import pl.edu.agh.DroneRadar.model.Record;

public record LatestRecordForDroneDto(Record record, Short droneIdentification) {
}
