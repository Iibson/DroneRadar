package pl.edu.agh.DroneRadar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.DroneRadar.model.BasicRecordData;

@Repository
public interface BasicRecordDataRepository extends JpaRepository<BasicRecordData, Long> {
}
