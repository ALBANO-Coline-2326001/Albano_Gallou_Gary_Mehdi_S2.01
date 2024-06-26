package fr.amu.iut.chess.jeu.pieces;

import fr.amu.iut.chess.jeu.Piece;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * Classe représentant un roi dans le jeu d'échecs.
 */
public class Roi extends Piece {

    private static int dernierId = 0;  // Compteur statique pour donner un identifiant unique à chaque roi

    /**
     * Constructeur de la classe Roi.
     *
     * @param isWhite Indique si le roi est blanc (true) ou noir (false).
     * @param x       Coordonnée x initiale du roi sur l'échiquier.
     * @param y       Coordonnée y initiale du roi sur l'échiquier.
     */
    public Roi(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/roi_blanc.png"));
            super.setNom("Roi" + dernierId + "blanc");
        } else {
            super.setImage(new ImageView("/img/image echec/roi_noir.png"));
            super.setNom("Roi" + dernierId + "noir");
        }

        this.getImage().setFitHeight(75);
        this.getImage().setFitWidth(75);
        ++dernierId;
    }

    /**
     * Déplace le roi vers de nouvelles coordonnées.
     *
     * @param x Nouvelle coordonnée x.
     * @param y Nouvelle coordonnée y.
     */
    @Override
    public void move(int x, int y) {
        setCoordonne(x, y);
    }

    /**
     * Vérifie si un mouvement est valide pour le roi.
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

        // Vérifier si le mouvement est d'une case dans n'importe quelle direction
        if (deltaX > 1 || deltaY > 1) {
            return false;
        }

        // Vérifier si la case d'arrivée est occupée par une pièce de la même couleur
        Piece destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }

}