package com.example.annuairecnam.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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

    public Classe(Parcel in) {
        _id = in.readLong();
        intitule = in.readString();
        promotion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(intitule);
        dest.writeString(promotion);
    }

    public static final Parcelable.Creator<Classe> CREATOR = new Parcelable.Creator<Classe>() {
        public Classe createFromParcel(Parcel in) {
            return new Classe(in);
        }

        public Classe[] newArray(int size) {
            return new Classe[size];
        }
    };

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
