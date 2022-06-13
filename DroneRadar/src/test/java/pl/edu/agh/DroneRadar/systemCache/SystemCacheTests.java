package pl.edu.agh.DroneRadar.systemCache;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.edu.agh.DroneRadar.systemCache.interfaces.SystemCache;
import pl.edu.agh.DroneRadar.systemCache.models.DroneCacheEntry;

@RunWith(JUnit4.class)
public class SystemCacheTests {

    SystemCache cache;

    @Before
    public void setCache() {
        cache = new CaffeineSystemCache();
    }

    @Test
    public void updateEntryOnUpdateAction() {
        //given
        cache.insertOrUpdateEntry(new DroneCacheEntry(1f, 1f, 1f, "(short) 1"));
        //when
        cache.insertOrUpdateEntry(new DroneCacheEntry(2f, 2f, 1f,  "(short) 1"));
        //then
        var entry = cache.getLatestEntries()
                .get(0);

        Assertions.assertThat(entry.latitude()).isEqualTo(2);
    }

    @Test
    public void removeEntryOnRemovalAction() {
        //given
        cache.insertOrUpdateEntry(new DroneCacheEntry(1f, 1f, 1f,  "(short) 1"));
        //when
        cache.removeEntryByRegistrationNumber("(short) 1");
        //then
        Assertions.assertThat(cache.getLatestEntries())
                .isEmpty();
    }

    @Test
    public void removeEntryAfterTime() throws InterruptedException {
        //given
        cache.insertOrUpdateEntry(new DroneCacheEntry(1f, 1f, 1f,  "(short) 1"));
        //when
        Thread.sleep(4000);
        //then
        Assertions.assertThat(cache.getLatestEntries())
                .isEmpty();
    }

    @Test
    public void updatingEntryResetsRemoveTimer() throws InterruptedException {
        //given
        cache.insertOrUpdateEntry(new DroneCacheEntry(1f, 1f, 1f,  "(short) 1"));
        //when
        Thread.sleep(2000);
        cache.insertOrUpdateEntry(new DroneCacheEntry(1f, 1f, 1f,  "(short) 1"));
        Thread.sleep(2000);
        //then
        Assertions.assertThat(cache.getLatestEntries())
                .isNotEmpty();
    }

    @Test
    public void gettingEntriesByAreaShouldReturnCorrectResults() {
        //given
        var entry1 = new DroneCacheEntry(15f, 15f, 1f,  "(short) 2");
        var entry2 = new DroneCacheEntry(20f, 20f, 1f,  "(short) 3");
        cache.insertOrUpdateEntry(new DroneCacheEntry(10f, 10f, 1f,  "(short) 1"));
        cache.insertOrUpdateEntry(entry1);
        cache.insertOrUpdateEntry(entry2);
        cache.insertOrUpdateEntry(new DroneCacheEntry(25f, 25f, 1f,  "(short) 4"));
        //when
        var entries = cache.getLatestEntriesByArea(21, 21, 14, 14);
        //then
        Assertions.assertThat(entries.size())
                .isEqualTo(2);
        Assertions.assertThat(entries)
                .containsExactly(entry1, entry2);
    }
}
