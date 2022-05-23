package pl.edu.agh.DroneRadar.parser.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CSVFlightData {

    @CsvBindByName(column = "Filename")
    private String fileName;

    @CsvBindByName(column = "Server")
    private String server;

    @CsvBindByName(column = "Date")
    private String date;

    @CsvBindByName(column = "Time")
    private String time;

    @CsvBindByName(column = "Flag")
    private String flag;

    @CsvBindByName(column = "Id")
    private String id;

    @CsvBindByName(column = "Latitude")
    private String latitude;

    @CsvBindByName(column = "Longitude")
    private String longitude;

    @CsvBindByName(column = "Heading")
    private String heading;

    @CsvBindByName(column = "Speed")
    private String speed;

    @CsvBindByName(column = "Altitude")
    private String altitude;

    @CsvBindByName(column = "Country")
    private String country;

    @CsvBindByName(column = "Operator")
    private String operator;

    @CsvBindByName(column = "Identification")
    private String droneIdentification;

    @CsvBindByName(column = "IdentificationLabel")
    private String identificationLabel;

    @CsvBindByName(column = "Model")
    private String model;

    @CsvBindByName(column = "RegistrationNumber")
    private String registrationNumber;

    @CsvBindByName(column = "Sign")
    private String sign;

    @CsvBindByName(column = "Type")
    private String type;

    @CsvBindByName(column = "Fuel")
    private String fuel;

    @CsvBindByName(column = "Signal")
    private String signal;

    @CsvBindByName(column = "Frequency")
    private String frequency;

    @CsvBindByName(column = "SensorLat")
    private String sensorLat;

    @CsvBindByName(column = "SensorLon")
    private String sensorLon;

    @CsvBindByName(column = "SensorLabel")
    private String sensorLabel;

    @CsvBindByName(column = "Notes")
    private String notes;

    @CsvBindByName(column = "Ext1")
    private String ext1;

    @CsvBindByName(column = "Ext2")
    private String ext2;

    @CsvBindByName(column = "Ext3")
    private String ext3;

    @CsvBindByName(column = "Ext4")
    private String ext4;

    @CsvBindByName(column = "Ext5")
    private String ext5;

    @CsvBindByName(column = "Ext6")
    private String ext6;
}
