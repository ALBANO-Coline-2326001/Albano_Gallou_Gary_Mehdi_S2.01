package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

public class Cavalier extends Piece {

    public Cavalier(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/resources/img/images echec/cavalier_blanc.png"));
        }
        else {
            super.setImage(new ImageView("/resources/img/images echec/cavalier_noir.png"));
        }
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
