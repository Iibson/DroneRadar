package pl.edu.agh.DroneRadar.dto;

import lombok.Getter;
import pl.edu.agh.DroneRadar.enums.FilterCompareType;

import java.util.List;
@Getter
public class FilterDto {
    private String propertyName;
    private FilterCompareType compareType;
    private List<String> compareValues;
}
