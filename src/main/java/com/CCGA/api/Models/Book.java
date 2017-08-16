package com.CCGA.api.Models;

import javax.persistence.*;

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
    private String ISBN;

    @Column
    private String description;

    @Column
    private String picture;

    @OneToOne
    private Major major;

    public Book() {
    }

    public Book(String name, String author, String ISBN, String description, String picture, Major major) {
        this.name = name;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.picture = picture;
        this.major = major;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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
            ", ISBN='" + ISBN + '\'' +
            ", description='" + description + '\'' +
            ", picture='" + picture + '\'' +
            '}';
    }
}
