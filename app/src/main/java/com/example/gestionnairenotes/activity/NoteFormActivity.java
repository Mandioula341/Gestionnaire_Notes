package com.example.gestionnairenotes.activity;
import com.example.gestionnairenotes.R;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionnairenotes.database.NoteDao;
import com.example.gestionnairenotes.model.Note;

/**
 * NoteFormActivity — Membre 3 Gère la création d'une note (titre, contenu,
 * couleur choisie). La couleur est reçue via l'Intent depuis MainActivity. En
 * mode modification, la note existante est pré-remplie (Membre 4).
 */
public class NoteFormActivity extends AppCompatActivity {

    // Clés des extras Intent
    public static final String EXTRA_COLOR = "extra_color";
    public static final String EXTRA_NOTE_ID = "extra_note_id";
    public static final String EXTRA_MODE = "extra_mode";

    public static final String MODE_CREATE = "create";
    public static final String MODE_EDIT = "edit";

    // Vues
    private EditText editTextTitle;
    private EditText editTextContent;
    private LinearLayout layoutNoteCard;
    private Button btnSave;

    // Données
    private String selectedColor = "#219653"; // couleur par défaut : vert
    private String currentMode = MODE_CREATE;
    private long noteId = -1;

    // DAO (fourni par Membre 1)
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);

        // Initialiser les vues
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        layoutNoteCard = findViewById(R.id.layoutNoteCard);
        btnSave = findViewById(R.id.btnSave);

        // Initialiser le DAO
        noteDao = new NoteDao(this);

        // Récupérer les extras de l'Intent
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_COLOR)) {
                selectedColor = intent.getStringExtra(EXTRA_COLOR);
            }
            if (intent.hasExtra(EXTRA_MODE)) {
                currentMode = intent.getStringExtra(EXTRA_MODE);
            }
            if (intent.hasExtra(EXTRA_NOTE_ID)) {
                noteId = intent.getLongExtra(EXTRA_NOTE_ID, -1);
            }
        }

        // Appliquer la couleur au fond de la carte
        appliquerCouleurFond(selectedColor);

        // Configurer le mode (création ou modification)
        if (MODE_EDIT.equals(currentMode) && noteId != -1) {
            configurerModeModification();
        } else {
            configurerModeCreation();
        }

        // Bouton Créer / Modifier
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enregistrerNote();
            }
        });
    }

    /**
     * Configure l'écran en mode création.
     */
    private void configurerModeCreation() {
        btnSave.setText("Créer");
        editTextTitle.setHint("Titre");
        editTextContent.setHint("Contenu de la note...");
    }

    /**
     * Configure l'écran en mode modification. Pré-remplit les champs avec la
     * note existante. (Utilisé aussi par Membre 4 — la logique de sauvegarde de
     * modif est ici)
     */
    private void configurerModeModification() {
        btnSave.setText("Modifier");

        Note note = noteDao.getNoteById(noteId);
        if (note != null) {
            editTextTitle.setText(note.getTitre());
            editTextContent.setText(note.getContenu());
            selectedColor = note.getCouleur();
            appliquerCouleurFond(selectedColor);
        }
    }

    /**
     * Valide et enregistre la note dans la base de données. Empêche
     * l'enregistrement si le titre ou le contenu est vide.
     */
    private void enregistrerNote() {
        String titre = editTextTitle.getText().toString().trim();
        String contenu = editTextContent.getText().toString().trim();

        // Validation : titre obligatoire
        if (TextUtils.isEmpty(titre)) {
            editTextTitle.setError("Le titre est obligatoire");
            editTextTitle.requestFocus();
            return;
        }

        // Validation : contenu obligatoire
        if (TextUtils.isEmpty(contenu)) {
            editTextContent.setError("Le contenu est obligatoire");
            editTextContent.requestFocus();
            return;
        }

        if (MODE_CREATE.equals(currentMode)) {
            // Créer une nouvelle note
            Note nouvelleNote = new Note(titre, contenu, selectedColor);
            long id = noteDao.insererNote(nouvelleNote);

            if (id != -1) {
                Toast.makeText(this, "Note créée avec succès", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Erreur lors de la création", Toast.LENGTH_SHORT).show();
            }

        } else {
            // Modifier une note existante
            Note noteModifiee = new Note(titre, contenu, selectedColor);
            noteModifiee.setId(noteId);
            int lignesAffectees = noteDao.mettreAJourNote(noteModifiee);

            if (lignesAffectees > 0) {
                Toast.makeText(this, "Note modifiée avec succès", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Erreur lors de la modification", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Applique dynamiquement la couleur choisie au fond de la carte note.
     *
     * @param couleurHex La couleur en format hexadécimal (#RRGGBB)
     */
    private void appliquerCouleurFond(String couleurHex) {
        try {
            int couleur = Color.parseColor(couleurHex);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(24f);
            drawable.setColor(couleur);
            layoutNoteCard.setBackground(drawable);
        } catch (IllegalArgumentException e) {
            // Couleur invalide → on garde la couleur par défaut verte
            layoutNoteCard.setBackgroundColor(Color.parseColor("#219653"));
        }
    }
}
 
