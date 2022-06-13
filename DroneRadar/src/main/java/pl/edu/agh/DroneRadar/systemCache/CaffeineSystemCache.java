package pl.edu.agh.DroneRadar.systemCache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
@Scope("singleton")
public class CaffeineSystemCache implements SystemCache {
    private final Cache<String, DroneCacheEntry> cache;

    public CaffeineSystemCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public List<DroneCacheEntry> getLatestEntriesByArea(float maxLat, float maxLong, float minLat, float minLong) {
        return getCacheValuesAsStream()
                .filter(entry -> entry.latitude() > minLat)
                .filter(entry -> entry.latitude() < maxLat)
                .filter(entry -> entry.longitude() > minLong)
                .filter(entry -> entry.longitude() < maxLong)
                .toList();
    }

    @Override
    public List<DroneCacheEntry> getLatestEntries() {
        return getCacheValuesAsStream()
                .toList();
    }

    @Override
    public void insertOrUpdateEntry(DroneCacheEntry droneCacheEntry) {
        cache.put(droneCacheEntry.registrationNumber(), droneCacheEntry);
    }

    @Override
    public void removeEntryByRegistrationNumber(String registrationNumber) {
        cache.invalidate(registrationNumber);
    }

    private Stream<DroneCacheEntry> getCacheValuesAsStream() {
        return cache.asMap()
                .values()
                .stream();
    }
}
