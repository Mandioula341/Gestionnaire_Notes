package com.example.gestionnairenotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gestionnairenotes.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Porte d'entrée des données : toutes les opérations sur les notes passent par ici.
 * Le reste de l'application appelle ces méthodes et n'écrit jamais de SQL directement.
 */
public class NoteDAO {

    private final DatabaseHelper dbHelper;

    public NoteDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // ---------- ÉCRITURE ----------

    // Ajoute une note et renvoie l'id généré par SQLite
    public long ajouterNote(Note note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(DatabaseHelper.TABLE_NOTES, null, construireValeurs(note));
        db.close();
        return id;
    }

    // Modifie une note existante (repérée par son id) ; renvoie le nombre de lignes modifiées
    public int modifierNote(Note note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int lignes = db.update(
                DatabaseHelper.TABLE_NOTES,
                construireValeurs(note),
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{ String.valueOf(note.getId()) }
        );
        db.close();
        return lignes;
    }

    // Supprime une note par son id (bonus)
    public int supprimerNote(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int lignes = db.delete(
                DatabaseHelper.TABLE_NOTES,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{ String.valueOf(id) }
        );
        db.close();
        return lignes;
    }

    // Met une note en favori ou l'en retire (utilisé par le double-clic)
    public int changerFavori(int id, boolean favori) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valeurs = new ContentValues();
        valeurs.put(DatabaseHelper.COLUMN_FAVORI, favori ? 1 : 0);
        int lignes = db.update(
                DatabaseHelper.TABLE_NOTES,
                valeurs,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{ String.valueOf(id) }
        );
        db.close();
        return lignes;
    }

    // ---------- LECTURE ----------

    // Toutes les notes
    public List<Note> listerNotes() {
        return executerRequete(null, null);
    }

    // Uniquement les notes favorites
    public List<Note> listerFavoris() {
        return executerRequete(DatabaseHelper.COLUMN_FAVORI + " = ?", new String[]{ "1" });
    }

    // Les notes dont le titre contient le texte recherché
    public List<Note> rechercherParTitre(String texte) {
        return executerRequete(
                DatabaseHelper.COLUMN_TITRE + " LIKE ?",
                new String[]{ "%" + texte + "%" }
        );
    }

    // ---------- OUTILS PRIVÉS (évitent la duplication) ----------

    // Lit la table notes avec un filtre optionnel, triée de la plus récente à la plus ancienne
    private List<Note> executerRequete(String selection, String[] arguments) {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NOTES,
                null,        // toutes les colonnes
                selection,   // filtre (null = aucun)
                arguments,   // valeurs du filtre
                null, null,
                DatabaseHelper.COLUMN_DATE + " DESC"
        );

        while (cursor.moveToNext()) {
            notes.add(cursorToNote(cursor));
        }
        cursor.close();
        db.close();
        return notes;
    }

    // Transforme une Note en ContentValues (pour l'ajout et la modification)
    private ContentValues construireValeurs(Note note) {
        ContentValues valeurs = new ContentValues();
        valeurs.put(DatabaseHelper.COLUMN_TITRE, note.getTitre());
        valeurs.put(DatabaseHelper.COLUMN_CONTENU, note.getContenu());
        valeurs.put(DatabaseHelper.COLUMN_COULEUR, note.getCouleur());
        valeurs.put(DatabaseHelper.COLUMN_DATE, note.getDate());
        valeurs.put(DatabaseHelper.COLUMN_FAVORI, note.isFavori() ? 1 : 0);
        return valeurs;
    }

    // Transforme la ligne courante du Cursor en objet Note
    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
        note.setTitre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITRE)));
        note.setContenu(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTENU)));
        note.setCouleur(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COULEUR)));
        note.setDate(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)));
        note.setFavori(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FAVORI)) == 1);
        return note;
    }
}
