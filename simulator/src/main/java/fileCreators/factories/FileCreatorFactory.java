package fileCreators.factories;

import exceptions.WrongFileCreatorType;
import fileCreators.CsvFileCreator;
import fileCreators.interfaces.FileCreator;
import jdk.jshell.spi.ExecutionControl;

import java.util.Locale;

public class FileCreatorFactory {

    public static FileCreator getFileCreator(String type, String path) throws WrongFileCreatorType {
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "csv" -> new CsvFileCreator(path);
            default -> throw new WrongFileCreatorType("No such FileCreator type: " + type);
        };
    }
}
