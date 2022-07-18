package fileCreators;

import fileCreators.interfaces.FileCreator;
import models.template.DroneFileTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public record CsvFileCreator(String dirPath) implements FileCreator {
    @Override
    public void createFile(DroneFileTemplate droneFileTemplate) {
        var file = new File(dirPath + "\\" + droneFileTemplate.getFileName() + ".csv");
        try {
            file.createNewFile();
            writeToCsv(file, droneFileTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToCsv(File file, DroneFileTemplate droneFileTemplate) throws IllegalAccessException {

        try(PrintWriter pw = new PrintWriter(file)) {
            pw.println(prepCsvLine(droneFileTemplate.getDroneFileTemplateFieldNames()));
            pw.println(prepCsvLine(droneFileTemplate.getDroneFileTemplateFieldValues()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String prepCsvLine(String[] lineElements) {
        var sb = new StringBuilder();
        for (String lineElement : lineElements) {
            sb.append(escapeSpecialCharacters(lineElement)).append(',');
        }
        return sb.toString();
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
