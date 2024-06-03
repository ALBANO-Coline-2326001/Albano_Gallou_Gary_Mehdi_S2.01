package fr.amu.iut.earthquakeapp.jeu;

import javafx.scene.image.ImageView;

public abstract class Piece {
    private ImageView image;
    private boolean isWhite;
    private int x;
    private int y;

    public Piece(boolean isWhite, int x, int y, ImageView image) {
        this.isWhite = isWhite;
        this.x = x;
        this.y = y;
        this.image = image;

    }

    abstract public void move(int x, int y);

    abstract public void isValide(int x, int y, Board chessBoard);

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

}
