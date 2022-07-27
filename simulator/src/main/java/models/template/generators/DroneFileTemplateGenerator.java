package models.template.generators;

import models.template.DroneFileTemplate;
import models.template.creationOptions.DroneFileTemplateCreationOptions;
import models.template.enums.FileFlag;
import org.javatuples.Sextet;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class DroneFileTemplateGenerator {
    private static final String[] modelNames = new String[]{"DJI Mavic 3", "DJI FPV", "Parrot ANAFI", "DJI Mini SE", "Autel EVO"};
    private static final String[] countryNames = new String[]{"pl", "en", "fr"};
    private static final String[] types = new String[]{"FIXED-WING", "MULTI-ROTOR", "INGLE ROTOR", "HYBRID VTOL"};
    private static final List<Sextet<String, String, String, String, Integer, String>> preGeneratedValues = List.of(
            new Sextet<>("en", "Dron DJI Mavic 3", "MULTI-ROTOR", "ABCD123", 1, "ABCD123"),
            new Sextet<>("pl", "Dron DJI Mavic 3", "INGLE ROTOR", "BCDE456", 2, "BCDE456"),
            new Sextet<>("pl", "DJI FPV", "INGLE ROTOR", "CDEF567", 3, "CDEF567"),
            new Sextet<>("fr", "Dron DJI Mavic 3", "MULTI-ROTOR", "GRGR2137", 4, "GRGR2137"),
            new Sextet<>("fr", "Autel EVO", "FIXED-WING", "123G3F", 5, "123G3F"),
            new Sextet<>("fr", "Parrot ANAFI", "HYBRID VTOL", "PePo12", 6, "PePo12"),
            new Sextet<>("pl", "DJI FPV", "HYBRID VTOL", "HGX5T", 7, "HGX5T"),
            new Sextet<>("en", "Parrot ANAFI", "FIXED-WING", "XD121XD", 8, "XD121XD"),
            new Sextet<>("en", "Autel EVO", "FIXED-WING", "OWOOWO", 9, "OWOOWO"),
            new Sextet<>("en", "Parrot ANAFI", "HYBRID VTOL", "PLFREN", 10, "PLFREN")
    );

    private static final Set<String> userRegistrations = preGeneratedValues.stream()
            .map(Sextet::getValue5)
            .collect(Collectors.toSet());
    public final static Integer lengthOfPreGeneratedDrones = preGeneratedValues.size();

    public static String randomModel() {
        return modelNames[Math.abs(new Random().nextInt()) % modelNames.length];
    }

    private static String randomCountry() {
        return countryNames[Math.abs(new Random().nextInt()) % countryNames.length];
    }

    private static String randomType() {
        return types[Math.abs(new Random().nextInt()) % types.length];
    }

    private static String randomRegistrationNumber() {
        var reg = randomString();
        while (userRegistrations.contains(reg)) {
            reg = randomString();
        }
        userRegistrations.add(reg);
        return reg;
    }

    private static Integer randomIdentification() {
        return Math.abs(new Random().nextInt()) % 15 + 1;
    }

    private static String randomSign() {
        return randomString();
    }

    private static String randomString() {
        Random r = new Random();
        return (String.valueOf((char) (r.nextInt(26) + 'a')) +
                (char) (r.nextInt(26) + 'a') +
                (char) (r.nextInt(26) + 'a') +
                r.nextInt() % 999)
                .toUpperCase();
    }

    public static DroneFileTemplate randomGeneration(DroneFileTemplateCreationOptions options) {
        return getBuilder(options)
                .country(randomCountry())
                .model(randomModel())
                .type(randomType())
                .sign(randomSign())
                .registrationNumber(randomRegistrationNumber())
                .identification(randomIdentification())
                .build();
    }

    public static DroneFileTemplate preGeneratedGeneration(DroneFileTemplateCreationOptions options) {
        var values = preGeneratedValues.get(options.getId());
        return getBuilder(options)
                .country(values.getValue0())
                .model(values.getValue1())
                .type(values.getValue2())
                .sign(values.getValue3())
                .registrationNumber(values.getValue5())
                .identification(values.getValue4())
                .build();
    }

    private static DroneFileTemplate.DroneFileTemplateBuilder getBuilder(DroneFileTemplateCreationOptions options) {
        return DroneFileTemplate.builder()
                .fileName("Simulator_" + options.getId() + "_" + options.getDate().getTime())
                .date(options.getDate().getTime())
                .time(options.getDate())
                .id(options.getId())
                .idExt(options.getId())
                .identification(options.getId())
                .flag(FileFlag.BEG)
                .latitude(options.getLatitude())
                .registrationNumber(options.getId().toString())
                .longitude(options.getLongitude())
                .heading(options.getHeading())
                .speed(options.getSpeed())
                .altitude(options.getAltitude());
    }
}
