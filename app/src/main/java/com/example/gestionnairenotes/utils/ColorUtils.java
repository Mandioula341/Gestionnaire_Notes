package com.example.gestionnairenotes.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

public class ColorUtils {

    // Liste des couleurs disponibles (hex)
    public static final String VERT   = "#219653";
    public static final String ROUGE  = "#EB5757";
    public static final String BLEU   = "#2F80ED";
    public static final String JAUNE  = "#F2C94C";
    public static final String ORANGE = "#F2994A";
    public static final String GRIS   = "#828282";

    public static final String[] TOUTES_COULEURS = {
        VERT, ROUGE, BLEU, JAUNE, ORANGE, GRIS
    };

    /**
     * Applique une couleur arrondie comme fond d'une vue
     */
    public static void appliquerCouleurArrondie(View vue, String couleurHex) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(24f);
        try {
            drawable.setColor(Color.parseColor(couleurHex));
        } catch (IllegalArgumentException e) {
            drawable.setColor(Color.parseColor(VERT));
        }
        vue.setBackground(drawable);
    }

    /**
     * Applique une couleur circulaire comme fond d'une vue (pour la palette)
     */
    public static void appliquerCouleurCercle(View vue, String couleurHex) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        try {
            drawable.setColor(Color.parseColor(couleurHex));
        } catch (IllegalArgumentException e) {
            drawable.setColor(Color.parseColor(VERT));
        }
        vue.setBackground(drawable);
    }
} 
