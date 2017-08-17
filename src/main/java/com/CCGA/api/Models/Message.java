package com.CCGA.api.Models;

import javax.persistence.*;

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

    public Message() {
    }

    public Message(User sentFrom, User sentTo, String message) {
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.message = message;
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
