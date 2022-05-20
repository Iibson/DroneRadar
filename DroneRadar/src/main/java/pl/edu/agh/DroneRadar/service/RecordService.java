package pl.edu.agh.DroneRadar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.DroneRadar.model.Record;
import pl.edu.agh.DroneRadar.repository.RecordRepository;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Record addRecord(Record record){
        return this.recordRepository.save(record);
    }

    public void removeRecordById(Long recordId){
        this.recordRepository.deleteById(recordId);
    }
}
