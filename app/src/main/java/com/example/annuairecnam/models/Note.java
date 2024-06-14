package com.example.annuairecnam.models;

public class Note extends DataInfo {
    private long classeId;
    private long eleveId;
    private Matiere matiere;
    private float valeur;

    public Note() {}

    public Note(long _id, Matiere matiere) {
        this._id = _id;
        this.matiere = matiere;
    }

    public long getClasseId() {
        return classeId;
    }

    public void setClasseId(long classeId) {
        this.classeId = classeId;
    }

    public long getEleveId() {
        return eleveId;
    }

    public void setEleveId(long eleveId) {
        this.eleveId = eleveId;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public float getValeur() {
        return valeur;
    }

    public void setValeur(float valeur) {
        this.valeur = valeur;
    }
}
