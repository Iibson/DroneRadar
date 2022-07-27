package generators;

import exceptions.WrongFileCreatorType;
import fileCreators.factories.FileCreatorFactory;
import lombok.Getter;
import models.configuration.Configuration;
import models.droneState.DroneState;
import models.template.DroneFileTemplate;
import models.template.creationOptions.DroneFileTemplateCreationOptions;
import models.template.generators.DroneFileTemplateGenerator;
import org.javatuples.Pair;

import java.util.Date;
import java.util.Random;
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
            var isRandom = i >= configuration.getNumberOfPreGeneratedDrones() || i >= DroneFileTemplateGenerator.lengthOfPreGeneratedDrones;
            var template = initTemplate(generateOptions(configuration, i), isRandom);
            states[i] = new DroneState(creator, configuration, i, template);
        }

        return states;
    }

    public void exec() throws InterruptedException {
        var semaphore = new Semaphore(configuration.getMaxNumberOfDronesGeneratedAtOnce());
        for (int i = 0; i < configuration.getDroneNumber(); i++) {
            states[i].executeDroneLoop(semaphore);
        }
    }

    private DroneFileTemplate initTemplate(DroneFileTemplateCreationOptions options, boolean isRandom) {
        return isRandom ? DroneFileTemplateGenerator.randomGeneration(options) : DroneFileTemplateGenerator.preGeneratedGeneration(options);
    }

    private DroneFileTemplateCreationOptions generateOptions(Configuration configuration, Integer id) {
        var latLong = initStartingLatLong(configuration);
        return DroneFileTemplateCreationOptions.builder()
                .heading(initHeading())
                .speed(initSpeed())
                .id(id)
                .altitude(initAltitude())
                .latitude(latLong.getValue0())
                .longitude(latLong.getValue1())
                .date(new Date())
                .build();
    }

    private Integer initHeading() {
        return Math.abs(new Random().nextInt()) % 361;
    }

    private Integer initSpeed() {
        return Math.abs(new Random().nextInt()) % 15 + 15;
    }

    private Integer initAltitude() {
        return Math.abs(new Random().nextInt() % 200 + 100);
    }

    private static Pair<Double, Double> initStartingLatLong(Configuration configuration) {
        var r = new Random().nextDouble() * configuration.getPositionSpread();
        return new Pair<>(configuration.getStartLat() + r / 2, configuration.getStartLong() + r);
    }
}