package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

public class Pion extends Piece {

    public Pion(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/resources/img/images echec/cavalier_blanc.png"));
        } else {
            super.setImage(new ImageView("/resources/img/images echec/cavalier_noir.png"));
        }
        this.getImage().setFitHeight(50);
        this.getImage().setFitWidth(50);
    }

    //@Override
    //public void move(int x, int y, Board board) {
    // Vérifier si le mouvement est valide
    //if (isValide(x, y, board)) {
    // Mettre à jour les coordonnées
    //this.setCoordonne(x, y);
    //} else {
    // Gérer l'erreur
    //System.out.println("Invalid move");
    //}
    // this.setCoordonne(x,y);
    // }

    @Override
    public boolean isValide(int x, int y, Board chessBoard) {
        // Vérifier si le mouvement est valide pour un pion
        // Cette logique est simplifiée et ne couvre pas tous les cas (comme la prise en passant)
        int direction = isWhite() ? -1 : 1;

        if (chessBoard.getPiece(getX() + direction, getY()) != null) {
            // Le pion est bloqué par une autre pièce
            return false;
        } else if (y == getY() && x == getX() + direction && chessBoard.getPiece(x, y) == null) {
            // Avancer d'une case
            return true;
        } else if ((y == getY() + 1 || y == getY() - 1) && x == getX() + direction && chessBoard.getPiece(x, y) != null && chessBoard.getPiece(x, y).isWhite() != isWhite()) {
            // Capturer en diagonale
            return true;
        } else if (!hasMoved() && y == getY() && x == getX() + 2 * direction && chessBoard.getPiece(x, y) == null && chessBoard.getPiece(getX() + direction, getY()) == null) {
            // Avancer de deux cases
            return true;
        }
        return false;
    }

    private boolean hasMoved() {
        // Vérifier si le pion a déjà bougé
        // Cette logique est simplifiée et suppose que les pions commencent aux rangées 1 et 6
        return isWhite() ? getX() != 6 : getX() != 1;
    }


    @Override
    public void move(int x, int y) {

    }
}

