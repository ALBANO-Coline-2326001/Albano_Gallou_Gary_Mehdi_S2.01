package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Roi extends Piece {

    public Roi(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/roi_blanc.png"));
        }
        else {
            super.setImage(new ImageView("/img/image echec/roi_noir.png"));
        }
        this.getImage().setFitHeight(75);
        this.getImage().setFitWidth(75);
    }

    //@Override
    //public void move(int x, int y) {

        //this.setCoordonne(x,y);
    //}

    @Override
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>>  chessBoard) {

        return false;
    }
    @Override
    public void move(int x, int y) {

    }




}
