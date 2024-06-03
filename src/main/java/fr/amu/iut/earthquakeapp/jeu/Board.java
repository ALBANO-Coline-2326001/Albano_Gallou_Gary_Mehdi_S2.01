package fr.amu.iut.earthquakeapp.jeu;

import fr.amu.iut.earthquakeapp.jeu.pieces.*;
import java.util.ArrayList;

public class Board {
    private ArrayList<ArrayList<Piece>> board;

    public Board() {
        board = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ArrayList<Piece> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                boolean isWhite = i < 2;
                if (i == 0 || i == 7) {
                    if (j == 0 || j == 7) {
                        row.add(new Tour(isWhite, i, j));
                    } else if (j == 1 || j == 6) {
                        row.add(new Cavalier(isWhite, i, j));
                    } else if (j == 2 || j == 5) {
                        row.add(new Fou(isWhite, i, j));
                    } else if (j == 3) {
                        row.add(new Reine(isWhite, i, j));
                    } else {
                        row.add(new Roi(isWhite, i, j));
                    }
                } else if (i == 1 || i == 6) {
                    row.add(new Pion(isWhite, i, j));
                } else {
                    row.add(null);
                }
            }
            board.add(row);
        }
    }

    // autres méthodes...
}