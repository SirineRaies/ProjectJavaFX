package org.example.projectjavafx.Models;

public class Produit {
    private long id;
    private String nompro;
    private float prixpro;

    // Constructeurs
    public Produit() {
    }

    public Produit(long id, String nompro, float prixpro) {
        this.id = id;
        this.nompro = nompro;
        this.prixpro = prixpro;
    }
    public Produit(String nompro, float prixpro) {
        this(0,nompro,prixpro);
    }
    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNompro() {
        return nompro;
    }

    public void setNompro(String nompro) {
        this.nompro = nompro;
    }

    public float getPrixpro() {
        return prixpro;
    }

    public void setPrixpro(float prixpro) {
        this.prixpro = prixpro;
    }

}
