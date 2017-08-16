package com.CCGA.api.Models;

import javax.persistence.*;

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

    public Request() {
    }

    public Request(Book bookRequested, User userRequested) {
        this.bookRequested = bookRequested;
        this.userRequested = userRequested;
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
