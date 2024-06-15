package com.example.annuairecnam.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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

    public Eleve(Parcel in) {
        _id = in.readLong();
        nom = in.readString();
        prenom = in.readString();
        dateNaissance = in.readString();
        email = in.readString();
        telephone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(dateNaissance);
        dest.writeString(email);
        dest.writeString(telephone);
    }

    public static final Parcelable.Creator<Eleve> CREATOR = new Parcelable.Creator<Eleve>() {
        public Eleve createFromParcel(Parcel in) {
            return new Eleve(in);
        }

        public Eleve[] newArray(int size) {
            return new Eleve[size];
        }
    };

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
