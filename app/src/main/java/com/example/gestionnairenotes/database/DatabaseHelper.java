package com.example.gestionnairenotes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Gère le fichier de base de données SQLite de l'application.
 * Son rôle : créer la base et la table "notes" la première fois,
 * et gérer les changements de version du schéma.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom du fichier de base de données et version du schéma
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    // Noms de la table et des colonnes (centralisés ici pour éviter les fautes
    // de frappe et la duplication : le NoteDAO les réutilisera).
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_CONTENU = "contenu";
    public static final String COLUMN_COULEUR = "couleur";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_FAVORI = "favori";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Appelée UNE SEULE FOIS, à la création de la base.
     * C'est ici qu'on crée la table "notes".
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NOTES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITRE + " TEXT NOT NULL, " +
                COLUMN_CONTENU + " TEXT, " +
                COLUMN_COULEUR + " TEXT, " +
                COLUMN_DATE + " INTEGER, " +
                COLUMN_FAVORI + " INTEGER DEFAULT 0" +
                ")";
        db.execSQL(createTable);
    }

    /**
     * Appelée quand on augmente DATABASE_VERSION (changement de structure).
     * Version simple pour un projet scolaire : on supprime puis on recrée la table.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}
