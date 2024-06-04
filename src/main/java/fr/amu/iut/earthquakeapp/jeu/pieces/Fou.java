package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Fou extends Piece {


    private static int dernierId =0;
    public Fou(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/fou_blanc.png"));
            super.setNom("Fou" + dernierId + "blanc");

        }

        else {
            super.setImage(new ImageView("/img/image echec/fou_noir.png"));
            super.setNom("Fou" + dernierId + "noir");
        }

        this.getImage().setFitHeight(75);
        this.getImage().setFitWidth(75);
        ++dernierId;

    }

        @Override
        public void move(int x, int y) {

        this.setCoordonne(x,y);
        }

    @Override
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>> plateau) {

    return true;
    }
}