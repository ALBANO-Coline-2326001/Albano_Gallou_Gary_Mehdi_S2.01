package fr.amu.iut.earthquakeapp.jeu;

public abstract class Piece {

    private boolean isWhite;
    private int x;
    private int y;

    public Piece(boolean isWhite,int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;

    }

    abstract public void move();

    public int[] getCoordonne(){
        return new int[]{x,y};
    }

}
