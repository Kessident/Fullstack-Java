package com.CCGA.api.Models;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Message() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Message(User sentFrom, User sentTo, String message) {
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.message = message;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
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
