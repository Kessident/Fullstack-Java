package com.CCGA.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int userID;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @JsonIgnore
    private String salt;

    @OneToOne(fetch = FetchType.LAZY)
    private Major major;

    @OneToOne(fetch = FetchType.LAZY)
    private School school;

    @Column
    private boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Book> booksOwned;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Listing> booksForSale;


    public User() {
    }

    public User(String name, String email, String password, String salt, Major major, School school, boolean isDeleted, List<Book> booksOwned, List<Listing> booksForSale) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.major = major;
        this.school = school;
        this.isDeleted = isDeleted;
        this.booksOwned = booksOwned;
        this.booksForSale = booksForSale;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<Book> getBooksOwned() {
        return booksOwned;
    }

    public void setBooksOwned(List<Book> booksOwned) {
        this.booksOwned = booksOwned;
    }

    public List<Listing> getBooksForSale() {
        return booksForSale;
    }

    public void setBooksForSale(List<Listing> booksForSale) {
        this.booksForSale = booksForSale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userID == user.userID;
    }

    @Override
    public int hashCode() {
        return userID;
    }

    @Override
    public String toString() {
        return "User{" +
            "userID=" + userID +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", salt='" + salt + '\'' +
            ", major=" + major +
            ", school=" + school +
            ", isDeleted=" + isDeleted +
            ", booksOwned=" + booksOwned +
            ", booksForSale=" + booksForSale +
            '}';
    }
}
