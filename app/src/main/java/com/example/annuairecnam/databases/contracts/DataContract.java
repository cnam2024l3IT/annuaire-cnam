package com.example.annuairecnam.databases.contracts;

import android.provider.BaseColumns;

public class DataContract {

    private DataContract() {}

    public static abstract class InfoTable implements BaseColumns {
        protected static String TABLE_NAME;
    }

    public static class ClasseTable extends InfoTable {
        public static final String TABLE_NAME = "classes";
        public static final String INTITULE = "intitule";
        public static final String PROMOTION = "promotion";
    }

    public static final String SQL_CREATE_CLASSE_TABLE = "CREATE TABLE " + ClasseTable.TABLE_NAME + " (" + ClasseTable._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + ClasseTable.INTITULE + " TEXT NOT NULL, " + ClasseTable.PROMOTION
            + " TEXT NOT NULL);";
    public static final String SQL_DELETE_CLASSE_TABLE = "DROP TABLE IF EXISTS " + ClasseTable.TABLE_NAME;
    public static final String SQL_CLASSES_BY_ELEVE_ID = "SELECT " + ClasseTable._ID + ", " + ClasseTable.INTITULE + ", "
            + ClasseTable.PROMOTION + " FROM " + ClasseEleveTable.TABLE_NAME + " INNER JOIN " + ClasseTable.TABLE_NAME + " ON "
            + ClasseEleveTable.CLASSE_ID + " = " + ClasseTable._ID + " WHERE " + ClasseEleveTable.ELEVE_ID + " = ?s";

    public static class EleveTable extends InfoTable {
        public static final String TABLE_NAME = "eleves";
        public static final String NOM = "nom";
        public static final String PRENOM = "prenom";
        public static final String DATE_NAISSANCE = "date_naissance";
        public static final String EMAIL = "email";
        public static final String TELEPHONE = "telephone";
    }

    public static final String SQL_CREATE_ELEVE_TABLE = "CREATE TABLE " + EleveTable.TABLE_NAME + " (" + EleveTable._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + EleveTable.NOM + " TEXT NOT NULL, " + EleveTable.PRENOM + " TEXT NOT NULL, "
            + EleveTable.DATE_NAISSANCE + " TEXT NOT NULL, " + EleveTable.EMAIL + " TEXT NOT NULL UNIQUE, " + EleveTable.TELEPHONE
            + " TEXT DEFAULT NULL);";
    public static final String SQL_DELETE_ELEVE_TABLE = "DROP TABLE IF EXISTS " + EleveTable.TABLE_NAME;

    public static class MatiereTable extends InfoTable {
        public static final String TABLE_NAME = "matieres";
        public static final String DESCRIPTION = "description";
        public static final String INTITULE = "intitule";
        public static final String PROFESSEUR = "professeur";
    }

    public static final String SQL_CREATE_MATIERE_TABLE = "CREATE TABLE " + MatiereTable.TABLE_NAME + " (" + MatiereTable._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + MatiereTable.INTITULE + " TEXT NOT NULL, " + MatiereTable.DESCRIPTION
            + " TEXT NOT NULL, " + MatiereTable.PROFESSEUR + " TEXT NOT NULL);" ;
    public static final String SQL_DELETE_MATIERE_TABLE = "DROP TABLE IF EXISTS " + MatiereTable.TABLE_NAME;

    public static class ClasseEleveTable extends InfoTable {
        public static final String TABLE_NAME = "classes_eleves";
        public static final String CLASSE_ID = "classe_id";
        public static final String ELEVE_ID = "eleve_id";
    }

    public static final String SQL_CREATE_CLASSE_ELEVE_TABLE = "CREATE TABLE " + ClasseEleveTable.TABLE_NAME + " ("
            + ClasseEleveTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ClasseEleveTable.CLASSE_ID + " INTEGER NOT NULL, "
            + ClasseEleveTable.ELEVE_ID + " INTEGER NOT NULL, FOREIGN KEY (" + ClasseEleveTable.CLASSE_ID + ") REFERENCES "
            + ClasseTable.TABLE_NAME + " (" + ClasseTable._ID + ") ON UPDATE NO ACTION ON DELETE CASCADE, FOREIGN KEY ("
            + ClasseEleveTable.ELEVE_ID + ") REFERENCES " + EleveTable.TABLE_NAME + " (" + EleveTable._ID
            + ") ON UPDATE NO ACTION ON DELETE CASCADE);";
    public static final String SQL_DELETE_CLASSE_ELEVE_TABLE = "DROP TABLE IF EXISTS " + ClasseEleveTable.TABLE_NAME;
    public static final String SQL_ELEVES_BY_CLASSE_ID = "SELECT " + EleveTable._ID + ", " + EleveTable.NOM + ", "
            + EleveTable.PRENOM + " FROM " + ClasseEleveTable.TABLE_NAME + " INNER JOIN "
            + EleveTable.TABLE_NAME + " ON " + ClasseEleveTable.ELEVE_ID + " = "
            + EleveTable._ID + " WHERE " + ClasseEleveTable.CLASSE_ID + " = ?s";

    public static class ClasseMatiereTable extends InfoTable {
        public static final String TABLE_NAME = "classes_matieres";
        public static final String CLASSE_ID = "classe_id";
        public static final String MATIERE_ID = "matiere_id";
    }

    public static final String SQL_CREATE_CLASSE_MATIERE_TABLE = "CREATE TABLE " + ClasseMatiereTable.TABLE_NAME + " ("
            + ClasseMatiereTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ClasseMatiereTable.CLASSE_ID + " INTEGER NOT NULL, "
            + ClasseMatiereTable.MATIERE_ID + " INTEGER NOT NULL, FOREIGN KEY (" + ClasseMatiereTable.CLASSE_ID + ") REFERENCES "
            + ClasseTable.TABLE_NAME + " (" + ClasseTable._ID + ") ON UPDATE NO ACTION ON DELETE CASCADE, FOREIGN KEY ("
            + ClasseMatiereTable.MATIERE_ID + ") REFERENCES " + MatiereTable.TABLE_NAME + " (" + MatiereTable._ID
            + ") ON UPDATE NO ACTION ON DELETE CASCADE);";
    public static final String SQL_DELETE_CLASSE_MATIERE_TABLE = "DROP TABLE IF EXISTS " + ClasseMatiereTable.TABLE_NAME;
    public static final String SQL_MATIERES_BY_CLASSE_ID = "SELECT " + MatiereTable._ID + ", " + MatiereTable.INTITULE + " FROM "
            + ClasseMatiereTable.TABLE_NAME + " INNER JOIN " + MatiereTable.TABLE_NAME + " ON " + ClasseMatiereTable.MATIERE_ID
            + " = " + MatiereTable._ID + " WHERE " + ClasseMatiereTable.CLASSE_ID + " = ?s";

    public static class NoteTable extends InfoTable {
        public static final String TABLE_NAME = "notes";
        public static final String CLASSE_ID = "classe_id";
        public static final String ELEVE_ID = "eleve_id";
        public static final String MATIERE_ID = "matiere_id";
        public static final String VALEUR = "valeur";
    }

    public static final String SQL_CREATE_NOTE_TABLE = "CREATE TABLE " + NoteTable.TABLE_NAME + " (" + NoteTable._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + NoteTable.CLASSE_ID + " INTEGER NOT NULL, " + NoteTable.ELEVE_ID
            + " INTEGER NOT NULL, " + NoteTable.MATIERE_ID + " INTEGER NOT NULL, " + NoteTable.VALEUR
            + " REAL NOT NULL DEFAULT 0, FOREIGN KEY (" + NoteTable.CLASSE_ID + ") REFERENCES " + ClasseTable.TABLE_NAME + " ("
            + ClasseTable._ID + ") ON UPDATE NO ACTION ON DELETE CASCADE, FOREIGN KEY (" + NoteTable.ELEVE_ID + ") REFERENCES "
            + EleveTable.TABLE_NAME + " (" + EleveTable._ID + ") ON UPDATE NO ACTION ON DELETE CASCADE, FOREIGN KEY ("
            + NoteTable.MATIERE_ID + ") REFERENCES " + MatiereTable.TABLE_NAME + " (" + MatiereTable._ID
            + ") ON UPDATE NO ACTION ON DELETE CASCADE);";
    public static final String SQL_DELETE_NOTE_TABLE = "DROP TABLE IF EXISTS " + NoteTable.TABLE_NAME;

    public static final String SQL_NOTES_BY_CLASSE_ID_AND_ELEVE_ID = "SELECT " + NoteTable._ID + ", " + MatiereTable._ID + ", "
            + MatiereTable.INTITULE + " FROM " + NoteTable.TABLE_NAME + " INNER JOIN " + MatiereTable.TABLE_NAME + " ON "
            + NoteTable.MATIERE_ID + " = " + MatiereTable._ID + " WHERE " + NoteTable.CLASSE_ID + " = ?s AND " + NoteTable.ELEVE_ID
            + " = ?s";

}
