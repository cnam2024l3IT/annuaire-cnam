package com.example.annuairecnam.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
        return database.query(tableName, columns, null, null, null,
                null, null);
    }

    private Cursor fetchOne(String tableName, String[] columns, long _id) {
        return database.query(tableName, columns, DataContract.InfoTable._ID + " = " + _id, null, null,
                null, null);
    }

    private Cursor fetchSQL(String sql, String[] params) {
        return database.rawQuery(sql, params);
    }

    public ArrayList<Classe> getAllClasses() {
        String[] columns = new String[] { DataContract.ClasseTable._ID,DataContract.ClasseTable.INTITULE,
                DataContract.ClasseTable.PROMOTION };
        ArrayList<Classe> classes = new ArrayList<>();
        try (Cursor cursor = fetchAll(DataContract.ClasseTable.TABLE_NAME, columns)) {
            if (cursor.moveToFirst()) {
                do {
                    classes.add(new Classe(cursor.getLong(0), cursor.getString(1),
                            cursor.getString(2)));
                } while (cursor.moveToNext());
            }
        }
        return classes;
    }

    public ArrayList<Classe> getClassesByEleveId(long _id) {
        ArrayList<Classe> classes = new ArrayList<>();
        try (Cursor cursor = fetchSQL(DataContract.SQL_CLASSES_BY_ELEVE_ID, new String[]{Long.toString(_id)})) {
            if (cursor.moveToFirst()) {
                do {
                    classes.add(new Classe(cursor.getLong(0), cursor.getString(1),
                            cursor.getString(2)));
                } while (cursor.moveToNext());
            }
        }
        return classes;
    }

    public Classe getClasse(long _id) throws EntityNotFoundException {
        String[] columns = new String[] { DataContract.ClasseTable._ID,DataContract.ClasseTable.INTITULE,
                DataContract.ClasseTable.PROMOTION };
        try (Cursor cursor = fetchOne(DataContract.ClasseTable.TABLE_NAME, columns, _id)) {
            if (cursor.moveToFirst())
                return new Classe(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            else throw new EntityNotFoundException("Aucune classe n'a été trouvée avec l'id " + _id);
        }
    }

    public ArrayList<Eleve> getAllEleves() {
        String[] columns = new String[] { DataContract.EleveTable._ID,DataContract.EleveTable.NOM,
                DataContract.EleveTable.PRENOM };
        ArrayList<Eleve> eleves = new ArrayList<>();
        try (Cursor cursor = fetchAll(DataContract.EleveTable.TABLE_NAME, columns)) {
            if (cursor.moveToFirst()) {
                do {
                    eleves.add(new Eleve(cursor.getLong(0), cursor.getString(1),
                            cursor.getString(2)));
                } while (cursor.moveToNext());
            }
        }
        return eleves;
    }

    public ArrayList<Eleve> getElevesByClasseId(long _id) {
        ArrayList<Eleve> eleves = new ArrayList<>();
        try (Cursor cursor = fetchSQL(DataContract.SQL_ELEVES_BY_CLASSE_ID, new String[]{Long.toString(_id)})) {
            if (cursor.moveToFirst()) {
                do {
                    eleves.add(new Eleve(cursor.getLong(0), cursor.getString(1),
                            cursor.getString(2)));
                } while (cursor.moveToNext());
            }
        }
        return eleves;
    }

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

    public ArrayList<Matiere> getAllMatieres() {
        String[] columns = new String[] { DataContract.MatiereTable._ID,DataContract.MatiereTable.INTITULE };
        ArrayList<Matiere> matieres = new ArrayList<>();
        try (Cursor cursor = fetchAll(DataContract.MatiereTable.TABLE_NAME, columns)) {
            if (cursor.moveToFirst()) {
                do {
                    matieres.add(new Matiere(cursor.getLong(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
        }
        return matieres;
    }

    public ArrayList<Matiere> getMatieresByClasseId(long _id) {
        ArrayList<Matiere> matieres = new ArrayList<>();
        try (Cursor cursor = fetchSQL(DataContract.SQL_MATIERES_BY_CLASSE_ID, new String[]{Long.toString(_id)})) {
            if (cursor.moveToFirst()) {
                do {
                    matieres.add(new Matiere(cursor.getLong(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
        }
        return matieres;
    }

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

    public ArrayList<Note> getNotesByClasseIdAndEleveId(long classeId, long eleveId) {
        ArrayList<Note> notes = new ArrayList<>();
        try (Cursor cursor = fetchSQL(DataContract.SQL_NOTES_BY_CLASSE_ID_AND_ELEVE_ID,
                new String[]{Long.toString(classeId),Long.toString(eleveId)})) {
            if (cursor.moveToFirst()) {
                do {
                    notes.add(new Note(cursor.getLong(0), new Matiere(cursor.getLong(1),
                            cursor.getString(2))));
                } while (cursor.moveToNext());
            }
        }
        return notes;
    }

    private long insert(String tableName, ContentValues content) {
        return database.insert(tableName, null, content);
    }

    public long insertClasse(Classe classe) {
        ContentValues content = new ContentValues();
        content.put(DataContract.ClasseTable.INTITULE, classe.getIntitule());
        content.put(DataContract.ClasseTable.PROMOTION, classe.getPromotion());
        return insert(DataContract.ClasseTable.TABLE_NAME, content);
    }

    public long insertEleve(Eleve eleve) {
        ContentValues content = new ContentValues();
        content.put(DataContract.EleveTable.NOM, eleve.getNom());
        content.put(DataContract.EleveTable.PRENOM, eleve.getPrenom());
        content.put(DataContract.EleveTable.DATE_NAISSANCE, eleve.getDateNaissance());
        content.put(DataContract.EleveTable.EMAIL, eleve.getEmail());
        content.put(DataContract.EleveTable.TELEPHONE, eleve.getTelephone());
        return insert(DataContract.EleveTable.TABLE_NAME, content);
    }

    public long insertMatiere(Matiere matiere) {
        ContentValues content = new ContentValues();
        content.put(DataContract.MatiereTable.INTITULE, matiere.getIntitule());
        content.put(DataContract.MatiereTable.DESCRIPTION, matiere.getDescription());
        content.put(DataContract.MatiereTable.PROFESSEUR, matiere.getProfesseur());
        return insert(DataContract.MatiereTable.TABLE_NAME, content);
    }

    public long insertClasseEleve(long classeId, long eleveId) {
        ContentValues content = new ContentValues();
        content.put(DataContract.ClasseEleveTable.CLASSE_ID, classeId);
        content.put(DataContract.ClasseEleveTable.ELEVE_ID, eleveId);
        return insert(DataContract.ClasseEleveTable.TABLE_NAME, content);
    }

    public long insertClasseMatiere(long classeId, long matiereId) {
        ContentValues content = new ContentValues();
        content.put(DataContract.ClasseMatiereTable.CLASSE_ID, classeId);
        content.put(DataContract.ClasseMatiereTable.MATIERE_ID, matiereId);
        return insert(DataContract.ClasseMatiereTable.TABLE_NAME, content);
    }

    public long insertNote(Note note) {
        ContentValues content = new ContentValues();
        content.put(DataContract.NoteTable.CLASSE_ID, note.getClasseId());
        content.put(DataContract.NoteTable.ELEVE_ID, note.getEleveId());
        content.put(DataContract.NoteTable.MATIERE_ID, note.getMatiere().get_id());
        content.put(DataContract.NoteTable.VALEUR, note.getValeur());
        return insert(DataContract.NoteTable.TABLE_NAME, content);
    }

    private int update(String tableName, long _id, ContentValues content) {
        return database.update(tableName, content, DataContract.InfoTable._ID + " = " + _id, null);
    }

    public int updateClasse(long _id, Classe classe) {
        ContentValues content = new ContentValues();
        content.put(DataContract.ClasseTable.INTITULE, classe.getIntitule());
        content.put(DataContract.ClasseTable.PROMOTION, classe.getPromotion());
        return update(DataContract.ClasseTable.TABLE_NAME, _id, content);
    }

    public int updateEleve(long _id, Eleve eleve) {
        ContentValues content = new ContentValues();
        content.put(DataContract.EleveTable.NOM, eleve.getNom());
        content.put(DataContract.EleveTable.PRENOM, eleve.getPrenom());
        content.put(DataContract.EleveTable.DATE_NAISSANCE, eleve.getDateNaissance());
        content.put(DataContract.EleveTable.EMAIL, eleve.getEmail());
        content.put(DataContract.EleveTable.TELEPHONE, eleve.getTelephone());
        return update(DataContract.EleveTable.TABLE_NAME, _id, content);
    }

    public int updateMatiere(long _id, Matiere matiere) {
        ContentValues content = new ContentValues();
        content.put(DataContract.MatiereTable.INTITULE, matiere.getIntitule());
        content.put(DataContract.MatiereTable.DESCRIPTION, matiere.getDescription());
        content.put(DataContract.MatiereTable.PROFESSEUR, matiere.getProfesseur());
        return update(DataContract.MatiereTable.TABLE_NAME, _id, content);
    }

    public int updateNote(long _id, Note note) {
        ContentValues content = new ContentValues();
        content.put(DataContract.NoteTable.VALEUR, note.getValeur());
        return update(DataContract.NoteTable.TABLE_NAME, _id, content);
    }

    private void delete(String tableName, long _id) {
        database.delete(tableName, DataContract.InfoTable._ID + " = " + _id, null);
    }

    public void deleteClasse(long _id) {
        delete(DataContract.ClasseTable.TABLE_NAME, _id);
    }

    public void deleteEleve(long _id) {
        delete(DataContract.EleveTable.TABLE_NAME, _id);
    }

    public void deleteMatiere(long _id) {
        delete(DataContract.MatiereTable.TABLE_NAME, _id);
    }

    public void deleteClasseEleve(long _id) {
        delete(DataContract.ClasseEleveTable.TABLE_NAME, _id);
    }

    public void deleteClasseMatiere(long _id) {
        delete(DataContract.ClasseMatiereTable.TABLE_NAME, _id);
    }

    public void deleteNote(long _id) {
        delete(DataContract.NoteTable.TABLE_NAME, _id);
    }

}
