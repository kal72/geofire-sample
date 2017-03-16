package com.example.kal.geofire_sample;

/**
 * Created by kal on 3/15/2017.
 */

public class User {
    private String nama;
    private double latitude;
    private double longitude;

    public User(String nama, double latitude, double longitude) {
        this.nama = nama;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User(String nama) {
        this.nama = nama;
    }

    public User() {
    }

    public String getNama() {
        return nama;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "User{" +
                "nama='" + nama + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
