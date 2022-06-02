package pl.edu.agh.DroneRadar.systemCache.models;

public record DroneCacheEntry(float latitude, float longitude, float heading, String droneName, short droneIdentification) {
}
