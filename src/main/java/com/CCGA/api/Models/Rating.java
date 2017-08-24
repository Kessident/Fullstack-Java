package com.CCGA.api.Models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue
    private int ratingID;

    @OneToOne
    private User about;

    @Column
    private double rating;

    @OneToOne
    private User issuer;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Rating() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public Rating(User about, double rating, User issuer) {
        this.about = about;
        this.rating = rating;
        this.issuer = issuer;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public int getRatingID() {
        return ratingID;
    }

    public void setRatingID(int ratingID) {
        this.ratingID = ratingID;
    }

    public User getAbout() {
        return about;
    }

    public void setAbout(User about) {
        this.about = about;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
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

        Rating rating = (Rating) o;

        return ratingID == rating.ratingID;
    }

    @Override
    public int hashCode() {
        return ratingID;
    }

    @Override
    public String toString() {
        return "Rating{" +
            "ratingID=" + ratingID +
            ", about=" + about +
            ", rating=" + rating +
            ", issuer=" + issuer +
            '}';
    }
}
