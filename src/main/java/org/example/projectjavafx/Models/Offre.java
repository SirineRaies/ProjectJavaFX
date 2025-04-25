package org.example.projectjavafx.Models;

public class Offre {
    private long id;
    private String offre;
    private String contenu;
    private long produit_Id;

    // Constructeurs
    public Offre() {
    }
    public Offre(String offre, String contenu, long produit_Id) {
        this(0, offre, contenu, produit_Id);
    }
    public Offre(long id, String offre, String contenu, long produit_Id) {
        this.id = id;
        this.offre = offre;
        this.contenu = contenu;
        this.produit_Id = produit_Id;
    }

    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOffre() {
        return offre;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public long getProduit_Id() { // Modification du nom de la méthode
        return produit_Id;
    }

    public void setProduit_Id(long produit_Id) {
        this.produit_Id = produit_Id;
    }

}
