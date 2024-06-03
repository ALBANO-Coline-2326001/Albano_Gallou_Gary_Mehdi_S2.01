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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    abstract public void move(int x, int y);
    abstract public void isValide(int x, int y,Board chessBoard);

    public int[] getCoordonne(){
        return new int[]{x,y};
    }

    public void setCoordonne(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
