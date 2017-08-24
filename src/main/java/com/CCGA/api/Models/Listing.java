package com.CCGA.api.Models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue
    private int listingID;

    @OneToOne
    private Book offered;

    @Column
    private Condition condition;

    @Column
    private long askingPrice;

    //Base-64 encoded string of book picture
    @Column
    private String picture;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Listing() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Listing(Book offered, Condition condition, long askingPrice, String picture) {
        this.offered = offered;
        this.condition = condition;
        this.askingPrice = askingPrice;
        this.picture = picture;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public Book getOffered() {
        return offered;
    }

    public void setOffered(Book offered) {
        this.offered = offered;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public long getAskingPrice() {
        return askingPrice;
    }

    public void setAskingPrice(long askingPrice) {
        this.askingPrice = askingPrice;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

        Listing listing = (Listing) o;

        return listingID == listing.listingID;
    }

    @Override
    public int hashCode() {
        return listingID;
    }

    @Override
    public String toString() {
        return "Listing{" +
            "listingID=" + listingID +
            ", offered=" + offered +
            ", condition=" + condition +
            ", askingPrice=" + askingPrice +
            '}';
    }
}
