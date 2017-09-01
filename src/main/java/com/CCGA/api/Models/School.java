package com.CCGA.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue
    private int schoolID;

    @Column(unique = true)
    private String name;

    @Column
    private Location location;

    @ManyToMany
    private List<Major> majorsOffered;

    @Column @JsonIgnore
    private LocalDateTime createdAt;

    @Column @JsonIgnore
    private LocalDateTime updatedAt;

    public School() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public School(String name) {
        this.name = name;
        majorsOffered = new ArrayList<>();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public School(String name, Location location, List<Major> majorsOffered) {
        this.name = name;
        this.location = location;
        this.majorsOffered = majorsOffered;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Major> getMajorsOffered() {
        return majorsOffered;
    }

    public void setMajorsOffered(List<Major> majorsOffered) {
        this.majorsOffered = majorsOffered;
    }

    public void addMajor(Major major){
        Objects.requireNonNull(major);
        majorsOffered.add(major);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School school = (School) o;

        return schoolID == school.schoolID;
    }

    @Override
    public int hashCode() {
        return schoolID;
    }

    @Override
    public String toString() {
        return "School{" +
            "schoolID=" + schoolID +
            ", name='" + name + '\'' +
            ", location=" + location +
            ", majorsOffered=" + majorsOffered +
            '}';
    }
}