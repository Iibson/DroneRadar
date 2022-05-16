package fileCreators.interfaces;

import models.DroneFileTemplate;

import java.util.List;

public interface FileCreator {
    void createFile(DroneFileTemplate droneFileTemplate);
    void createFilesAsync(List<DroneFileTemplate> droneFileTemplates);
}
