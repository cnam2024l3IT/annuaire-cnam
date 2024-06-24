package com.example.annuairecnam.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.annuairecnam.databases.contracts.DataContract;
import com.example.annuairecnam.databases.helpers.DatabaseHelper;
import com.example.annuairecnam.models.Classe;
import com.example.annuairecnam.models.Eleve;
import com.example.annuairecnam.models.Matiere;
import com.example.annuairecnam.models.Note;
import com.example.annuairecnam.models.exceptions.EntityNotFoundException;

import java.util.ArrayList;

public class DbManager {
    private DatabaseHelper dbHelper;
    private final Context context;
    private SQLiteDatabase database;

    public DbManager(Context c) {
        context = c;
    }

    public DbManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private  Cursor fetchAll(String tableName, String[] columns) {
        return database.query(tableName, columns, null, null, null, null, null);
    }

    private Cursor fetchOne(String tableName, String[] columns, long _id) {
        return database.query(tableName, columns, BaseColumns._ID + " = " + _id, null, null,
                null, null);
    }

    private Cursor fetchSQL(String sql, String[] params) {
        return database.rawQuery(sql, params);
    }

    /**
     * Récupération de toutes les classes
     * @return tableau de classes
     */
    public  ArrayList<Classe> getAllClasses() {
        String[] columns = new String[] { DataContract.ClasseTable._ID,DataContract.ClasseTable.INTITULE,
                DataContract.ClasseTable.PROMOTION };
        ArrayList<Classe> classes = new ArrayList<>();
        try (Cursor cursor = fetchAll(DataContract.ClasseTable.TABLE_NAME, columns)) {
            if (cursor.moveToFirst())
                do classes.add(new Classe(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2))); while (cursor.moveToNext());
        }
        return classes;
    }

    /**
     * Récupération des classes d'un élève
     * @param _id id élève
     * @return tableau de classes
     */
    public ArrayList<Classe> getClassesByEleveId(long _id) {
        ArrayList<Classe> classes = new ArrayList<>();
        try (Cursor cursor = fetchSQL(DataContract.SQL_CLASSES_BY_ELEVE_ID, new String[]{Long.toString(_id)})) {
            if (cursor.moveToFirst())
                do classes.add(new Classe(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2))); while (cursor.moveToNext());
        }
        return classes;
    }

    /**
     * Récupération d'une classe
     * @param _id id classe
     * @return objet Classe
     * @throws EntityNotFoundException Enregistrement non-trouvé
     */
    public Classe getClasse(long _id) throws EntityNotFoundException {
        String[] columns = new String[] { DataContract.ClasseTable._ID,DataContract.ClasseTable.INTITULE,
                DataContract.ClasseTable.PROMOTION };
        try (Cursor cursor = fetchOne(DataContract.ClasseTable.TABLE_NAME, columns, _id)) {
            if (cursor.moveToFirst())
                return new Classe(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            else throw new EntityNotFoundException("Aucune classe n'a été trouvée avec l'id " + _id);
        }
    }

    /**
     * Récupération de tous les élèves
     * @return tableau d'élèves
     */
    public ArrayList<Eleve> getAllEleves() {
        String[] columns = new String[] { DataContract.EleveTable._ID,DataContract.EleveTable.NOM,
                DataContract.EleveTable.PRENOM };
        ArrayList<Eleve> eleves = new ArrayList<>();
        try (Cursor cursor = fetchAll(DataContract.EleveTable.TABLE_NAME, columns)) {
            if (cursor.moveToFirst())
                do eleves.add(new Eleve(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2))); while (cursor.moveToNext());
        }
        return eleves;
    }

    /**
     * Récupération des élèves d'une classe
     * @param _id id classe
     * @return tableau d'élèves
     */
    public ArrayList<Eleve> getElevesByClasseId(long _id) {
        ArrayList<Eleve> eleves = new ArrayList<>();
        try (Cursor cursor = fetchSQL(DataContract.SQL_ELEVES_BY_CLASSE_ID, new String[]{Long.toString(_id)})) {
            if (cursor.moveToFirst())
                do eleves.add(new Eleve(cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2))); while (cursor.moveToNext());
        }
        return eleves;
    }

    /**
     * Récupération d'un élève
     * @param _id id élève
     * @return objet élève
     * @throws EntityNotFoundException Enregistrement non-trouvé
     */
    public Eleve getEleve(long _id) throws EntityNotFoundException {
        String[] columns = new String[] { DataContract.EleveTable._ID,DataContract.EleveTable.NOM,
                DataContract.EleveTable.PRENOM,DataContract.EleveTable.DATE_NAISSANCE,DataContract.EleveTable.EMAIL,
                DataContract.EleveTable.TELEPHONE };
        try (Cursor cursor = fetchOne(DataContract.EleveTable.TABLE_NAME, columns, _id)) {
            if (cursor.moveToFirst())
                return new Eleve(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5));
            else throw new EntityNotFoundException("Aucun élève n'a été trouvée avec l'id " + _id);
        }
    }

    /**
     * Récupération de toutes les matières
     * @return tableau de matières
     */
    public ArrayList<Matiere> getAllMatieres() {
        String[] columns = new String[] { DataContract.MatiereTable._ID,DataContract.MatiereTable.INTITULE };
        ArrayList<Matiere> matieres = new ArrayList<>();
        try (Cursor cursor = fetchAll(DataContract.MatiereTable.TABLE_NAME, columns)) {
            if (cursor.moveToFirst())
                do matieres.add(new Matiere(cursor.getLong(0), cursor.getString(1)));
                while (cursor.moveToNext());
        }
        return matieres;
    }

    /**
     * Récupération des matières d'une classe
     * @param _id id classe
     * @return tableau de matières
     */
    public ArrayList<Matiere> getMatieresByClasseId(long _id) {
        ArrayList<Matiere> matieres = new ArrayList<>();
        try (Cursor cursor = fetchSQL(DataContract.SQL_MATIERES_BY_CLASSE_ID, new String[]{Long.toString(_id)})) {
            if (cursor.moveToFirst())
                do matieres.add(new Matiere(cursor.getLong(0), cursor.getString(1)));
                while (cursor.moveToNext());
        }
        return matieres;
    }

    /**
     * Récupération d'une matière
     * @param _id id matière
     * @return objet matière
     * @throws EntityNotFoundException Enregistrement non-trouvé
     */
    public Matiere getMatiere(long _id) throws EntityNotFoundException {
        String[] columns = new String[] { DataContract.MatiereTable._ID,DataContract.MatiereTable.INTITULE,
                DataContract.MatiereTable.DESCRIPTION,DataContract.MatiereTable.PROFESSEUR };
        try (Cursor cursor = fetchOne(DataContract.MatiereTable.TABLE_NAME, columns, _id)) {
            if (cursor.moveToFirst())
                return new Matiere(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3));
            else throw new EntityNotFoundException("Aucune matière n'a été trouvée avec l'id " + _id);
        }
    }

    /**
     * Récupération notes d'une classe et d'un élève
     * @param classeId id classe
     * @param eleveId id élève
     * @return tableau de notes
     */
    public ArrayList<Note> getNotesByClasseIdAndEleveId(long classeId, long eleveId) {
        ArrayList<Note> notes = new ArrayList<>();
        try (Cursor cursor = fetchSQL(DataContract.SQL_NOTES_BY_CLASSE_ID_AND_ELEVE_ID,
                new String[]{Long.toString(classeId),Long.toString(eleveId)})) {
            if (cursor.moveToFirst())
                do notes.add(new Note(cursor.getLong(0), new Matiere(cursor.getLong(1),
                        cursor.getString(2)))); while (cursor.moveToNext());
        }
        return notes;
    }

    private long insert(String tableName, ContentValues content) {
        return database.insert(tableName, null, content);
    }

    /**
     * Création d'une classe
     * @param classe nouvelle classe
     * @return id créé
     */
    public long insertClasse(Classe classe) {
        ContentValues content = new ContentValues();
        content.put(DataContract.ClasseTable.INTITULE, classe.getIntitule());
        content.put(DataContract.ClasseTable.PROMOTION, classe.getPromotion());
        return insert(DataContract.ClasseTable.TABLE_NAME, content);
    }

    /**
     * Création d'un élève
     * @param eleve nouvel élève
     * @return id créé
     */
    public long insertEleve(Eleve eleve) {
        ContentValues content = new ContentValues();
        content.put(DataContract.EleveTable.NOM, eleve.getNom());
        content.put(DataContract.EleveTable.PRENOM, eleve.getPrenom());
        content.put(DataContract.EleveTable.DATE_NAISSANCE, eleve.getDateNaissance());
        content.put(DataContract.EleveTable.EMAIL, eleve.getEmail());
        content.put(DataContract.EleveTable.TELEPHONE, eleve.getTelephone());
        return insert(DataContract.EleveTable.TABLE_NAME, content);
    }

    /**
     * Création d'une matière
     * @param matiere nouvelle matière
     * @return id créé
     */
    public long insertMatiere(Matiere matiere) {
        ContentValues content = new ContentValues();
        content.put(DataContract.MatiereTable.INTITULE, matiere.getIntitule());
        content.put(DataContract.MatiereTable.DESCRIPTION, matiere.getDescription());
        content.put(DataContract.MatiereTable.PROFESSEUR, matiere.getProfesseur());
        return insert(DataContract.MatiereTable.TABLE_NAME, content);
    }

    /**
     * Ajout d'un élève à une classe
     * @param classeId id classe
     * @param eleveId id élève
     * @return id créé
     */
    public long insertClasseEleve(long classeId, long eleveId) {
        ContentValues content = new ContentValues();
        content.put(DataContract.ClasseEleveTable.CLASSE_ID, classeId);
        content.put(DataContract.ClasseEleveTable.ELEVE_ID, eleveId);
        return insert(DataContract.ClasseEleveTable.TABLE_NAME, content);
    }

    /**
     * Ajout d'une matière à une classe
     * @param classeId id classe
     * @param matiereId id matière
     * @return id créé
     */
    public long insertClasseMatiere(long classeId, long matiereId) {
        ContentValues content = new ContentValues();
        content.put(DataContract.ClasseMatiereTable.CLASSE_ID, classeId);
        content.put(DataContract.ClasseMatiereTable.MATIERE_ID, matiereId);
        return insert(DataContract.ClasseMatiereTable.TABLE_NAME, content);
    }

    /**
     * Création d'une note
     * @param note nouvelle note
     * @return id créé
     */
    public long insertNote(Note note) {
        ContentValues content = new ContentValues();
        content.put(DataContract.NoteTable.CLASSE_ID, note.getClasseId());
        content.put(DataContract.NoteTable.ELEVE_ID, note.getEleveId());
        content.put(DataContract.NoteTable.MATIERE_ID, note.getMatiere().get_id());
        content.put(DataContract.NoteTable.VALEUR, note.getValeur());
        return insert(DataContract.NoteTable.TABLE_NAME, content);
    }

    private int update(String tableName, long _id, ContentValues content) {
        return database.update(tableName, content, BaseColumns._ID + " = " + _id, null);
    }

    /**
     * Modification d'une classe
     * @param _id id classe
     * @param classe classe modifiée
     * @return nombre de lignes modifiées
     */
    public int updateClasse(long _id, Classe classe) {
        ContentValues content = new ContentValues();
        content.put(DataContract.ClasseTable.INTITULE, classe.getIntitule());
        content.put(DataContract.ClasseTable.PROMOTION, classe.getPromotion());
        return update(DataContract.ClasseTable.TABLE_NAME, _id, content);
    }

    /**
     * Modification d'un élève
     * @param _id id élève
     * @param eleve élève modifié
     * @return nombre de lignes modifiées
     */
    public int updateEleve(long _id, Eleve eleve) {
        ContentValues content = new ContentValues();
        content.put(DataContract.EleveTable.NOM, eleve.getNom());
        content.put(DataContract.EleveTable.PRENOM, eleve.getPrenom());
        content.put(DataContract.EleveTable.DATE_NAISSANCE, eleve.getDateNaissance());
        content.put(DataContract.EleveTable.EMAIL, eleve.getEmail());
        content.put(DataContract.EleveTable.TELEPHONE, eleve.getTelephone());
        return update(DataContract.EleveTable.TABLE_NAME, _id, content);
    }

    /**
     * Modification d'une matière
     * @param _id id matière
     * @param matiere matière modifiée
     * @return nombre de lignes modifiées
     */
    public int updateMatiere(long _id, Matiere matiere) {
        ContentValues content = new ContentValues();
        content.put(DataContract.MatiereTable.INTITULE, matiere.getIntitule());
        content.put(DataContract.MatiereTable.DESCRIPTION, matiere.getDescription());
        content.put(DataContract.MatiereTable.PROFESSEUR, matiere.getProfesseur());
        return update(DataContract.MatiereTable.TABLE_NAME, _id, content);
    }

    /**
     * Modification d'une note
     * @param _id id note
     * @param note note modifiée
     * @return nombre de lignes modifiées
     */
    public int updateNote(long _id, Note note) {
        ContentValues content = new ContentValues();
        content.put(DataContract.NoteTable.VALEUR, note.getValeur());
        return update(DataContract.NoteTable.TABLE_NAME, _id, content);
    }

    private void delete(String tableName, long _id) {
        database.delete(tableName, BaseColumns._ID + " = " + _id, null);
    }

    /**
     * Suppression d'une classe
     * @param _id id classe
     */
    public void deleteClasse(long _id) {
        delete(DataContract.ClasseTable.TABLE_NAME, _id);
    }

    /**
     * Suppression d'un élève
     * @param _id id élève
     */
    public void deleteEleve(long _id) {
        delete(DataContract.EleveTable.TABLE_NAME, _id);
    }

    /**
     * Suppression d'une matière
     * @param _id id matière
     */
    public void deleteMatiere(long _id) {
        delete(DataContract.MatiereTable.TABLE_NAME, _id);
    }

    /**
     * Retrait d'un élève d'un classe
     * @param _id id classe-élève
     */
    public void deleteClasseEleve(long _id) {
        delete(DataContract.ClasseEleveTable.TABLE_NAME, _id);
    }

    /**
     * Retrait d'une matière d'une classe
     * @param _id id classe-matière
     */
    public void deleteClasseMatiere(long _id) {
        delete(DataContract.ClasseMatiereTable.TABLE_NAME, _id);
    }

    /**
     * Suppression d'une note
     * @param _id id note
     */
    public void deleteNote(long _id) {
        delete(DataContract.NoteTable.TABLE_NAME, _id);
    }

    public void deleteClasseMatiereLiaison(long classeId, long matiereId) {
        String whereClause = DataContract.ClasseMatiereTable.CLASSE_ID + " = ? AND " +
                DataContract.ClasseMatiereTable.MATIERE_ID + " = ?";
        String[] whereArgs = { String.valueOf(classeId), String.valueOf(matiereId) };
        dbHelper.getWritableDatabase().delete(DataContract.ClasseMatiereTable.TABLE_NAME, whereClause, whereArgs);
    }

    public void deleteClasseEleveLiaison(long classeId, long eleveId) {
        String whereClause = DataContract.ClasseEleveTable.CLASSE_ID + " = ? AND " +
                DataContract.ClasseEleveTable.ELEVE_ID + " = ?";
        String[] whereArgs = { String.valueOf(classeId), String.valueOf(eleveId) };
        dbHelper.getWritableDatabase().delete(DataContract.ClasseEleveTable.TABLE_NAME, whereClause, whereArgs);
    }


}
