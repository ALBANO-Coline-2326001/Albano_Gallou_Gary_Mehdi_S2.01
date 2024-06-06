package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Pion extends Piece {

    private static int dernierId = 0;
    private boolean fristMove;

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
                plateau.get(getX() + direction).get(getY()) == null && plateau.get(x).get(y) == null &&
                plateau.get(x - direction).get(y) == null) {
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

    @Override
    public void move(int x, int y) {
        // Ne désactivez le premier mouvement que si le pion a effectivement bougé
        fristMove = false;
        setCoordonne(x, y);


    }
}
