package fr.amu.iut.earthquakeapp.jeu.pieces;

import fr.amu.iut.earthquakeapp.jeu.Board;
import fr.amu.iut.earthquakeapp.jeu.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Pion extends Piece {

    private boolean fristmove = true;
    private static int dernierId =0;
    public Pion(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            super.setImage(new ImageView("/img/image echec/pion_blanc.png"));
            super.setNom("Pion" + dernierId + "blanc");

        }

        else {
            super.setImage(new ImageView("/img/image echec/pion_noir.png"));
            super.setNom("Pion" + dernierId + "noir");
        }

        this.getImage().setFitHeight(75);
        this.getImage().setFitWidth(75);
        ++dernierId;

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
    public boolean isValide(int x, int y, ArrayList<ArrayList<Piece>> plateau) {
        int direction = isWhite() ? 1 : -1;
        int startRow = isWhite() ? 6 : 1;

        System.out.println(getX());
        // Avancer d'une case
        if (x == getX() + direction && y == getY() && plateau.get(x).get(y) == null ) {
            return true;
        }

        // Avancer de deux cases depuis la position initiale
        if (x == getX() + 2 * direction && y == getY()  &&
                plateau.get(x).get(y) == null && plateau.get(getX() + direction).get(getY()) == null && fristmove) {
            fristmove = false;
            return true;
        }/*
        // Capturer une pièce en diagonale
        if (x == getX() + direction && (y == getY() + 1 || y == getY() - 1) &&
                plateau.get(x).get(y) != null && plateau.get(x).get(y).isWhite() != isWhite()) {
            return true;
        }*/
        return false;
    }


    private boolean hasMoved() {
        // Vérifier si le pion a déjà bougé
        // Cette logique est simplifiée et suppose que les pions commencent aux rangées 1 et 6
        return isWhite() ? getX() != 6 : getX() != 1;
    }


    @Override
    public void move(int x, int y) {
        this.setCoordonne(x, y);  // Met à jour les coordonnées internes de la pièce
    }
}

