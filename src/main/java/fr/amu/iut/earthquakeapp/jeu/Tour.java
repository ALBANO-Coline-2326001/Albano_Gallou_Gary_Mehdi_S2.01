package fr.amu.iut.earthquakeapp.jeu;

public class Tour extends Piece{


    public Tour(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public void move(int x, int y) {
          this.setCoordonne(x,y);
    }
}
