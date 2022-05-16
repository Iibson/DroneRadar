import exceptions.NoConfigFileException;
import exceptions.WrongFileCreatorType;
import fileCreators.factories.FileCreatorFactory;
import generators.DroneFileTemplatesGenerator;
import models.Configuration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws IOException, NoConfigFileException, InterruptedException, WrongFileCreatorType {
        var config = Configuration.initConfiguration("config.json");
        var droneFileGenerator = new DroneFileTemplatesGenerator(config);
        var fileCreator = FileCreatorFactory.getFileCreator(config.getCreatedFileTypes(), config.getFilePath());
        var timer = new Timer();

        timer.schedule(new TimerTask() {
            int iterations = 0;
            final int maxIterations = config.getMaxNumberOfFiles();

            @Override
            public void run() {
                if(this.iterations == this.maxIterations) cancel();
                var droneFiles = droneFileGenerator.updateDroneFiles();
                fileCreator.createFilesAsync(droneFiles);
                System.out.println("generating files");
                iterations++;
            }
        }, 0, 1000L * config.getRefreshRateInSeconds());
    }
}
