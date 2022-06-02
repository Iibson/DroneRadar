package pl.edu.agh.DroneRadar.systemCache.interfaces;

import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

import java.util.Collection;
import java.util.List;

public interface SystemCache {

    List<DroneCacheEntry> getLatestEntriesByArea(float maxLat, float maxLong, float minLat, float minLong);
    List<DroneCacheEntry> getLatestEntries();
    void insertOrUpdateEntry(DroneCacheEntry droneCacheEntry);
    void removeEntryByIdentification(Short droneIdentification);
}
