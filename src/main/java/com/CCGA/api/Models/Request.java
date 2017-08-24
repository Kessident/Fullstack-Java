package com.CCGA.api.Models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="requests")
public class Request {
    @Id
    @GeneratedValue
    private int requestID;

    @OneToOne
    private Book bookRequested;

    @OneToOne
    private User userRequested;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Request() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Request(Book bookRequested, User userRequested) {
        this.bookRequested = bookRequested;
        this.userRequested = userRequested;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public Book getBookRequested() {
        return bookRequested;
    }

    public void setBookRequested(Book bookRequested) {
        this.bookRequested = bookRequested;
    }

    public User getUserRequested() {
        return userRequested;
    }

    public void setUserRequested(User userRequested) {
        this.userRequested = userRequested;
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

        Request request = (Request) o;

        return requestID == request.requestID;
    }

    @Override
    public int hashCode() {
        return requestID;
    }

    @Override
    public String toString() {
        return "Request{" +
            "requestID=" + requestID +
            ", bookRequested=" + bookRequested +
            ", userRequested=" + userRequested +
            '}';
    }
}
