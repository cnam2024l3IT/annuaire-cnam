package com.example.annuairecnam.models;

public class Classe extends DataInfo {

    private String intitule;
    private String promotion;

    public Classe() { }

    public Classe(String intitule, String promotion) {
        this.intitule = intitule;
        this.promotion = promotion;
    }

    public Classe(long _id, String intitule, String promotion) {
        this._id = _id;
        this.intitule = intitule;
        this.promotion = promotion;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

}
