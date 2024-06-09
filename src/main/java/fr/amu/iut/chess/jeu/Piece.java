package fr.amu.iut.chess.jeu;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Classe abstraite représentant une pièce du jeu d'échecs.
 * Cette classe sera héritée par toutes les autres pièces spécifiques du jeu.
 */
public abstract class Piece {
    private final boolean isWhite;  // Couleur de la pièce : true pour blanc, false pour noir
    private ImageView image;  // Image représentant la pièce
    private String nom;       // Nom de la pièce (Roi, Reine, Cavalier, etc.)
    private int x;            // Coordonnée x de la pièce sur l'échiquier
    private int y;            // Coordonnée y de la pièce sur l'échiquier

    /**
     * Constructeur de la classe Piece.
     *
     * @param isWhite Indique si la pièce est blanche (true) ou noire (false).
     * @param x       Coordonnée x initiale de la pièce sur l'échiquier.
     * @param y       Coordonnée y initiale de la pièce sur l'échiquier.
     */
    public Piece(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
    }

    /**
     * Obtient la coordonnée x de la pièce.
     *
     * @return La coordonnée x de la pièce.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtient la coordonnée y de la pièce.
     *
     * @return La coordonnée y de la pièce.
     */
    public int getY() {
        return y;
    }

    /**
     * Déplace la pièce vers de nouvelles coordonnées.
     *
     * @param x Nouvelle coordonnée x.
     * @param y Nouvelle coordonnée y.
     */
    public void move(int x, int y) {
        setCoordonne(x, y);
    }

    /**
     * Méthode abstraite pour vérifier si un mouvement est valide.
     *
     * @param x       Coordonnée x de destination.
     * @param y       Coordonnée y de destination.
     * @param plateau Plateau de jeu contenant toutes les pièces.
     * @return true si le mouvement est valide, false sinon.
     */
    public abstract boolean isValide(int x, int y, ArrayList<ArrayList<Piece>> plateau);

    /**
     * Définit les nouvelles coordonnées de la pièce.
     *
     * @param x Nouvelle coordonnée x.
     * @param y Nouvelle coordonnée y.
     */
    public void setCoordonne(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Indique si la pièce est blanche.
     *
     * @return true si la pièce est blanche, false si elle est noire.
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * Obtient l'image représentant la pièce.
     *
     * @return L'image de la pièce.
     */
    public ImageView getImage() {
        return image;
    }

    /**
     * Définit l'image représentant la pièce.
     *
     * @param image L'image de la pièce.
     */
    public void setImage(ImageView image) {
        this.image = image;
    }

    /**
     * Obtient le nom de la pièce.
     *
     * @return Le nom de la pièce.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de la pièce.
     *
     * @param nom Le nom de la pièce.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}
