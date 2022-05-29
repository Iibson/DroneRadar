package pl.edu.agh.DroneRadar.systemCache.interfaces;

import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

import java.util.Collection;
import java.util.List;

public interface SystemCache {

    List<DroneCacheEntry> getLatestEntries();
    void insertOrUpdateEntry(DroneCacheEntry droneCacheEntry);
    void removeEntryByIdentification(Short droneIdentification);
}
