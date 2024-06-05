package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Pion extends Piece {

    private static int dernierId = 0;
    private boolean fristMove = true;

    public Pion(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        setImage(isWhite ? new ImageView("/img/image echec/pion_noir.png") : new ImageView("/img/image echec/pion_blanc.png"));
        setNom("Pion" + dernierId + (isWhite ? "noir" : "blanc"));
        getImage().setFitHeight(75);
        getImage().setFitWidth(75);
        dernierId++;
    }

    @Override
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>> plateau) {
        int direction = isWhite() ? 1 : -1;

        // Avancer d'une case
        if (x == getX() + direction && y == getY() && plateau.get(x).get(y) == null) {
            return true;
        }

        // Avancer de deux cases depuis la position initiale
        if (x == getX() + 2 * direction && y == getY() && fristMove &&
                plateau.get(x).get(y) == null && plateau.get(getX() + direction).get(getY()) == null) {
            fristMove = false;
            return true;
        }

        // Capturer une pièce en diagonale
        return x == getX() + direction && (y == getY() + 1 || y == getY() - 1) &&
                plateau.get(x).get(y) != null && plateau.get(x).get(y).isWhite() != isWhite();
    }

    @Override
    public void move(int x, int y) {
        setCoordonne(x, y);
        fristMove = false;
    }
}
