package com.example.googlemaplocations;

/**
 * Created by LouisAudibert on 21/04/2017.
 */

class LocationModel {
    private double latitude;
    private double longitude;
    private int zoomLvl;

    public LocationModel(double latitude, double longitude, int zoomLvl) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoomLvl = zoomLvl;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getZoomLvl() {
        return zoomLvl;
    }

    public void setZoomLvl(int zoomLvl) {
        this.zoomLvl = zoomLvl;
    }
}
