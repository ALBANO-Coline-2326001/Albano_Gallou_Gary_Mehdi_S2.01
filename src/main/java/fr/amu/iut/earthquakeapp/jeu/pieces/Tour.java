package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

public class Tour extends Piece {

    private ImageView image;

    public Tour(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.image = image;
        this.image.setFitHeight(50);
        this.image.setFitWidth(50);
    }

    @Override
    public void move(int x, int y) {

        this.setCoordonne(x,y);
    }

    @Override
    public void isValide(int x, int y, Board chessBoard) {

    }
}
