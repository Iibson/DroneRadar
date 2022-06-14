package pl.edu.agh.DroneRadar.systemCache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
@Scope("singleton")
@PropertySource("classpath:cache.properties")
public class CaffeineSystemCache implements SystemCache {
    private final Cache<String, DroneCacheEntry> cache;
    public CaffeineSystemCache(Environment environment) {
        var entryLiveSpan = Integer.parseInt(environment.getProperty("cache.entryLiveSpan", "10"));
        var cacheMaxSize = Integer.parseInt(environment.getProperty("cache.cacheMaxSize", "100"));
        this.cache = Caffeine.newBuilder()
                .maximumSize(cacheMaxSize)
                .expireAfterWrite(entryLiveSpan, TimeUnit.SECONDS)
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
