package com.droneradar.droneradarbackend.model;

public class MapObjectInfoDto {
    public int objectId;
    public double lat;
    public double lon;
    public String basicInfoString;

    public MapObjectInfoDto(int objectId, double lat, double lon, String basicInfoString) {
        this.objectId = objectId;
        this.lat = lat;
        this.lon = lon;
        this.basicInfoString = basicInfoString;
    }

}
