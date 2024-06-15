package com.example.annuairecnam.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Matiere extends DataInfo {
    private String intitule;
    private String description;
    private String professeur;

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

    private Matiere(Parcel in) {
        _id = in.readLong();
        intitule = in.readString();
        description = in.readString();
        professeur = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(intitule);
        dest.writeString(description);
        dest.writeString(professeur);
    }

    public static final Parcelable.Creator<Matiere> CREATOR = new Parcelable.Creator<Matiere>() {
        public Matiere createFromParcel(Parcel in) {
            return new Matiere(in);
        }

        public Matiere[] newArray(int size) {
            return new Matiere[size];
        }
    };

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

