package pl.edu.agh.DroneRadar.systemCache.models;

import lombok.Builder;
import lombok.Getter;
import pl.edu.agh.DroneRadar.dto.FiltersDto;

import java.util.Locale;

@Builder
@Getter
public class DroneCacheEntry{
    public float latitude;
    public float longitude;
    public float heading;
    public String id;
    public String idExt;
    public String country;
    public String type;
    public String model;
    public String registrationNumber;
    public String fuelState;
    public String signal;
    public String frequency;
    public String marking;

    public boolean doesMeetFilters(FiltersDto filters){
        return filters.getNonBlankFields().stream().allMatch(field -> {
                try {
                    var entryField = DroneCacheEntry.class.getField(field.getName()).toString().toLowerCase(Locale.ROOT);
                    var filtersField = field.get(filters).toString().toLowerCase(Locale.ROOT);
                    return entryField.contains(filtersField);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
                return false;
        });
    }
}