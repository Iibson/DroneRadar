package pl.edu.agh.DroneRadar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.model.BasicRecordData;
import pl.edu.agh.DroneRadar.repository.BasicRecordDataRepository;

@Service
public class BasicRecordDataService {
    private final BasicRecordDataRepository basicRecordDataRepository;

    public BasicRecordDataService(BasicRecordDataRepository basicRecordDataRepository) {
        this.basicRecordDataRepository = basicRecordDataRepository;
    }

    public BasicRecordData addBasicRecordData(BasicRecordData basicRecordData){
        return this.basicRecordDataRepository.save(basicRecordData);
    }

    public void removeBasicRecordDataById(Long basicRecordDataId){
        this.basicRecordDataRepository.deleteById(basicRecordDataId);
    }
}
