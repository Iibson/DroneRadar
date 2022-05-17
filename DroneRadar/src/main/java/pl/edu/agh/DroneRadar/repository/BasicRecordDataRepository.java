package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.DroneRadar.model.BasicRecordData;

public interface BasicRecordDataRepository extends JpaRepository<BasicRecordData, Long> {
}
