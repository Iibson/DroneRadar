package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.DroneRadar.model.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
