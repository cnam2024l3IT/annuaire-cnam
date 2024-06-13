package com.example.annuairecnam;

public class Classes {
    int id;
   String intitule;
   String promotion;

    public Classes() {
    }

    public Classes(int id, String intitule, String promotion) {
        this.id = id;
        this.intitule = intitule;
        this.promotion = promotion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
