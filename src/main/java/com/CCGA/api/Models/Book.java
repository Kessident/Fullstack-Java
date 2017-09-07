package com.CCGA.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private int bookID;

    @Column
    private String name;

    @Column
    private String author;

    @Column
    private String isbn;

    @Column
    private String description;

    //Base-64 encoded string of book picture

    @Column
    private String picture;

    @OneToOne
    private Major major;

    @Column
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Book() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Book(String name, String author, String isbn, String description, String picture, Major major) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.picture = picture;
        this.major = major;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
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

        Book book = (Book) o;

        return bookID == book.bookID;
    }

    @Override
    public int hashCode() {
        return bookID;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
