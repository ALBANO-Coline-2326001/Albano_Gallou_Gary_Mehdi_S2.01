package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Roi extends Piece {

    private static int dernierId = 0;

    public Roi(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/roi_noir.png"));
            super.setNom("Roi" + dernierId + "noir");
        } else {
            super.setImage(new ImageView("/img/image echec/roi_blanc.png"));
            super.setNom("Roi" + dernierId + "blanc");
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

    public boolean isDead(int x, int y, ArrayList<ArrayList<Piece>> plateau){
        // Verifier si la case ou se trouve le roi vient d'être prise
        if (this == plateau.get(x).get(y) ){

        }
        return false;

    }
}
