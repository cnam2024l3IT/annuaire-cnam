package com.example.annuairecnam;

public class Matieres {
    int id;
    String professeur;
    String intitule;
    String description;

    public Matieres() {
    }

    public Matieres(int id, String professeur, String intitule, String description) {
        this.id = id;
        this.professeur = professeur;
        this.intitule = intitule;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfesseur() {
        return professeur;
    }

    public void setProfesseur(String professeur) {
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
}

