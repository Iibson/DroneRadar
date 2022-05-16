package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.NoConfigFileException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {
    private String filePath;
    private Integer droneNumber;
    private Double startLat;
    private Double startLong;
    private Double positionSpread;
    private Integer refreshRateInSeconds;
    private String createdFileTypes;
    private Integer maxNumberOfFiles;

    public static Configuration initConfiguration(String filename) throws IOException, NoConfigFileException {
        var file = Configuration.class
                .getClassLoader()
                .getResourceAsStream(filename);

        if (file == null)
            throw new NoConfigFileException("Config file is missing or has not been read");

        var fileContent = new String(file.readAllBytes());

        return new ObjectMapper()
                .readValue(fileContent, Configuration.class);
    }
}
