package fr.amu.iut.chess.jeu.pieces;

import fr.amu.iut.chess.jeu.Piece;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * Classe représentant un pion dans le jeu d'échecs.
 */
public class Pion extends Piece {

    private static int dernierId = 0;  // Compteur statique pour donner un identifiant unique à chaque pion
    private boolean fristMove;  // Indique si le pion est dans son premier mouvement

    /**
     * Constructeur de la classe Pion.
     *
     * @param isWhite Indique si le pion est blanc (true) ou noir (false).
     * @param x       Coordonnée x initiale du pion sur l'échiquier.
     * @param y       Coordonnée y initiale du pion sur l'échiquier.
     */
    public Pion(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        setImage(isWhite ? new ImageView("/img/image echec/pion_blanc.png") : new ImageView("/img/image echec/pion_noir.png"));
        setNom("Pion" + dernierId + (isWhite ? "blanc" : "noir"));
        getImage().setFitHeight(75);
        getImage().setFitWidth(75);
        dernierId++;

        // Initialiser fristMove à true pour chaque nouvelle instance de Pion
        fristMove = true;
    }

    /**
     * Vérifie si un mouvement est valide pour le pion.
     *
     * @param x       Coordonnée x de destination.
     * @param y       Coordonnée y de destination.
     * @param plateau Plateau de jeu contenant toutes les pièces.
     * @return true si le mouvement est valide, false sinon.
     */
    @Override
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>> plateau) {
        int direction = isWhite() ? -1 : 1;

        // Vérifier que les indices sont dans les limites du plateau
        if (x < 0 || x >= plateau.size() || y < 0 || y >= plateau.get(0).size()) {
            return false;
        }

        // Avancer d'une case
        if (x == getX() + direction && y == getY() && plateau.get(x).get(y) == null) {
            return true;
        }

        // Avancer de deux cases depuis la position initiale
        if (fristMove && x == getX() + 2 * direction && y == getY() &&
                plateau.get(getX() + direction).get(getY()) == null && plateau.get(x).get(y) == null) {
            return true;
        }

        // Capturer une pièce en diagonale
        if (x == getX() + direction && (y == getY() + 1 || y == getY() - 1)) {
            Piece piece = plateau.get(x).get(y);
            if (piece != null && piece.isWhite() != isWhite()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Déplace le pion vers de nouvelles coordonnées.
     *
     * @param x Nouvelle coordonnée x.
     * @param y Nouvelle coordonnée y.
     */
    @Override
    public void move(int x, int y) {
        // Ne désactivez le premier mouvement que si le pion a effectivement bougé
        fristMove = false;
        setCoordonne(x, y);
    }
}
