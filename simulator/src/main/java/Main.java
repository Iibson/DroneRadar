import exceptions.NoConfigFileException;
import exceptions.WrongFileCreatorType;
import generators.DroneFileTemplatesGenerator;
import models.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class Main {
    public static void main(String[] args) throws IOException, NoConfigFileException, InterruptedException, WrongFileCreatorType {
        Thread.sleep(6000);


        var config = Configuration.initConfiguration("config.json");
        var droneFileGenerator = new DroneFileTemplatesGenerator(config);

        var filePath = config.getFilePath();
        if(args.length > 0){
            filePath = args[0];
        }

        FileUtils.cleanDirectory(new File(filePath));

        System.out.println("File path: " + filePath);

        try {
            droneFileGenerator.exec();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
