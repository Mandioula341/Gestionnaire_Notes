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

## 📁 Structure du projet

```
GestionnaireNotes/
└── app/src/main/
    ├── java/com/example/gestionnairenotes/
    │   ├── model/
    │   │   └── Note.java                   # Objet de données (id, titre, contenu, couleur, date, favori)
    │   ├── database/
    │   │   ├── DatabaseHelper.java         # Création et gestion de la base SQLite
    │   │   └── NoteDao.java                # Opérations CRUD (créer, lire, modifier, basculer favori)
    │   ├── adapter/
    │   │   └── NoteAdapter.java            # Affichage de la liste des notes (RecyclerView + gestion clics)
    │   ├── activity/
    │   │   ├── MainActivity.java           # Écran principal (liste, recherche, favoris, palette)
    │   │   └── NoteFormActivity.java       # Formulaire de création et modification d'une note
    │   └── utils/
    │       └── ColorUtils.java             # Constantes de couleurs et application sur les vues
    ├── res/
    │   ├── layout/
    │   │   ├── activity_main.xml           # Layout écran principal
    │   │   ├── activity_note_form.xml      # Layout formulaire création/modification
    │   │   └── item_note.xml               # Layout d'un élément de la liste
    │   ├── drawable/
    │   │   ├── bg_btn_favoris.xml          # Fond arrondi du bouton Favoris
    │   │   ├── bg_note_form.xml            # Fond coloré arrondi du formulaire
    │   │   ├── bg_search.xml               # Fond arrondi de la barre de recherche
    │   │   ├── btn_save.xml                # Fond du bouton Créer/Modifier
    │   │   ├── circle_blue.xml             # Cercle bleu de la palette (#2F80ED)
    │   │   ├── circle_green.xml            # Cercle vert de la palette (#219653)
    │   │   ├── circle_grey.xml             # Cercle gris de la palette (#828282)
    │   │   ├── circle_orange.xml           # Cercle orange de la palette (#F2994A)
    │   │   ├── circle_red.xml              # Cercle rouge de la palette (#EB5757)
    │   │   ├── circle_yellow.xml           # Cercle jaune de la palette (#F2C94C)
    │   │   └── rounded_note.xml            # Fond arrondi des cartes de notes
    │   └── values/
    │       ├── colors.xml                  # Couleurs de l'application (noir, blanc, vert, rouge...)
    │       ├── strings.xml                 # Textes de l'interface (titres, messages, boutons)
    │       └── styles.xml                  # Typographie (Titre1 à Text2) + thème AppTheme
    └── AndroidManifest.xml                 # Déclaration des activités et permissions
```

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

## ⚠️ Notes techniques

### Double clic et émulateur Android

La fonctionnalité de gestion des favoris par double clic est implémentée via 
`GestureDetector.SimpleOnGestureListener` avec la méthode `onDoubleTap()`, 
conformément aux bonnes pratiques Android.

**Comportement selon l'environnement :**

| Environnement | Comportement |
|---------------|-------------|
| Téléphone physique | ✅ Double clic fonctionne parfaitement |
| Émulateur Android Studio (souris) | ⚠️ Double clic non détecté (limitation connue de l'émulateur) |

**Explication technique :**  
Sur émulateur, les événements tactiles sont simulés via la souris du PC. 
Android Studio ne transmet pas correctement les événements `ACTION_DOWN` 
successifs nécessaires à la détection du double tap via `GestureDetector`. 
Ce comportement est une **limitation connue de l'émulateur**, 
et non un bug du code.

**Solution de contournement sur émulateur :**  
Sélectionner la note avec les touches directionnelles puis appuyer sur 
Entrée deux fois rapidement.

**Code implémenté :**
```java
new GestureDetector.SimpleOnGestureListener() {
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (listener != null) listener.onNoteDoubleClic(note);
        return true;
    }
}
```

##  Contexte académique

- **Module** : Développement Mobile
- **Établissement** : École Supérieure Polytechnique (ESP/UCAD) — Dakar, Sénégal
- **Année** : L2 GLSI-A — 2025/2026
- **Encadrant** : Amadou NDIAYE 
