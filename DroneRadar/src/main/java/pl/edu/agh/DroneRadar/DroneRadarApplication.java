package pl.edu.agh.DroneRadar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableJpaRepositories("pl.edu.agh.DroneRadar.repository")
public class DroneRadarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneRadarApplication.class, args);
	}

}
