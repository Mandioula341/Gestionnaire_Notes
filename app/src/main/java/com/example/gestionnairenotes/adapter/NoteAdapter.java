package com.example.gestionnairenotes.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionnairenotes.R;
import com.example.gestionnairenotes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    // Interface pour les clics
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
        void onNoteDoubleClic(Note note);
    }

    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListener listener;

    public NoteAdapter(OnNoteClickListener listener) {
        this.listener = listener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        // Afficher titre et date
        holder.tvTitre.setText(note.getTitre());
        holder.tvDate.setText(String.valueOf(note.getDate()));

        // Appliquer la couleur de fond arrondie
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(24f);
        try {
            drawable.setColor(Color.parseColor(note.getCouleur()));
        } catch (IllegalArgumentException e) {
            drawable.setColor(Color.parseColor("#219653"));
        }
        holder.layoutNoteItem.setBackground(drawable);

        // Afficher ou cacher l'icône favori
        if (note.isFavori()) {
            holder.ivFavori.setVisibility(View.VISIBLE);
        } else {
            holder.ivFavori.setVisibility(View.GONE);
        }

        // Clic simple → ouvrir modification
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNoteClick(note);
            }
        });

        // Double clic → basculer favori
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            private int nbClics = 0;
            private final long DELAI = 300;

            @Override
            public void onClick(View v) {
                nbClics++;
                v.postDelayed(() -> {
                    if (nbClics == 1) {
                        // Clic simple
                        if (listener != null) listener.onNoteClick(note);
                    } else if (nbClics >= 2) {
                        // Double clic
                        if (listener != null) listener.onNoteDoubleClic(note);
                    }
                    nbClics = 0;
                }, DELAI);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // ViewHolder
    static class NoteViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layoutNoteItem;
        TextView tvTitre;
        TextView tvDate;
        ImageView ivFavori;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutNoteItem = itemView.findViewById(R.id.layoutNoteItem);
            tvTitre = itemView.findViewById(R.id.tvTitre);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivFavori = itemView.findViewById(R.id.ivFavori);
        }
    }
} 
