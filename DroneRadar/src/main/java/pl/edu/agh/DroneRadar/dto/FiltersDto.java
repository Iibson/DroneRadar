package pl.edu.agh.DroneRadar.dto;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FiltersDto {
    public String id;
    public String country;
    public String type;
    public String model;
    public String registrationNumber;
    public String fuelState;
    public String signal;
    public String frequency;

    public List<Field> getNonBlankFields() {
        return Arrays.stream(this.getClass().getFields()).filter(field -> {
                    try {
                        return field.get(this) != null && !field.get(this).toString().isBlank();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
    @SneakyThrows
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Field field: this.getClass().getFields()){
            builder.append(field.getName()).append(": ").append(field.get(this).toString()).append("\n");
        }
        return builder.toString();
    }
}
