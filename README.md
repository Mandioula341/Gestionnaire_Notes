#  Gestionnaire de Notes

Application Android de gestion de notes  développée dans le cadre du TP final de **Développement Mobile ** à l'École Supérieure Polytechnique (ESP/UCAD).

---

##  Description

L'application permet à un utilisateur de gérer ses notes  directement depuis son téléphone Android. Toutes les données sont sauvegardées localement et conservées après fermeture et redémarrage de l'application.

---

##  Fonctionnalités

- ✅ Créer une note avec un titre, un contenu et une couleur
- ✅ Modifier une note existante
- ✅ Supprimer une note
- ✅ Mettre une note en favori ou la retirer (double clic)
- ✅ Filtrer les notes favorites
- ✅ Rechercher une note par son titre
- ✅ Attribuer une couleur à une note (palette de 6 couleurs)
- ✅ Persistance locale des données (SQLite)

---

##  Écrans de l'application

| Écran | Description |
|-------|-------------|
| Liste vide | Message "Aucune notes" + bouton flottant d'ajout |
| Liste des notes | Barre de recherche, bouton Favoris, liste colorée |
| Palette de couleurs | Sélection de la couleur avant création |
| Création d'une note | Formulaire titre + contenu + couleur |
| Modification d'une note | Formulaire pré-rempli avec les données existantes |

---

##  Technologies utilisées

- **Langage** : Java
- **Plateforme** : Android (SDK minimum : 21)
- **Base de données** : SQLite (via SQLiteOpenHelper)
- **Architecture** : Séparation model / database / adapter / activity / utils
- **Gestion de version** : Git / GitHub

---

## 📁 Structure du projet

```
application/
└── src/main/java/com/example/gestionnairenotes/
    ├── model/
    │   └── Note.java               # Objet de données (titre, contenu, couleur, date, favori)
    ├── database/
    │   ├── DatabaseHelper.java     # Création et gestion de la base SQLite
    │   └── NoteDAO.java            # Opérations CRUD sur les notes
    ├── adapter/
    │   └── NoteAdapter.java        # Affichage de la liste des notes (RecyclerView)
    ├── activity/
    │   ├── MainActivity.java       # Écran principal (liste, recherche, favoris)
    │   └── NoteFormActivity.java   # Formulaire de création et modification
    └── utils/
        ├── DateUtils.java          # Formatage des dates (timestamp → "01 Juin 2026")
        ├── ValidationUtils.java    # Validation des champs avant enregistrement
        └── ColorUtils.java         # Conversion des couleurs (hex → couleur Android)

res/
├── layout/
│   ├── activity_main.xml           # Layout écran principal
│   ├── activity_note_form.xml      # Layout formulaire
│   └── item_note.xml               # Layout d'un élément de la liste
├── drawable/                       # Icônes et formes (cercles palette, étoile favori)
└── values/
    ├── colors.xml                  # Couleurs de l'application
    ├── styles.xml                  # Typographie (Titre1 à Text2)
    ├── strings.xml                 # Textes de l'interface
    ├── dimens.xml                  # Espacements et tailles
    └── arrays.xml                  # Tableau des couleurs de la palette
```

---

##  Palette de couleurs

| Couleur | Code Hex |
|---------|----------|
| Vert    | #219653  |
| Rouge   | #EB5757  |
| Bleu    | #2F80ED  |
| Jaune   | #F2C94C  |
| Orange  | #F2994A  |
| Gris    | #828282  |

---

##  Installation et lancement

### Prérequis
- Android Studio (version récente recommandée)
- JDK 11 ou supérieur
- Un émulateur Android ou un téléphone Android (API 21+)

### Étapes

1. Cloner le dépôt :
```bash
git clone https://github.com/Mandioula341/Gestionnaire_Notes
```

2. Ouvrir le projet dans Android Studio :
   - File > Open > sélectionner le dossier `Gestionnaire_Notes`
   - Attendre la synchronisation Gradle

3. Lancer l'application :
   - Brancher un téléphone Android ou démarrer un émulateur
   - Cliquer sur le bouton **Run** (▶) dans Android Studio

---

## Répartition des rôles

| Membre   | Rôle                                                                              |
|----------|-----------------------------------------------------------------------------------|
| Membre 1 | `model/` + `database/` (Note.java, DatabaseHelper.java, NoteDAO.java)             |
| Membre 2 | `adapter/` + `item_note.xml` + `activity_main.xml`                                | 
| Membre 3 | `NoteFormActivity.java` (création) + `activity_note_form.xml` + `drawable/`       |
| Membre 4 | `NoteFormActivity.java` (modification) + logique favoris dans `MainActivity.java` |
| Membre 5 | `utils/` + intégration + `values/` + `README.md` |

---

##  Contexte académique

- **Module** : Développement Mobile
- **Établissement** : École Supérieure Polytechnique (ESP/UCAD) — Dakar, Sénégal
- **Année** : L2 GLSI-A — 2025/2026
- **Encadrant** : Amadou NDIAYE 
