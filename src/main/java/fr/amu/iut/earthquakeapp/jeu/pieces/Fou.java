package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Fou extends Piece {

    private static int dernierId = 0;

    public Fou(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        setImage(isWhite ? new ImageView("/img/image echec/fou_blanc.png") : new ImageView("/img/image echec/fou_noir.png"));
        setNom("Fou" + dernierId + (isWhite ? "blanc" : "noir"));
        getImage().setFitHeight(75);
        getImage().setFitWidth(75);
        dernierId++;
    }

    @Override
    public void move(int x, int y) {
        setCoordonne(x, y);
    }

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

        // Vérifier qu'il n'y a pas de pièces sur le chemin
        int currentX = getX() + xDirection;
        int currentY = getY() + yDirection;
        while (currentX != x && currentY != y) {
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
