package com.example.annuairecnam.models;

public class Matiere extends DataInfo {
    String intitule;
    String description;
    String professeur;

    public Matiere() { }

    public Matiere(long _id, String intitule) {
        this._id = _id;
        this.intitule = intitule;
    }

    public Matiere(String intitule, String description, String professeur) {
        this.intitule = intitule;
        this.description = description;
        this.professeur = professeur;
    }

    public Matiere(long _id, String intitule, String description, String professeur) {
        this._id = _id;
        this.intitule = intitule;
        this.description = description;
        this.professeur = professeur;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfesseur() {
        return professeur;
    }

    public void setProfesseur(String professeur) {
        this.professeur = professeur;
    }

}

