package com.CCGA.api.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "majors")
public class Major {

    @Id
    @GeneratedValue
    private int majorID;

    @Column
    private String name;

//    @Column
//    private List<Book> booksNeeded;

    public Major() {
    }

    public Major(String name, List<Book> booksNeeded) {
        this.name = name;
//        this.booksNeeded = booksNeeded;
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

//    public List<Book> getBooksNeeded() {
//        return booksNeeded;
//    }
//
//    public void setBooksNeeded(List<Book> booksNeeded) {
//        this.booksNeeded = booksNeeded;
//    }

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
//            ", booksNeeded=" + booksNeeded +
            '}';
    }
}
