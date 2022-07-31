package pl.edu.agh.DroneRadar.systemCache.models;

import lombok.Builder;
import lombok.Getter;
import pl.edu.agh.DroneRadar.dto.FilterDto;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Builder
@Getter
public class DroneCacheEntry {
    public float latitude;
    public float longitude;
    public float heading;
    public short identification;
    public String idExt;
    public String country;
    public String type;
    public String model;
    public String registrationNumber;
    public String fuelState;
    public String signal;
    public String frequency;
    public String marking;

    public boolean doesMeetFilters(List<FilterDto> filters) {
        return filters.stream().filter(x -> !x.getCompareValues().isEmpty()).allMatch(filter -> {
            try {
                var entryField = DroneCacheEntry.class.getField(filter.getPropertyName()).get(this).toString().toLowerCase(Locale.ROOT);
                switch (filter.getCompareType()) {
                    case CONTAINS -> {
                        var compareValue = filter.getCompareValues()
                                .stream()
                                .findFirst();
                        return compareValue.isPresent() && entryField.contains(compareValue.get().toLowerCase(Locale.ROOT));
                    }
                    case BETWEEN -> {
                        var compareValues = filter.getCompareValues()
                                .stream()
                                .map(Float::parseFloat)
                                .sorted()
                                .collect(Collectors.toList());
                        if(compareValues.size() != 2) return false;
                        var firstValue = compareValues.get(0);
                        var secondValue = compareValues.get(1);
                        var parsedEntryField = Float.parseFloat(entryField);

                        return parsedEntryField >= firstValue & parsedEntryField <= secondValue;
                    }
                    case HIGHER -> {
                        var compareValue = filter.getCompareValues().stream().findFirst();
                        return compareValue.isPresent() && Float.parseFloat(entryField) > Float.parseFloat(compareValue.get());
                    }
                    case LOWER -> {
                        var compareValue = filter.getCompareValues().stream().findFirst();
                        return compareValue.isPresent() && Float.parseFloat(entryField) < Float.parseFloat(compareValue.get());
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        });
    }
}