package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * Classe représentant un fou dans le jeu d'échecs.
 */
public class Fou extends Piece {

    private static int dernierId = 0;  // Compteur statique pour donner un identifiant unique à chaque fou

    /**
     * Constructeur de la classe Fou.
     *
     * @param isWhite Indique si le fou est blanc (true) ou noir (false).
     * @param x       Coordonnée x initiale du fou sur l'échiquier.
     * @param y       Coordonnée y initiale du fou sur l'échiquier.
     */
    public Fou(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        setImage(isWhite ? new ImageView("/img/image echec/fou_blanc.png") : new ImageView("/img/image echec/fou_noir.png"));
        setNom("Fou" + dernierId + (isWhite ? "blanc" : "noir"));
        getImage().setFitHeight(75);
        getImage().setFitWidth(75);
        dernierId++;
    }

    /**
     * Déplace le fou vers de nouvelles coordonnées.
     *
     * @param x Nouvelle coordonnée x.
     * @param y Nouvelle coordonnée y.
     */
    @Override
    public void move(int x, int y) {
        setCoordonne(x, y);
    }

    /**
     * Vérifie si un mouvement est valide pour le fou.
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

        // Vérifier que le mouvement est diagonal
        if (deltaX != deltaY) {
            return false;
        }

        // Déterminer la direction du mouvement
        int xDirection = (x - getX()) > 0 ? 1 : -1;
        int yDirection = (y - getY()) > 0 ? 1 : -1;

        // Vérifier que les indices sont dans les limites du plateau
        if (x < 0 || x >= plateau.size() || y < 0 || y >= plateau.get(0).size()) {
            return false;
        }

        // Vérifier qu'il n'y a pas de pièces sur le chemin
        int currentX = getX() + xDirection;
        int currentY = getY() + yDirection;
        while (currentX != x && currentY != y) {
            if (currentX < 0 || currentX >= plateau.size() || currentY < 0 || currentY >= plateau.get(0).size()) {
                return false;
            }
            if (plateau.get(currentX).get(currentY) != null) {
                return false;
            }
            currentX += xDirection;
            currentY += yDirection;
        }

        // Vérifier la case de destination
        Piece destinationPiece = plateau.get(x).get(y);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        return true;
    }
}
