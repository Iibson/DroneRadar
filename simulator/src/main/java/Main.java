import exceptions.NoConfigFileException;
import exceptions.WrongFileCreatorType;
import generators.DroneFileTemplatesGenerator;
import models.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class Main {
    public static void main(String[] args) throws IOException, NoConfigFileException, InterruptedException, WrongFileCreatorType {

        var config = Configuration.initConfiguration("config.json");
        var droneFileGenerator = new DroneFileTemplatesGenerator(config);
        FileUtils.cleanDirectory(new File(config.getFilePath()));

        var filePath = config.getFilePath();
        if(args.length > 0){
            filePath = args[0];
        }

        System.out.println("File path: " + filePath);

        try {
            droneFileGenerator.exec();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
