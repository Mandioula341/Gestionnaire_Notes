package com.example.gestionnairenotes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
        return new NoteViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.tvTitre.setText(note.getTitre());
        holder.tvDate.setText(String.valueOf(note.getDate()));

        // Couleur de fond arrondie
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(24f);
        try {
            drawable.setColor(Color.parseColor(note.getCouleur()));
        } catch (IllegalArgumentException e) {
            drawable.setColor(Color.parseColor("#219653"));
        }
        holder.layoutNoteItem.setBackground(drawable);

        // Icône favori
        holder.ivFavori.setVisibility(note.isFavori() ? View.VISIBLE : View.GONE);

        // GestureDetector pour clic simple et double clic
        GestureDetector gestureDetector = new GestureDetector(
                holder.itemView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (listener != null) listener.onNoteClick(note);
                        return true;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (listener != null) listener.onNoteDoubleClic(note);
                        return true;
                    }

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }
                }
        );

        holder.itemView.setOnTouchListener((v, event) -> {
            boolean result = gestureDetector.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            return result;
        });

        holder.itemView.setOnClickListener(v -> {
            // nécessaire pour que setOnTouchListener fonctionne correctement
        });

        holder.itemView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layoutNoteItem;
        TextView tvTitre;
        TextView tvDate;
        ImageView ivFavori;

        NoteViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            layoutNoteItem = itemView.findViewById(R.id.layoutNoteItem);
            tvTitre = itemView.findViewById(R.id.tvTitre);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivFavori = itemView.findViewById(R.id.ivFavori);
        }
    }
}