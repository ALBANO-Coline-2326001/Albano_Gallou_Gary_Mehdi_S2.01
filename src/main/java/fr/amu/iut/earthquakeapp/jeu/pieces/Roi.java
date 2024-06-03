package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

public class Roi extends Piece {

    public Roi(boolean isWhite, int x, int y, ImageView image) {
        super(isWhite, x, y, image);
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
    }

    @Override
    public void move(int x, int y) {
        this.setCoordonne(x,y);
    }

    @Override
    public void isValide(int x, int y, Board chessBoard) {

    }
}
