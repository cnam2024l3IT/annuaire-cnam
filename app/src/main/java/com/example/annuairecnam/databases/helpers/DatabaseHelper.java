package com.example.annuairecnam.databases.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.annuairecnam.databases.contracts.DataContract;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "annuaire_cnam.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataContract.SQL_CREATE_CLASSE_TABLE);
        db.execSQL(DataContract.SQL_CREATE_ELEVE_TABLE);
        db.execSQL(DataContract.SQL_CREATE_MATIERE_TABLE);
        db.execSQL(DataContract.SQL_CREATE_CLASSE_ELEVE_TABLE);
        db.execSQL(DataContract.SQL_CREATE_CLASSE_MATIERE_TABLE);
        db.execSQL(DataContract.SQL_CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataContract.SQL_DROP_NOTE_TABLE);
        db.execSQL(DataContract.SQL_DROP_CLASSE_MATIERE_TABLE);
        db.execSQL(DataContract.SQL_DROP_CLASSE_ELEVE_TABLE);
        db.execSQL(DataContract.SQL_DROP_MATIERE_TABLE);
        db.execSQL(DataContract.SQL_DROP_ELEVE_TABLE);
        db.execSQL(DataContract.SQL_DROP_CLASSE_TABLE);
        onCreate(db);
    }

}
