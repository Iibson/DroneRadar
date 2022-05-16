package models;

import exceptions.NoConfigFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

public class ConfigurationTests {

    @Test
    public void testNoConfigurationError() {
        Assertions.assertThrows(NoConfigFileException.class, () -> Configuration.initConfiguration("no-file.json"));
    }

    @Test
    public void testConfigFields() throws IOException, NoConfigFileException {
        var config = Configuration.initConfiguration("config-test.json");

        Assertions.assertEquals("---folder----", config.getFilePath());
        Assertions.assertEquals("csv", config.getCreatedFileTypes());
        Assertions.assertEquals(10, config.getDroneNumber());
        Assertions.assertEquals(50.09, config.getStartLat());
        Assertions.assertEquals(19.94, config.getStartLong());
        Assertions.assertEquals(0.5, config.getPositionSpread());
        Assertions.assertEquals(5, config.getRefreshRateInSeconds());
        Assertions.assertEquals(5, config.getMaxNumberOfFiles());
    }
}