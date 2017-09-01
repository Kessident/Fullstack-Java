package com.CCGA.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "majors")
public class Major {

    @Id
    @GeneratedValue
    private int majorID;

    @Column(unique = true)
    private String name;

    @Column
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Major() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Major(String name) {
        this.name = name;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public int getMajorID() {
        return majorID;
    }

    public void setMajorID(int majorID) {
        this.majorID = majorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        Major major = (Major) o;

        return majorID == major.majorID;
    }

    @Override
    public int hashCode() {
        return majorID;
    }

    @Override
    public String toString() {
        return "Major{" +
            "majorID=" + majorID +
            ", name='" + name + '\'' +
            '}';
    }
}
