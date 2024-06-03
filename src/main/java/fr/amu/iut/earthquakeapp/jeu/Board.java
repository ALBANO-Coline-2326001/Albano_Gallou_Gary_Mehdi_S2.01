package fr.amu.iut.earthquakeapp.jeu;

import fr.amu.iut.earthquakeapp.jeu.pieces.*;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        // initialiser le plateau avec les pièces
        // 0 = case vide
        // 1 = pion
        // 2 = cavalier
        // 3 = fou
        // 4 = tour
        // 5 = reine
        // 6 = roi
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean isWhite = i < 2; // les pièces des deux premières rangées sont blanches, les autres sont noires
                if (i == 0 || i == 7) {
                    if (j == 0 || j == 7) {
                        board[i][j] = new Tour(isWhite, i, j); // Tour
                    } else if (j == 1 || j == 6) {
                        board[i][j] = new Cavalier(isWhite, i, j); // Cavalier
                    } else if (j == 2 || j == 5) {
                        board[i][j] = new Fou(isWhite, i, j); // Fou
                    } else if (j == 3) {
                        board[i][j] = new Reine(isWhite, i, j); // Reine
                    } else {
                        board[i][j] = new Roi(isWhite, i, j); // Roi
                    }
                } else if (i == 1 || i == 6) {
                    board[i][j] = new Pion(isWhite, i, j); // Pion
                } else {
                    board[i][j] = null; // case vide
                }
            }
        }
    }

    // autres méthodes...
}