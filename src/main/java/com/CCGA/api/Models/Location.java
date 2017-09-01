package com.CCGA.api.Models;

import javax.persistence.Embeddable;

@Embeddable
public class Location {

    private Integer streetNum;
    private String street;
    private String city;
    private String state;
    private Integer zipCode;
    private Integer zipExp;
    private Float latitude;
    private Float longitude;

    public Location() {
    }

    public Location(Integer streetNum, String street, String city, String state, Integer zipCode, Integer zipExp, Float latitude, Float longitude) {
        this.streetNum = streetNum;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.zipExp = zipExp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(Integer streetNum) {
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getZipExp() {
        return zipExp;
    }

    public void setZipExp(int zipExp) {
        this.zipExp = zipExp;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
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

        if (streetNum != null ? !streetNum.equals(location.streetNum) : location.streetNum != null) return false;
        if (street != null ? !street.equals(location.street) : location.street != null) return false;
        if (city != null ? !city.equals(location.city) : location.city != null) return false;
        if (state != null ? !state.equals(location.state) : location.state != null) return false;
        if (zipCode != null ? !zipCode.equals(location.zipCode) : location.zipCode != null) return false;
        if (zipExp != null ? !zipExp.equals(location.zipExp) : location.zipExp != null) return false;
        if (latitude != null ? !latitude.equals(location.latitude) : location.latitude != null) return false;
        return longitude != null ? longitude.equals(location.longitude) : location.longitude == null;
    }

    @Override
    public int hashCode() {
        int result = streetNum != null ? streetNum.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (zipExp != null ? zipExp.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
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
