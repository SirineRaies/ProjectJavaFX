package org.example.projectjavafx.Models;

public class Commande {
    private long id;
    private String adresse;
    private String telephone;
    private long produit_ID;
    private long user_ID;

    // Constructeurs
    public Commande() {
    }

    public Commande(long id, String adresse, String telephone, long produit_ID ,long user_ID) {
        this.id = id;
        this.adresse = adresse;
        this.telephone = telephone;
        this.produit_ID = produit_ID;

        this.user_ID = user_ID;
    }

    public Commande(String adresse, String telephone, long produit_ID ,long user_ID) {
        this(0, adresse, telephone, produit_ID,user_ID);
    }


    // Getters et setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public long getProduit_ID() { // Modification du nom de la méthode
        return produit_ID;
    }

    public void setProduit_ID(long produit_ID) {
        this.produit_ID = produit_ID;
    }
    public long getUser_ID() { // Modification du nom de la méthode
        return user_ID;
    }

    public void setUser_ID(long user_ID) {
        this.user_ID = user_ID;
    }

}
