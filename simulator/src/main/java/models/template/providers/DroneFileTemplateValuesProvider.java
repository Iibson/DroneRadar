package models.template.providers;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DroneFileTemplateValuesProvider {

    private static final String[] modelNames = new String[]{"DJI Mavic 3", "DJI FPV", "Parrot ANAFI", "DJI Mini SE", "Autel EVO"};
    private static final String[] countryNames = new String[]{"pl", "en", "fr"};
    private static final String[] types = new String[]{"FIXED-WING ", "MULTI-ROTOR", "INGLE ROTOR", "HYBRID VTOL"};
    private static final Set<String> userRegistrations = new HashSet<>();

    public static String randomModel() {
        return modelNames[Math.abs(new Random().nextInt()) % modelNames.length];
    }

    public static String randomCountry() {
        return countryNames[Math.abs(new Random().nextInt()) % countryNames.length];
    }

    public static String randomType() {
        return types[Math.abs(new Random().nextInt()) % types.length];
    }

    public static String randomRegistrationNumber() {
        Random r = new Random();
        var reg = randomString();
        while (userRegistrations.contains(reg)) {
            reg = randomString();
        }
        userRegistrations.add(reg);
        return reg;
    }

    public static Integer randomIdentification() {
        return Math.abs(new Random().nextInt()) % 15 + 1;
    }

    public static String randomSign() {
        return randomString();
    }

    public static Integer randomAltitude() {
        return Math.abs(new Random().nextInt()) % 500 + 100;
    }

    private static String randomString() {
        Random r = new Random();
        return (String.valueOf((char) (r.nextInt(26) + 'a')) +
                (char) (r.nextInt(26) + 'a') +
                (char) (r.nextInt(26) + 'a') +
                r.nextInt() % 999)
                .toUpperCase();
    }
}
