package generators;

import exceptions.WrongFileCreatorType;
import fileCreators.factories.FileCreatorFactory;
import lombok.Getter;
import models.configuration.Configuration;
import models.droneState.DroneState;

import java.util.concurrent.Semaphore;

public class DroneFileTemplatesGenerator {
    private final Configuration configuration;
    @Getter
    private final DroneState[] states;

    public DroneFileTemplatesGenerator(Configuration configuration) throws WrongFileCreatorType {
        this.configuration = configuration;
        this.states = initDroneStates(configuration);
    }

    private DroneState[] initDroneStates(Configuration configuration) throws WrongFileCreatorType {
        var states = new DroneState[configuration.getDroneNumber()];
        var creator = FileCreatorFactory.getFileCreator(configuration.getCreatedFileTypes(), configuration.getFilePath());

        for (int i = 0; i < configuration.getDroneNumber(); i++) {
            states[i] = new DroneState(creator, configuration, i);
        }

        return states;
    }

    public void exec() throws InterruptedException {
        var semaphore = new Semaphore(configuration.getMaxNumberOfDronesGeneratedAtOnce());
        for (int i = 0; i < configuration.getDroneNumber(); i++) {
            states[i].executeDroneLoop(semaphore);
        }
    }
}