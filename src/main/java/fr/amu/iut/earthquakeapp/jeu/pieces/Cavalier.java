package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * Classe représentant un cavalier dans le jeu d'échecs.
 */
public class Cavalier extends Piece {
    private static int dernierId = 0;  // Compteur statique pour donner un identifiant unique à chaque cavalier

    /**
     * Constructeur de la classe Cavalier.
     *
     * @param isWhite Indique si le cavalier est blanc (true) ou noir (false).
     * @param x       Coordonnée x initiale du cavalier sur l'échiquier.
     * @param y       Coordonnée y initiale du cavalier sur l'échiquier.
     */
    public Cavalier(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/cavalier_blanc.png"));
            super.setNom("Cavalier" + dernierId + "blanc");
        } else {
            super.setImage(new ImageView("/img/image echec/cavalier_noir.png"));
            super.setNom("Cavalier" + dernierId + "noir");
        }
        this.getImage().setFitHeight(75);
        this.getImage().setFitWidth(75);
        ++dernierId;
    }

    /**
     * Déplace le cavalier vers de nouvelles coordonnées.
     *
     * @param x Nouvelle coordonnée x.
     * @param y Nouvelle coordonnée y.
     */
    @Override
    public void move(int x, int y) {
        setCoordonne(x, y);
    }

    /**
     * Vérifie si un mouvement est valide pour le cavalier.
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

        // Vérifier que le mouvement correspond à un mouvement en "L" (2 cases dans une direction et 1 case dans l'autre)
        boolean isLMove = (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);

        if (!isLMove) {
            return false;
        }

        // Vérifier que la case de destination n'est pas occupée par une pièce de la même couleur
        Piece destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }
}
