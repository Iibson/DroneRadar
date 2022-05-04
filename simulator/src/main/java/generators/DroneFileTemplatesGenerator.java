package generators;

import models.Configuration;
import models.DroneFileTemplate;
import models.enums.FileFlag;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DroneFileGenerator {
    private final Configuration configuration;
    private final List<DroneFileTemplate> droneFileTemplates;
    private final Random random;

    public DroneFileGenerator(Configuration configuration) {
        this.random = new Random();
        this.configuration = configuration;
        this.droneFileTemplates = initDroneFiles(configuration);
    }

    public List<DroneFileTemplate> updateDroneFiles() {
        var date = new Date();
        droneFileTemplates.forEach(drone -> {
            var distInKm =  (double) drone.getSpeed() / 3600f * configuration.getRefreshRate();
            var angleInRad = drone.getHeading() * Math.PI / 180;
            var updateLat = Math.cos(angleInRad) * distInKm;
            var updateLong = Math.sin(angleInRad) * distInKm;
            drone.setLatitude(drone.getLatitude() + updateLat);
            drone.setLongitude(drone.getLongitude() + updateLong);
            drone.setHeading(createNewHeading(drone.getHeading()));
            drone.setFileName("Simulator_" + drone.getId() + "_" + date.getTime());
            drone.setDate(date.getTime());
            drone.setTime(date);
        });
        return droneFileTemplates;
    }

    private List<DroneFileTemplate> initDroneFiles(Configuration configuration) {
        var droneFiles = new ArrayList<DroneFileTemplate>();

        for(int i = 0; i < configuration.getDroneNumber(); i++) {
            droneFiles.add(buildDefaultDroneFile(configuration, i));
        }

        return droneFiles;
    }

    private DroneFileTemplate buildDefaultDroneFile(Configuration configuration, Integer id) {
        var date = new Date();
        var position = initStartingLatLong(configuration.getStartLat(), configuration.getStartLong(), configuration.getPositionSpread());
        return DroneFileTemplate.builder()
                .fileName("Simulator_" + id + "_" + date.getTime())
                .date(date.getTime())
                .time(date)
                .id(id)
                .idExt(id)
                .flag(FileFlag.BEG)
                .latitude(position.getValue0())
                .longitude(position.getValue1())
                .heading(initHeading())
                .speed(initSpeed())
                .altitude(100)
                .build();
    }

    private Pair<Double, Double> initStartingLatLong(Double latitude, Double longitude, Double spread) {
        var r = spread * random.nextDouble();
        return new Pair<>(latitude + r, longitude + r);
    }

    private Integer initHeading() {
        return Math.abs(random.nextInt()) % 361;
    }

    private Integer initSpeed() {
        return Math.abs(random.nextInt()) % 400;
    }

    //TODO improve this shit
    private Integer createNewHeading(Integer currentHeading) {
        if(random.nextInt() % 10 < 9) return currentHeading;
        var newValue = random.nextInt() % 15 + currentHeading;
        if(newValue > 360) newValue = newValue % 360;
        else if(newValue < 0) newValue = 360 + newValue;
        return newValue;
    }
}