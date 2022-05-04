package fileCreators;

import models.DroneFile;

import java.util.List;

public interface FileCreator {
    void createFile(DroneFile droneFile);
    void createFilesAsync(List<DroneFile> droneFiles);
}
