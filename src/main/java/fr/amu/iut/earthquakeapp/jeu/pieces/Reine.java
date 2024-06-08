package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * Classe représentant une reine dans le jeu d'échecs.
 */
public class Reine extends Piece {

    private static int dernierId = 0;  // Compteur statique pour donner un identifiant unique à chaque reine

    /**
     * Constructeur de la classe Reine.
     *
     * @param isWhite Indique si la reine est blanche (true) ou noire (false).
     * @param x       Coordonnée x initiale de la reine sur l'échiquier.
     * @param y       Coordonnée y initiale de la reine sur l'échiquier.
     */
    public Reine(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/dame_blanc.png"));
            super.setNom("Reine" + dernierId + "blanc");
        } else {
            super.setImage(new ImageView("/img/image echec/dame_noir.png"));
            super.setNom("Reine" + dernierId + "noir");
        }

        this.getImage().setFitHeight(75);
        this.getImage().setFitWidth(75);
        ++dernierId;
    }

    /**
     * Déplace la reine vers de nouvelles coordonnées.
     *
     * @param x Nouvelle coordonnée x.
     * @param y Nouvelle coordonnée y.
     */
    @Override
    public void move(int x, int y) {
        setCoordonne(x, y);
    }

    /**
     * Vérifie si un mouvement est valide pour la reine.
     *
     * @param x       Coordonnée x de destination.
     * @param y       Coordonnée y de destination.
     * @param plateau Plateau de jeu contenant toutes les pièces.
     * @return true si le mouvement est valide, false sinon.
     */
    @Override
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>> plateau) {
        int deltaX = Math.abs(x - getX());
        int deltaY = Math.abs(y - getY());

        // Vérifier si le mouvement est horizontal, vertical ou diagonal
        boolean isHorizontalOrVertical = (x == getX() || y == getY());
        boolean isDiagonal = (deltaX == deltaY);

        if (!isHorizontalOrVertical && !isDiagonal) {
            return false;
        }

        // Vérifier si le chemin est bloqué
        if (isHorizontalOrVertical) {
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
        } else {
            // Mouvement diagonal
            int xDirection = (x - getX()) > 0 ? 1 : -1;
            int yDirection = (y - getY()) > 0 ? 1 : -1;
            int currentX = getX() + xDirection;
            int currentY = getY() + yDirection;
            while (currentX != x && currentY != y) {
                if (plateau.get(currentX).get(currentY) != null) {
                    return false;
                }
                currentX += xDirection;
                currentY += yDirection;
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
