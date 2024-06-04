package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Tour extends Piece {

    public Tour(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/tour_blanc.png"));
        }
        else {
            super.setImage(new ImageView("/img/image echec/tour_noir.png"));
        }
        this.getImage().setFitHeight(75);
        this.getImage().setFitWidth(75);
    }

    @Override
    public void move(int x, int y) {

    }

    //@Override
    //public void move(int x, int y) {

        //this.setCoordonne(x,y);
    //}

    @Override
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>>  chessBoard) {
        // Vérifier si le mouvement est horizontal ou vertical
        if (x != getX() && y != getY()) {
            return false;
        }

        // Vérifier si le chemin est bloqué
        if (x == getX()) {
            // Mouvement vertical
            int startY = Math.min(y, getY());
            int endY = Math.max(y, getY());
            for (int i = startY; i <= endY; i++) {
                Piece piece = chessBoard.get(x).get( i);
                if (piece != null && piece.isWhite() == isWhite()) {
                    return false;
                }
            }
        } else {
            // Mouvement horizontal
            int startX = Math.min(x, getX());
            int endX = Math.max(x, getX());
            for (int i = startX; i <= endX; i++) {
                Piece piece = chessBoard.get(i).get( y);
                if (piece != null && piece.isWhite() == isWhite()) {
                    return false;
                }
            }
        }

        // Vérifier si la case d'arrivée est occupée par une pièce de la même couleur
        Piece destinationPiece = chessBoard.get(x).get( y);
        if (destinationPiece != null && destinationPiece.isWhite() == isWhite()) {
            return false;
        }

        return true;
    }
}
