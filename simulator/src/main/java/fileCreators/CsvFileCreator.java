package fileCreators;

import fileCreators.interfaces.FileCreator;
import models.DroneFileTemplate;
import models.DroneFileValueDto;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public record CsvFileCreator(String dirPath) implements FileCreator {
    @Override
    public void createFile(DroneFileTemplate droneFileTemplate) {
        createFilesAsync(List.of(droneFileTemplate));
    }

    @Override
    public void createFilesAsync(List<DroneFileTemplate> droneFileTemplates) {
        var threads = droneFileTemplates.stream()
                .map(x -> {
                    var file = new File(dirPath + "\\" + x.getFileName() + ".csv");
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new Thread(() -> {
                        writeToCsv(file, x);
                    });
                })
                .collect(Collectors.toList());

        threads.forEach(x -> {
            x.start();
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeToCsv(File file, DroneFileTemplate droneFileTemplate) {
        var fieldValues = droneFileTemplate.getFileValues();

        var dataLines = List.of(
                fieldValues.stream().map(DroneFileValueDto::name).collect(Collectors.toList()),
                fieldValues.stream().map(DroneFileValueDto::value).collect(Collectors.toList())
        );


        try (PrintWriter pw = new PrintWriter(file)) {
            dataLines.stream()
                    .map(this::prepCsvLine)
                    .forEach(pw::println);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String prepCsvLine(List<String> lineElements) {
        return lineElements.stream()
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
