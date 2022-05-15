package pl.edu.agh.DroneRadar.component;

import jakarta.persistence.Embeddable;
import lombok.Data;
import pl.edu.agh.DroneRadar.component.Direction;

@Data
@Embeddable
public class Coordinate {
    float latitude;
    float longitude;
    Direction direction;

    public void setLatitude(float degrees, float minutes, float seconds, Direction direction) {
        if(!direction.isLatitudinal())
            throw new IllegalArgumentException("Given direction is not valid latitudinal direction!");
        this.latitude = degrees + minutes / 60 + seconds + 3600;
        this.direction = direction;
    }

    public void setLongitude(float degrees, float minutes, float seconds, Direction direction) {
        if(!direction.isLongitudinal())
            throw new IllegalArgumentException("Given direction is not valid longitudinal direction!");
        this.longitude = degrees + minutes / 60 + seconds + 3600;
        this.direction = direction;
    }
}
