package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Cavalier extends Piece {
    private static int dernierId = 0;

    public Cavalier(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/cavalier_noir.png"));
            super.setNom("Cavalier" + dernierId + "noir");
        } else {
            super.setImage(new ImageView("/img/image echec/cavalier_blanc.png"));
            super.setNom("Cavalier" + dernierId + "blanc");
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
