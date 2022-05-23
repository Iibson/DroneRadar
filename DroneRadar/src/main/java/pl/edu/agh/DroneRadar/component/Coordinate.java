package pl.edu.agh.DroneRadar.component;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.DroneRadar.component.Direction;

@Embeddable
@Data
@NoArgsConstructor
public class Coordinate {
    private float latitude;
    private float longitude;
    private Direction direction;

    public Coordinate(float degreesLat, float minutesLat, float secondsLat, Direction directionLat, float degreesLon, float minutesLon, float secondsLon, Direction directionLon){
        setLatitude(degreesLat, minutesLat, secondsLat, directionLat);
        setLongitude(degreesLon, minutesLon, secondsLon, directionLon);
    }

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
