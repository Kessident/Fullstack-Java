package com.CCGA.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue
    private int userID;

    @Column @NotNull
    private String name;

    @Column(unique = true) @NotNull
    private String email;

    @Column @NotNull @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToOne
    private Major major;

    @JsonIgnore
    @OneToOne
    private School school;

    @Column @JsonIgnore
    private boolean isDeleted;

    @OneToMany
    private List<Book> booksOwned;

    @OneToMany
    private List<Listing> booksForSale;

    @Column @JsonIgnore
    private LocalDateTime createdAt;

    @Column @JsonIgnore
    private LocalDateTime updatedAt;

    public User() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public User(String name, String email, String password, Major major, School school, boolean isDeleted, List<Book> booksOwned, List<Listing> booksForSale) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.major = major;
        this.school = school;
        this.isDeleted = isDeleted;
        this.booksOwned = booksOwned;
        this.booksForSale = booksForSale;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
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

    public void addListing(Listing listing){
        if (booksOwned.contains(listing.getOffered())){
            booksForSale.add(listing);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addBook(Book newBook){
        booksOwned.add(newBook);
    }

    public void removeListing(Listing listing){
        booksForSale.remove(listing);
    }

    public void removeBookOwned(Book book){
        booksOwned.remove(book);
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
            ", major=" + major +
            ", school=" + school +
            ", isDeleted=" + isDeleted +
            ", booksOwned=" + booksOwned +
            ", booksForSale=" + booksForSale +
            '}';
    }
}
