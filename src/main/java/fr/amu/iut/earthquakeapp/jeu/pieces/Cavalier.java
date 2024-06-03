package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Piece;

public class Cavalier extends Piece {

    public Cavalier(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }
    @Override
    public void move(int x, int y) {
          this.setCoordonne(x,y);
    }
}
