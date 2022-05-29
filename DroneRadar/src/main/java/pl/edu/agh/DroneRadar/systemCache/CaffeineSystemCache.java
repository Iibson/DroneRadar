package pl.edu.agh.DroneRadar.systemCache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Scope("singleton")
public class CaffeineSystemCache implements SystemCache {
    private final Cache<Short, DroneCacheEntry> cache;

    public CaffeineSystemCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public List<DroneCacheEntry> getLatestEntries() {
        return cache.asMap()
                .values()
                .stream()
                .toList();
    }

    @Override
    public void insertOrUpdateEntry(DroneCacheEntry droneCacheEntry) {
        cache.put(droneCacheEntry.droneIdentification(), droneCacheEntry);
    }

    @Override
    public void removeEntryByIdentification(Short droneIdentification) {
        cache.invalidate(droneIdentification);
    }
}
