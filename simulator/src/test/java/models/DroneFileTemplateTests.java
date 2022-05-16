package models;

import exceptions.NoConfigFileException;
import models.enums.FileFlag;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;

import java.util.Date;
import java.util.List;

public class DroneFileTemplateTests {

    @Test
    public void testOrderOfFileValues() throws IOException, NoConfigFileException {

        //given
        var date = new Date();
        var template =  DroneFileTemplate.builder()
                .fileName("Simulator_" + 1 + "_" + date.getTime())
                .date(date.getTime())
                .time(date)
                .id(1)
                .idExt(1)
                .flag(FileFlag.BEG)
                .latitude(1d)
                .longitude(1d)
                .heading(1)
                .speed(1)
                .altitude(100)
                .build();

        var list = List.of("Filename",
                "Server",
                "Date",
                "Time",
                "Flag",
                "Id", "Latitude",
                "Longitude",
                "Heading",
                "Speed",
                "Altitude",
                "Country",
                "Operator",
                "Identification",
                "Model",
                "RegistrationNumber",
                "Sign",
                "Type",
                "Fuel",
                "Signal",
                "Frequency",
                "SensorLat",
                "SensorLon",
                "SensorLabel",
                "Notes",
                "Ext1",
                "Ext2",
                "Ext3",
                "Ext4",
                "Ext5",
                "Ext6");

        var fields = template.getFileValues();

        Assertions.assertEquals(fields.size(), list.size());

        for(int i = 0; i < fields.size(); i++) {
            Assertions.assertEquals(list.get(i), fields.get(i).name());
        }

    }
}
