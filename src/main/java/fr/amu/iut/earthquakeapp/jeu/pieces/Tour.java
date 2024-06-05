package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Tour extends Piece {

    private static int dernierId = 0;

    public Tour(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/tour_blanc.png"));
            super.setNom("Tour" + dernierId + "blanc");
        } else {
            super.setImage(new ImageView("/img/image echec/tour_noir.png"));
            super.setNom("Tour" + dernierId + "noir");
        }

        this.getImage().setFitHeight(75);
        this.getImage().setFitWidth(75);
        ++dernierId;
    }

    @Override
    public void move(int x, int y) {
        setCoordonne(x, y);
    }

    @Override
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>> plateau) {
        // Vérifier si le mouvement est horizontal ou vertical
        if (x != getX() && y != getY()) {
            return false;
        }

        // Vérifier si le chemin est bloqué
        if (x == getX()) {
            // Mouvement vertical
            int startY = Math.min(y, getY()) + 1;
            int endY = Math.max(y, getY());
            for (int i = startY; i < endY; i++) {
                if (plateau.get(x).get(i) != null) {
                    return false;
                }
            }
        } else {
            // Mouvement horizontal
            int startX = Math.min(x, getX()) + 1;
            int endX = Math.max(x, getX());
            for (int i = startX; i < endX; i++) {
                if (plateau.get(i).get(y) != null) {
                    return false;
                }
            }
        }

        // Vérifier si la case d'arrivée est occupée par une pièce de la même couleur
        Piece destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }
}
