package com.CCGA.api.Models;

import javax.persistence.*;
import java.time.LocalTime;

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

    @Column
    private LocalTime createdAt;

    @Column
    private LocalTime updatedAt;

    public Listing() {
        createdAt = LocalTime.now();
        updatedAt = LocalTime.now();
    }

    public Listing(Book offered, Condition condition, long askingPrice) {
        this.offered = offered;
        this.condition = condition;
        this.askingPrice = askingPrice;
        createdAt = LocalTime.now();
        updatedAt = LocalTime.now();
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
