package models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.enums.FileFlag;

import java.util.*;

@Data
@Builder
public class DroneFileTemplate {
    //general
    private String fileName;
    @Builder.Default
    private String server = "Simulator";
    private Long date;
    private Date time;
    private FileFlag flag;
    private Integer id;
    private Integer idExt;
    //flight parameters
    private Double latitude;
    private Double longitude;
    private Integer heading;
    private Integer speed;
    private Integer altitude;
    //drone info
    @Builder.Default
    private String country = "";
    @Builder.Default
    private String operator = "";
    @Builder.Default
    private Integer identification = 2137;
    @Builder.Default
    private String identificationLabel = "";
    @Builder.Default
    private String model = "";
    @Builder.Default
    private String registrationNumber = "";
    @Builder.Default
    private String sign = "";
    @Builder.Default
    private String type = "";
    @Builder.Default
    private Integer fuel = 2137;
    //sensor info
    @Builder.Default
    private String signal = "";
    @Builder.Default
    private Integer frequency = 2137;
    @Builder.Default
    private String sensorLat = "";
    @Builder.Default
    private String sensorLon = "";
    @Builder.Default
    private String sensorLabel = "";
    //notes
    @Builder.Default
    private String notes = "";
    @Builder.Default
    private String ext1 = "";
    @Builder.Default
    private String ext2 = "";
    @Builder.Default
    private String ext3 = "";
    @Builder.Default
    private String ext4 = "";
    @Builder.Default
    private String ext5 = "";
    @Builder.Default
    private String ext6 = "";

    public List<DroneFileValueDto> getFileValues() {

        var res = new ArrayList<DroneFileValueDto>();

        res.add(new DroneFileValueDto("Filename", this.fileName));
        res.add(new DroneFileValueDto("Server", this.server));
        res.add(new DroneFileValueDto("Date", this.date.toString()));
        res.add(new DroneFileValueDto("Time", this.time.toString()));
        res.add(new DroneFileValueDto("Flag", this.flag.toString()));
        res.add(new DroneFileValueDto("Id", this.id.toString()));
        res.add(new DroneFileValueDto("Latitude", this.latitude.toString()));
        res.add(new DroneFileValueDto("Longitude", this.longitude.toString()));
        res.add(new DroneFileValueDto("Heading", this.heading.toString()));
        res.add(new DroneFileValueDto("Speed", this.speed.toString()));
        res.add(new DroneFileValueDto("Altitude", this.altitude.toString()));
        res.add(new DroneFileValueDto("Country", this.country));
        res.add(new DroneFileValueDto("Operator", this.operator));
        res.add(new DroneFileValueDto("Identification", this.identification.toString()));
        res.add(new DroneFileValueDto("identificationLabel", this.identificationLabel));
        res.add(new DroneFileValueDto("Model", this.model));
        res.add(new DroneFileValueDto("RegistrationNumber", this.registrationNumber));
        res.add(new DroneFileValueDto("Sign", this.sign));
        res.add(new DroneFileValueDto("Type", this.type));
        res.add(new DroneFileValueDto("Fuel", this.fuel.toString()));
        res.add(new DroneFileValueDto("Signal", this.signal));
        res.add(new DroneFileValueDto("Frequency", this.frequency.toString()));
        res.add(new DroneFileValueDto("SensorLat", this.sensorLat));
        res.add(new DroneFileValueDto("SensorLon", this.sensorLon));
        res.add(new DroneFileValueDto("SensorLabel", this.sensorLabel));
        res.add(new DroneFileValueDto("Notes", this.notes));
        res.add(new DroneFileValueDto("Ext1", this.ext1));
        res.add(new DroneFileValueDto("Ext2", this.ext2));
        res.add(new DroneFileValueDto("Ext3", this.ext3));
        res.add(new DroneFileValueDto("Ext4", this.ext4));
        res.add(new DroneFileValueDto("Ext5", this.ext5));
        res.add(new DroneFileValueDto("Ext6", this.ext6));

        return res;
    }
}
