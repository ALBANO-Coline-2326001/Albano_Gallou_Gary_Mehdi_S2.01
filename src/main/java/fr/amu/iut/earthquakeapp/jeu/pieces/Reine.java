package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

public  class Reine extends Piece {

    public Reine(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/dame_blanc.png"));
        }
        else {
            super.setImage(new ImageView("/img/image echec/dame_noir.png"));
        }
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
    }

        //@Override
        //public void move(int x, int y) {
            //this.setCoordonne(x,y);
        //}

    @Override
    public boolean isValide(int x, int y, Board chessBoard) {

        return false;
    }
    @Override
    public void move(int x, int y) {

    }

}
