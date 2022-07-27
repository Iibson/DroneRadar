package models.template.creationOptions;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DroneFileTemplateCreationOptions {
    private final Integer id;
    private final Double latitude;
    private final Double longitude;
    private final Integer heading;
    private final Integer speed;
    private final Date date;
    private final Integer altitude;
}
