package fr.amu.iut.earthquakeapp.jeu;

import javafx.scene.image.ImageView;

public abstract class Piece {
    private ImageView image;
    private boolean isWhite;
    private int x;
    private int y;

    public Piece(boolean isWhite, int x, int y) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //abstract public void move(int x, int y, Board chessBoard);

    public abstract void move(int x, int y);

    abstract public boolean isValide(int x, int y, Board chessBoard);

    public int[] getCoordonne() {
        return new int[]{x, y};
    }

    public void setCoordonne(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

}
