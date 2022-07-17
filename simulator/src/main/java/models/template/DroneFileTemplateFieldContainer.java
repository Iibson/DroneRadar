package models.template;

import java.lang.reflect.Field;
import java.util.Arrays;

class DroneFileTemplateFieldContainer {
    private static final Field[] fields = DroneFileTemplate.class
            .getDeclaredFields();
    private static final String[] fieldNames = Arrays.stream(fields)
            .map(Field::getName)
            .toList()
            .toArray(new String[fields.length]);

    static String[] getDroneFileTemplateFieldNames() {
        return fieldNames;
    }

    static String[] getDroneFileTemplateFieldValues(DroneFileTemplate template) throws IllegalAccessException {
        var res = new String[33];
        for (int i = 0; i < fields.length; i++){
            fields[i].setAccessible(true);
            res[i] = fields[i].get(template).toString();
        }
        return res;
    }
}
