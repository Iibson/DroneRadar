import exceptions.NoConfigFileException;
import exceptions.WrongFileCreatorType;
import fileCreators.factories.FileCreatorFactory;
import generators.DroneFileTemplatesGenerator;
import models.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import org.apache.commons.io.FileUtils;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws IOException, NoConfigFileException, InterruptedException, WrongFileCreatorType {

        var config = Configuration.initConfiguration("config.json");
        var droneFileGenerator = new DroneFileTemplatesGenerator(config);
        FileUtils.cleanDirectory(new File(config.getFilePath()));

        var filePath = config.getFilePath();
        if(args.length > 0){
            filePath = args[0];
        }

        System.out.println(filePath);

        var fileCreator = FileCreatorFactory.getFileCreator(config.getCreatedFileTypes(), filePath);
        var timer = new Timer();

        timer.schedule(new TimerTask() {
            int iterations = 0;
            final int maxIterations = config.getMaxNumberOfFiles();

            @Override
            public void run() {
                if(this.iterations == this.maxIterations) {
                    System.out.println("stopping");
                    cancel();
                } else {
                    var droneFiles = droneFileGenerator.updateDroneFiles();
                    fileCreator.createFilesAsync(droneFiles);
                    System.out.println("generating files");
                    iterations++;
                }

            }
        }, 0, 1000L * config.getRefreshRateInSeconds());
    }
}
