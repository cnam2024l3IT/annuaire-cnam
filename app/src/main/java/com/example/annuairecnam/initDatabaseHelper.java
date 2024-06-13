package com.example.annuairecnam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class initDatabaseHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbAnnuaireCnam";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CLASSES = "classes";
    private static final String TABLE_CLASSES_ELEVES = "classes_eleves";
    private static final String TABLE_CLASSES_MATIERES = "classes_matieres";
    private static final String TABLE_ELEVES = "eleves";
    private static final String TABLE_MATIERES = "matieres";
    private static final String TABLE_NOTES = "notes";

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public initDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //TABLE CLASSES
        String CREATE_CLASSES_TABLE = " CREATE TABLE " + TABLE_CLASSES +
                "(" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                "intitule" + " TEXT," +
                "promotion" + " TEXT" +
                ")";
        db.execSQL(CREATE_CLASSES_TABLE);

        // TABLE CLASSES_ELEVES
        String CREATE_CLASSES_ELEVES = " CREATE TABLE " + TABLE_CLASSES_ELEVES +
                "(" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                "fk_classe" + " INT," +
                "fk_eleve" + " INT" +
                ")";
        db.execSQL(CREATE_CLASSES_ELEVES);

        // TABLE CLASSES_MATIERES
        String CREATE_CLASSES_MATIERES = " CREATE TABLE " + TABLE_CLASSES_MATIERES +
                "(" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                "fk_classe" + " INT," +
                "fk_matiere" + " INT" +
                ")";
        db.execSQL(CREATE_CLASSES_MATIERES);

        // TABLE ELEVES
        String CREATE_ELEVES = " CREATE TABLE " + TABLE_ELEVES +
                "(" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                "nom" + " TEXT," +
                "prenom" + " TEXT," +
                "date_Naissance" + " TEXT," +
                "email" + " TEXT," +
                "mobile" + " TEXT" +
                ")";
        db.execSQL(CREATE_ELEVES);

        // TABLE MATIERES
        String CREATE_MATIERES = " CREATE TABLE " + TABLE_MATIERES +
                "(" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                "professeur" + " TEXT," +
                "intitulé" + " TEXT," +
                "description" + " TEXT" +
                ")";
        db.execSQL(CREATE_MATIERES);

        // TABLE NOTES
        String CREATE_NOTES = " CREATE TABLE " + TABLE_NOTES +
                "(" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                "fk_classe" + " INT," +
                "fk_matiere" + " INT," +
                "fk_eleve" + " INT," +
                "note" + " INT" +
                ")";
        db.execSQL(CREATE_NOTES);
    }


}
