package com.CCGA.api.Models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private int transactionID;

    @OneToOne
    private User seller;

    @OneToOne
    private User buyer;

    @OneToOne
    private Book bookSold;

    @Column
    private long amountSoldFor;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Transaction() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Transaction(User seller, User buyer, Book bookSold, long amountSoldFor) {
        this.seller = seller;
        this.buyer = buyer;
        this.bookSold = bookSold;
        this.amountSoldFor = amountSoldFor;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Book getBookSold() {
        return bookSold;
    }

    public void setBookSold(Book bookSold) {
        this.bookSold = bookSold;
    }

    public long getAmountSoldFor() {
        return amountSoldFor;
    }

    public void setAmountSoldFor(long amountSoldFor) {
        this.amountSoldFor = amountSoldFor;
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

        Transaction that = (Transaction) o;

        return transactionID == that.transactionID;
    }

    @Override
    public int hashCode() {
        return transactionID;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "transactionID=" + transactionID +
            ", seller=" + seller +
            ", buyer=" + buyer +
            ", bookSold=" + bookSold +
            ", amountSoldFor=" + amountSoldFor +
            '}';
    }
}
