package com.CCGA.api.Models;

import javax.persistence.*;

@Entity
@Table(name = "majors")
public class Major {

    @Id
    @GeneratedValue
    private int majorID;

    @Column
    private String name;

    public Major() {
    }

    public Major(String name) {
        this.name = name;
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
