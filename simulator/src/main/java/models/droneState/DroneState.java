package models.droneState;

import fileCreators.interfaces.FileCreator;
import models.configuration.Configuration;
import models.template.DroneFileTemplate;
import models.template.creationOptions.DroneFileTemplateCreationOptions;
import models.template.enums.FileFlag;
import org.javatuples.Pair;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class DroneState {
    private final DroneFileTemplate drone;
    private final Integer id;
    private final Configuration configuration;
    private final FileCreator fileCreator;

    public DroneState(FileCreator fileCreator, Configuration configuration, Integer id) {
        this.id = id;
        this.configuration = configuration;
        this.drone = defaultDroneFileTemplate();
        this.fileCreator = fileCreator;
    }
    public void executeDroneLoop(Semaphore semaphore) {
        var gotSem = new AtomicBoolean(true);
        try {
            var thread = new Thread(() -> {
                try {
                    semaphore.acquire(1);
                    droneLoop();
                } catch (InterruptedException e) {
                    gotSem.set(false);
                } finally {
                    if(gotSem.get()) semaphore.release(1);
                }

            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void droneLoop() {
        var iterations = 0;
        var continueTimer = true;
        while(true) {
            continueTimer = droneStateCycle(iterations);
            iterations++;
            if (!continueTimer) {
                break;
            }
            try {
                Thread.sleep(1000L * configuration.getRefreshRateInSeconds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean droneStateCycle(int iteration) {
        var continueFlight = continueFlight(iteration);
        var date = new Date();
        var distInKm = (double) drone.getSpeed() / 3600f * configuration.getRefreshRateInSeconds();
        var angleInRad = drone.getHeading() * Math.PI / 180;
        var updateLat = Math.cos(angleInRad) * distInKm;
        var updateLong = Math.sin(angleInRad) * distInKm;
        drone.setLatitude(drone.getLatitude() + updateLat);
        drone.setLongitude(drone.getLongitude() + updateLong);
        drone.setHeading(createNewHeading());
        drone.setFileName(createFileName());
        drone.setDate(date.getTime());
        drone.setTime(date);
        if (iteration > 0)
            drone.setFlag(continueFlight ? FileFlag.UPD : FileFlag.DROP);
        fileCreator.createFile(drone);
        return continueFlight;
    }

    private DroneFileTemplate defaultDroneFileTemplate() {
        var latLong = initStartingLatLong();
        var options = DroneFileTemplateCreationOptions.builder()
                .heading(initHeading())
                .speed(initSpeed())
                .id(id)
                .latitude(latLong.getValue0())
                .longitude(latLong.getValue1())
                .date(new Date())
                .build();
        return DroneFileTemplate.defaultGeneration(options);
    }

    private Integer initHeading() {
        return Math.abs(new Random().nextInt()) % 361;
    }

    private Integer initSpeed() {
        return Math.abs(new Random().nextInt()) % 15 + 15;
    }

    private String createFileName() {
        return "Simulator_" + id + "_" + new Date().getTime();
    }

    private Pair<Double, Double> initStartingLatLong() {
        var r = new Random().nextDouble() * configuration.getPositionSpread();
        return new Pair<>(configuration.getStartLat() + r, configuration.getStartLong() + r);
    }

    private Integer createNewHeading() {
        var currentHeading = drone.getHeading();
        var random = new Random();
        if (random.nextInt() % 10 < 4) return currentHeading;
        var newValue = random.nextInt() % 15 + 15 + currentHeading;
        if (newValue > 360) newValue = newValue % 360;
        else if (newValue < 0) newValue = 360 + newValue;
        return newValue;
    }

    private boolean continueFlight(int iteration) {
        return new Random().nextInt() % 45 != 3 && iteration < configuration.getMaxNumberOfFiles() - 1;
    }
}