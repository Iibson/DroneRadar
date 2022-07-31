package pl.edu.agh.DroneRadar.systemCache;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.env.MockEnvironment;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

@RunWith(JUnit4.class)
public class SystemCacheTests {

    SystemCache cache;
    private final int entryLiveSpan = 10000;

    @Before
    public void setCache() {
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("entryLiveSpan", "10");
        environment.setProperty("cacheMaxSize", "100");
        cache = new CaffeineSystemCache(environment);
    }

    @Test
    public void updateEntryOnUpdateAction() {
        //given
        cache.insertOrUpdateEntry(
                DroneCacheEntry.builder().latitude(1f).longitude(1f).heading(1f).registrationNumber("(short) 1").build());
        //when
        cache.insertOrUpdateEntry(DroneCacheEntry.builder().latitude(2f).longitude(2f).heading(1f).registrationNumber("(short) 1").build());
        //then
        var entry = cache.getLatestEntries()
                .get(0);

        Assertions.assertThat(entry.getLatitude()).isEqualTo(2);
    }

    @Test
    public void removeEntryOnRemovalAction() {
        //given
        cache.insertOrUpdateEntry(DroneCacheEntry.builder().latitude(1f).longitude(1f).heading(1f).registrationNumber("(short) 1").build());
        //when
        cache.removeEntryByRegistrationNumber("(short) 1");
        //then
        Assertions.assertThat(cache.getLatestEntries())
                .isEmpty();
    }

    @Test
    public void removeEntryAfterTime() throws InterruptedException {
        //given
        cache.insertOrUpdateEntry(DroneCacheEntry.builder().latitude(1f).longitude(1f).heading(1f).registrationNumber("(short) 1").build());
        //when
        Thread.sleep(entryLiveSpan + 1000);
        //then
        Assertions.assertThat(cache.getLatestEntries())
                .isEmpty();
    }

    @Test
    public void updatingEntryResetsRemoveTimer() throws InterruptedException {
        //given
        cache.insertOrUpdateEntry(DroneCacheEntry.builder().latitude(1f).longitude(1f).heading(1f).registrationNumber("(short) 1").build());
        //when
        Thread.sleep(entryLiveSpan - 1);
        cache.insertOrUpdateEntry(DroneCacheEntry.builder().latitude(1f).longitude(1f).heading(1f).registrationNumber("(short) 1").build());
        Thread.sleep(2);
        //then
        Assertions.assertThat(cache.getLatestEntries())
                .isNotEmpty();
    }

    @Test
    public void gettingEntriesByAreaShouldReturnCorrectResults() {
        //given
        var entry1 = DroneCacheEntry.builder().latitude(15f).longitude(15f).heading(1f).registrationNumber("(short) 2").build();
        var entry2 = DroneCacheEntry.builder().latitude(20f).longitude(20f).heading(1f).registrationNumber("(short) 3").build();
        cache.insertOrUpdateEntry(DroneCacheEntry.builder().latitude(10f).longitude(10f).heading(1f).registrationNumber("(short) 1").build());
        cache.insertOrUpdateEntry(entry1);
        cache.insertOrUpdateEntry(entry2);
        cache.insertOrUpdateEntry(DroneCacheEntry.builder().latitude(25f).longitude(25f).heading(1f).registrationNumber("(short) 4").build());
        //when
        var entries = cache.getLatestEntriesByArea(21, 21, 14, 14);
        //then
        Assertions.assertThat(entries.size())
                .isEqualTo(2);
        Assertions.assertThat(entries)
                .containsExactly(entry1, entry2);
    }
}
