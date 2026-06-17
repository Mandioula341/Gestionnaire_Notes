package com.example.gestionnairenotes.model;

/**
 * Représente une note de l'application.
 * C'est un simple objet de données : il décrit une note et ne contient
 * aucune logique de base de données (le SQL reste dans la partie database).
 */
public class Note {

    private int id;
    private String titre;
    private String contenu;
    private String couleur;   // code hexadécimal, ex : "219653" (vert)
    private long date;        // timestamp en millisecondes (sera mis en forme à l'affichage)
    private boolean favori;

    // Constructeur vide : pour créer une note puis remplir ses champs un par un
    public Note() {
    }

    // Constructeur complet : pour créer une note avec toutes ses informations d'un coup
    public Note(int id, String titre, String contenu, String couleur, long date, boolean favori) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.couleur = couleur;
        this.date = date;
        this.favori = favori;
    }

    // Getters et setters : lire et modifier chaque champ depuis l'extérieur

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isFavori() {
        return favori;
    }

    public void setFavori(boolean favori) {
        this.favori = favori;
    }
}
