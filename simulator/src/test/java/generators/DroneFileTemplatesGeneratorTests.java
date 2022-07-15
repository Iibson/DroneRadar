//package generators;
//
//import exceptions.NoConfigFileException;
//import models.configuration.Configuration;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;
//
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//public class DroneFileTemplatesGeneratorTests {
//
//    @Test
//    public void testTemplateGeneration() throws IOException, NoConfigFileException {
//
//        var config = Configuration.initConfiguration("config-test.json");
//
//        var generator = new DroneFileTemplatesGenerator(config);
//
//        var templates = generator.getDroneFileTemplates();
//
//        Assertions.assertEquals(templates.size(), config.getDroneNumber());
//
//        templates.forEach(x -> {
//            Assertions.assertTrue(x.getLatitude() > Math.abs(config.getPositionSpread() - config.getStartLat()));
//            Assertions.assertTrue(x.getLatitude() < Math.abs(config.getPositionSpread() + config.getStartLat()));
//            Assertions.assertTrue(x.getLongitude() > Math.abs(config.getPositionSpread() - config.getStartLong()));
//            Assertions.assertTrue(x.getLongitude() < Math.abs(config.getPositionSpread() + config.getStartLong()));
//        });
//    }
//
//    @Test
//    public void testTemplateUpdate() throws IOException, NoConfigFileException, InterruptedException {
//        var config = Configuration.initConfiguration("config-test.json");
//
//        var generator = new DroneFileTemplatesGenerator(config);
//
//        var templates = generator.getDroneFileTemplates();
//
//        //this writes oldFileName to new reference, its not overwritten on updateDroneFiles();
//        var oldNames = templates.stream()
//                .map(x -> new String(x.getFileName()))
//                .collect(Collectors.toList());
//
//        Thread.sleep(1);
//
//        var updatedTemplates = generator.updateDroneFiles();
//
//        Assertions.assertEquals(templates, updatedTemplates);
//
//        for (int i = 0; i < oldNames.size(); i++) {
//            Assertions.assertNotEquals(oldNames.get(i), updatedTemplates.get(i).getFileName());
//        }
//    }
//}
