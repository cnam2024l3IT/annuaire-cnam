package com.example.annuairecnam.models;

public class Eleve extends DataInfo {

    private String nom;
    private String prenom;
    private String dateNaissance;
    private String email;
    private String telephone;

    public Eleve() { }

    public Eleve(long _id, String nom, String prenom) {
        this._id = _id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Eleve(String nom, String prenom, String dateNaissance, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
    }

    public Eleve(long _id, String nom, String prenom, String dateNaissance, String email, String telephone) {
        this._id = _id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
