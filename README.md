# Gestionnaire de Notes

Application Android de gestion de notes personnelles, réalisée dans le cadre de l'examen de développement mobile (TP Mobile 2025/2026).

## Fonctionnalités
- Créer, consulter et modifier des notes
- Attribuer une couleur à une note
- Marquer ou retirer une note des favoris (double-clic)
- Rechercher une note par son titre
- Filtrer pour n'afficher que les favoris
- Sauvegarde locale : les notes sont conservées après la fermeture de l'application

## Technologies
- Android (Java)
- Persistance locale avec SQLite (SQLiteOpenHelper)
- RecyclerView pour l'affichage de la liste

## Organisation du code
Le code source se trouve dans app/src/main/java/com/example/gestionnairenotes/ :
- model/ : l'objet Note
- database/ : accès aux données (DatabaseHelper, NoteDAO)
- adapter/ : affichage de la liste (NoteAdapter)
- activity/ : les écrans (MainActivity, NoteFormActivity)
- utils/ : utilitaires (ColorUtils)

## Répartition du travail
Projet développé en équipe de 5, chacun sur sa branche :

| Branche               | Domaine                                | Membre |
|-----------------------|----------------------------------------|--------|
| feature/database      | Base de données (model + database)     | …      |
| feature/list-screen   | Écran liste, recherche, filtre favoris | …      |
| feature/create-note   | Création d'une note                    | …      |
| feature/edit-favorite | Modification et gestion des favoris    | …      |
| feature/integration   | Intégration et assemblage              | …      |

## Lancer le projet
1. Cloner le dépôt : git clone https://github.com/Mandioula341/Gestionnaire_Notes.git
2. Ouvrir le dossier dans Android Studio.
3. Laisser Gradle se synchroniser, puis lancer l'application sur un émulateur ou un appareil Android.
