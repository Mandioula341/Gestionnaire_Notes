package com.example.gestionnairenotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.gestionnairenotes.model.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteDao {

    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public NoteDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void ouvrirEcriture() { db = dbHelper.getWritableDatabase(); }
    private void ouvrirLecture()  { db = dbHelper.getReadableDatabase(); }
    private void fermer()         { dbHelper.close(); }

    public long creerNote(Note note) {
        ouvrirEcriture();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TITRE,   note.getTitre());
        values.put(DatabaseHelper.COL_CONTENU, note.getContenu());
        values.put(DatabaseHelper.COL_COULEUR, note.getCouleur());
        values.put(DatabaseHelper.COL_FAVORI,  note.isFavori() ? 1 : 0);
        values.put(DatabaseHelper.COL_DATE,    note.getDate());
        long id = db.insert(DatabaseHelper.TABLE_NOTES, null, values);
        fermer();
        return id;
    }

    public long insererNote(Note note) { return creerNote(note); }

    public List<Note> getToutesLesNotes() {
        ouvrirLecture();
        List<Note> notes = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NOTES,
                null, null, null, null, null,
                DatabaseHelper.COL_DATE + " DESC");
        if (cursor.moveToFirst()) {
            do { notes.add(cursorVersNote(cursor)); }
            while (cursor.moveToNext());
        }
        cursor.close();
        fermer();
        return notes;
    }

    public List<Note> getNotesFavorites() {
        ouvrirLecture();
        List<Note> notes = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NOTES,
                null, DatabaseHelper.COL_FAVORI + " = 1",
                null, null, null,
                DatabaseHelper.COL_DATE + " DESC");
        if (cursor.moveToFirst()) {
            do { notes.add(cursorVersNote(cursor)); }
            while (cursor.moveToNext());
        }
        cursor.close();
        fermer();
        return notes;
    }

    public List<Note> rechercherNotes(String motCle) {
        ouvrirLecture();
        List<Note> notes = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NOTES,
                null, DatabaseHelper.COL_TITRE + " LIKE ?",
                new String[]{"%" + motCle + "%"}, null, null,
                DatabaseHelper.COL_DATE + " DESC");
        if (cursor.moveToFirst()) {
            do { notes.add(cursorVersNote(cursor)); }
            while (cursor.moveToNext());
        }
        cursor.close();
        fermer();
        return notes;
    }

    public int modifierNote(Note note) {
        ouvrirEcriture();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TITRE,   note.getTitre());
        values.put(DatabaseHelper.COL_CONTENU, note.getContenu());
        values.put(DatabaseHelper.COL_COULEUR, note.getCouleur());
        values.put(DatabaseHelper.COL_FAVORI,  note.isFavori() ? 1 : 0);
        values.put(DatabaseHelper.COL_DATE,    note.getDate());
        int rows = db.update(DatabaseHelper.TABLE_NOTES, values,
                DatabaseHelper.COL_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        fermer();
        return rows;
    }

    public int mettreAJourNote(Note note) { return modifierNote(note); }

    public Note getNoteById(long id) {
        ouvrirLecture();
        Note note = null;
        Cursor cursor = db.query(DatabaseHelper.TABLE_NOTES,
                null, DatabaseHelper.COL_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) { note = cursorVersNote(cursor); }
        cursor.close();
        fermer();
        return note;
    }

    public void basculerFavori(Note note) {
        note.setFavori(!note.isFavori());
        modifierNote(note);
    }

    private Note cursorVersNote(Cursor cursor) {
        return new Note(
                cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TITRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CONTENU)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_COULEUR)),
                cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FAVORI)) == 1
        );
    }
}
