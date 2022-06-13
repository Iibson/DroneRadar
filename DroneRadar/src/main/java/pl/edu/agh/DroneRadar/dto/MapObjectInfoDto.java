package pl.edu.agh.DroneRadar.dto;

public class MapObjectInfoDto {
    public String objectId;
    public double lat;
    public double lon;
    public float heading;
    public String basicInfoString;

    public MapObjectInfoDto(String objectId, double lat, double lon, String basicInfoString, float heading) {
        this.objectId = objectId;
        this.lat = lat;
        this.lon = lon;
        this.basicInfoString = basicInfoString;
        this.heading = heading;

    }

}
