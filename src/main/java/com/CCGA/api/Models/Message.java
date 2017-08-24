package com.CCGA.api.Models;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue
    private int messageID;

    @OneToOne
    private User sentFrom;

    @OneToOne
    private User sentTo;

    @Column
    private String message;

    @Column
    private LocalTime createdAt;

    @Column
    private LocalTime updatedAt;

    public Message() {
        createdAt = LocalTime.now();
        updatedAt = LocalTime.now();
    }

    public Message(User sentFrom, User sentTo, String message) {
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.message = message;
        createdAt = LocalTime.now();
        updatedAt = LocalTime.now();
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public User getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(User sentFrom) {
        this.sentFrom = sentFrom;
    }

    public User getSentTo() {
        return sentTo;
    }

    public void setSentTo(User sentTo) {
        this.sentTo = sentTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return messageID == message.messageID;
    }

    @Override
    public int hashCode() {
        return messageID;
    }

    @Override
    public String toString() {
        return "Message{" +
            "messageID=" + messageID +
            ", sentFrom=" + sentFrom +
            ", sentTo=" + sentTo +
            ", message='" + message + '\'' +
            '}';
    }
}
