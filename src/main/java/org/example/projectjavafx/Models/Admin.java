package org.example.projectjavafx.Models;

import kotlin.text.UStringsKt;

public class Admin {
    private long id;
    private String nomcomplet;
    private String email;  // Nouveau champ email
    private String mdp;

    // Constructeurs
    public Admin() {
    }

    public Admin(long id, String nomcomplet, String email, String mdp) {
        this.id = id;
        this.nomcomplet = nomcomplet;
        this.email = email;
        this.mdp = mdp;
    }

    public Admin(String nomcomplet, String email, String mdp ) {
        this(0, nomcomplet, email, mdp);
    }


    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomcomplet() {
        return nomcomplet;
    }

    public void setNomcomplet(String nomcomplet) {
        this.nomcomplet = nomcomplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }



}
