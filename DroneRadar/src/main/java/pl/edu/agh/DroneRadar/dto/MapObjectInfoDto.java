package pl.edu.agh.DroneRadar.dto;

public class MapObjectInfoDto {
    public short objectId;
    public double lat;
    public double lon;
    public int angle;
    public String basicInfoString;

    public MapObjectInfoDto(short objectId, double lat, double lon, String basicInfoString, int angle) {
        this.objectId = objectId;
        this.lat = lat;
        this.lon = lon;
        this.basicInfoString = basicInfoString;
        this.angle = angle;

    }

}
