package org.example.projectjavafx.Models;

public class Rating {
    private long id;
    private String commentaire;
    private float rating;
    private long user_ID;

    public Rating() {
    }

    public Rating(long id, String commentaire, float rating, long user_ID) {
        this.id = id;
        this.commentaire = commentaire;
        this.rating = rating;
        this.user_ID = user_ID;
    }

    public Rating(String commentaire, float rating, long user_ID) {
        this(0, commentaire, rating, user_ID);
    }

    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getUser_ID() { // Modification du nom de la méthode
        return user_ID;
    }

    public void setUser_ID(long user_ID) {
        this.user_ID = user_ID;
    }

}
