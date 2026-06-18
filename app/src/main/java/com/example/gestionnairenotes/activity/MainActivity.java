package com.example.gestionnairenotes.activity;
import com.example.gestionnairenotes.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionnairenotes.adapter.NoteAdapter;
import com.example.gestionnairenotes.database.NoteDao;
import com.example.gestionnairenotes.model.Note;
import com.example.gestionnairenotes.utils.ColorUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_NOTE = 1;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteDao noteDao;
    private EditText searchBar;
    private Button btnFavoris;
    private TextView tvAucuneNote;
    private FloatingActionButton fabAjouter;

    // Palette de couleurs
    private LinearLayout layoutPalette;
    private ImageView cercleVert, cercleRouge, cercleBleu;
    private ImageView cercleJaune, cercleOrange, cercleGris;

    private boolean filtreActif = false;
    private boolean paletteVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les vues
        recyclerView   = findViewById(R.id.recyclerView);
        searchBar      = findViewById(R.id.searchBar);
        btnFavoris     = findViewById(R.id.btnFavoris);
        tvAucuneNote   = findViewById(R.id.tvAucuneNote);
        fabAjouter     = findViewById(R.id.fabAjouter);
        layoutPalette  = findViewById(R.id.layoutPalette);
        cercleVert     = findViewById(R.id.cercleVert);
        cercleRouge    = findViewById(R.id.cercleRouge);
        cercleBleu     = findViewById(R.id.cercleBleu);
        cercleJaune    = findViewById(R.id.cercleJaune);
        cercleOrange   = findViewById(R.id.cercleOrange);
        cercleGris     = findViewById(R.id.cercleGris);

        noteDao = new NoteDao(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adapter avec clic simple et double clic
        adapter = new NoteAdapter(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                ouvrirModification(note);
            }

            @Override
            public void onNoteDoubleClic(Note note) {
                noteDao.basculerFavori(note);
                chargerNotes();
            }
        });

        recyclerView.setAdapter(adapter);

        // Recherche en temps réel
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chargerNotes();
            }
        });

        // Filtre Favoris
        btnFavoris.setOnClickListener(v -> {
            filtreActif = !filtreActif;
            btnFavoris.setAlpha(filtreActif ? 1.0f : 0.5f);
            chargerNotes();
        });

        // FAB → afficher/cacher palette
        fabAjouter.setOnClickListener(v -> {
            paletteVisible = !paletteVisible;
            layoutPalette.setVisibility(paletteVisible ? View.VISIBLE : View.GONE);
        });

        // Clics sur les cercles de couleur
        cercleVert.setOnClickListener(v   -> ouvrirCreation(ColorUtils.VERT));
        cercleRouge.setOnClickListener(v  -> ouvrirCreation(ColorUtils.ROUGE));
        cercleBleu.setOnClickListener(v   -> ouvrirCreation(ColorUtils.BLEU));
        cercleJaune.setOnClickListener(v  -> ouvrirCreation(ColorUtils.JAUNE));
        cercleOrange.setOnClickListener(v -> ouvrirCreation(ColorUtils.ORANGE));
        cercleGris.setOnClickListener(v   -> ouvrirCreation(ColorUtils.GRIS));

        chargerNotes();
    }

    private void ouvrirCreation(String couleur) {
        paletteVisible = false;
        layoutPalette.setVisibility(View.GONE);
        Intent intent = new Intent(this, NoteFormActivity.class);
        intent.putExtra(NoteFormActivity.EXTRA_MODE, NoteFormActivity.MODE_CREATE);
        intent.putExtra(NoteFormActivity.EXTRA_COLOR, couleur);
        startActivityForResult(intent, REQUEST_NOTE);
    }

    private void ouvrirModification(Note note) {
        Intent intent = new Intent(this, NoteFormActivity.class);
        intent.putExtra(NoteFormActivity.EXTRA_MODE, NoteFormActivity.MODE_EDIT);
        intent.putExtra(NoteFormActivity.EXTRA_NOTE_ID, (long) note.getId());
        intent.putExtra(NoteFormActivity.EXTRA_COLOR, note.getCouleur());
        startActivityForResult(intent, REQUEST_NOTE);
    }

    private void chargerNotes() {
        List<Note> notes;
        String recherche = searchBar.getText().toString().trim();

        if (filtreActif) {
            notes = noteDao.getNotesFavorites();
        } else if (!recherche.isEmpty()) {
            notes = noteDao.rechercherNotes(recherche);
        } else {
            notes = noteDao.getToutesLesNotes();
        }

        adapter.setNotes(notes);
        tvAucuneNote.setVisibility(notes.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(notes.isEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NOTE && resultCode == RESULT_OK) {
            chargerNotes();
        }
    }
}
