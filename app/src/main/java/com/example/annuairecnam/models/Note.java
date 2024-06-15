package com.example.annuairecnam.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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

    public Note(Parcel in) {
        _id = in.readLong();
        classeId = in.readLong();
        eleveId = in.readLong();
        matiere = in.readParcelable(Matiere.class.getClassLoader());
        valeur = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeLong(classeId);
        dest.writeLong(eleveId);
        dest.writeParcelable(matiere, flags);
        dest.writeFloat(valeur);
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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
