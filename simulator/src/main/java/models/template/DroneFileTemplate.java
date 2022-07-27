package models.template;

import lombok.Builder;
import lombok.Data;
import models.template.enums.FileFlag;

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
    public String[] getDroneFileTemplateFieldNames() {
        return DroneFileTemplateFieldContainer.getDroneFileTemplateFieldNames();
    }

    public String[] getDroneFileTemplateFieldValues() throws IllegalAccessException {
        return DroneFileTemplateFieldContainer.getDroneFileTemplateFieldValues(this);
    }
}
