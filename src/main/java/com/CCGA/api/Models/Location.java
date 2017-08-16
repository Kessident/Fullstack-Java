package com.CCGA.api.Models;

import javax.persistence.Embeddable;

@Embeddable
public class Location {

    private String streetNum;
    private String street;
    private String city;
    private String state;
    private int zipCode;
    private int zipExp;
    private float latitude;
    private float longitude;

    public Location() {
    }

    public Location(String streetNum, String street, String city, String state, int zipCode, int zipExp, float latitude, float longitude) {
        this.streetNum = streetNum;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.zipExp = zipExp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getZipExp() {
        return zipExp;
    }

    public void setZipExp(int zipExp) {
        this.zipExp = zipExp;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (zipCode != location.zipCode) return false;
        if (zipExp != location.zipExp) return false;
        if (Float.compare(location.latitude, latitude) != 0) return false;
        if (Float.compare(location.longitude, longitude) != 0) return false;
        if (streetNum != null ? !streetNum.equals(location.streetNum) : location.streetNum != null) return false;
        if (street != null ? !street.equals(location.street) : location.street != null) return false;
        if (city != null ? !city.equals(location.city) : location.city != null) return false;
        return state != null ? state.equals(location.state) : location.state == null;
    }

    @Override
    public int hashCode() {
        int result = streetNum != null ? streetNum.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + zipCode;
        result = 31 * result + zipExp;
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
            "streetNum='" + streetNum + '\'' +
            ", street='" + street + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", zipCode=" + zipCode +
            ", zipExp=" + zipExp +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}
